import { computed, reactive, ref } from 'vue'
import { parseJson } from '../utils/json'
import { useCompanies } from './useCompanies'

export interface AuthUser {
  id: string
  account: string
}

type AuthMode = 'login' | 'register'

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api'
const token = ref(localStorage.getItem('liangxuan_token') || '')
const user = ref<AuthUser | null>(loadUser())
const showAuth = ref(false)
const mode = ref<AuthMode>('login')
const loading = ref(false)
const error = ref('')
const form = reactive({ account: '', password: '', confirmPassword: '' })

function loadUser(): AuthUser | null {
  const saved = localStorage.getItem('liangxuan_user')
  if (!saved) return null
  try {
    return parseJson<AuthUser>(saved)
  } catch {
    localStorage.removeItem('liangxuan_user')
    return null
  }
}

function isValidAccount(account: string) {
  return /^(?:[^\s@]+@[^\s@]+\.[^\s@]+|1[3-9]\d{9})$/.test(account)
}

function resetForm() {
  form.account = ''
  form.password = ''
  form.confirmPassword = ''
  error.value = ''
}

export function useAuth() {
  const isAuthenticated = computed(() => Boolean(token.value && user.value))

  function openLogin() {
    mode.value = 'login'
    resetForm()
    showAuth.value = true
  }

  function openRegister() {
    mode.value = 'register'
    resetForm()
    showAuth.value = true
  }

  function closeAuth() {
    if (!loading.value) showAuth.value = false
  }

  function switchMode(nextMode: AuthMode) {
    mode.value = nextMode
    resetForm()
  }

  async function submitAuth() {
    error.value = ''
    if (!isValidAccount(form.account.trim())) {
      error.value = '请输入有效的邮箱或手机号'
      return false
    }
    if (form.password.length < 8) {
      error.value = '密码至少需要 8 位'
      return false
    }
    if (mode.value === 'register' && form.password !== form.confirmPassword) {
      error.value = '两次输入的密码不一致'
      return false
    }

    loading.value = true
    try {
      const endpoint = mode.value === 'login' ? '/auth/login' : '/auth/register'
      const response = await fetch(`${API_BASE_URL}${endpoint}`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          account: form.account.trim(),
          password: form.password,
        }),
      })
      const contentType = response.headers.get('content-type') || ''
      if (!contentType.includes('application/json')) {
        if (response.status === 403) {
          throw new Error('请求被 CORS 拒绝，请将当前前端完整地址（含端口）配置到 CORS_ALLOWED_ORIGIN 后重启后端')
        }
        throw new Error(response.ok ? '服务返回了无法识别的响应' : `请求失败（${response.status}）`)
      }
      const result = parseJson<{ code: number; message?: string; data?: { token: string; user: AuthUser } }>(await response.text())
      if (!response.ok || result.code !== 200 || !result.data?.token) {
        throw new Error(result.message || '认证失败，请稍后重试')
      }
      token.value = result.data.token
      user.value = { ...result.data.user, id: String(result.data.user.id) }
      localStorage.setItem('liangxuan_token', token.value)
      localStorage.setItem('liangxuan_user', JSON.stringify(user.value))
      showAuth.value = false
      resetForm()
      void useCompanies().loadUserVotes()
      return true
    } catch (requestError) {
      error.value = requestError instanceof TypeError
        ? '无法连接后端，或当前前端地址未被 CORS_ALLOWED_ORIGIN 允许，请检查后端地址和端口配置'
        : requestError instanceof Error ? requestError.message : '认证失败，请稍后重试'
      return false
    } finally {
      loading.value = false
    }
  }

  function logout() {
    token.value = ''
    user.value = null
    localStorage.removeItem('liangxuan_token')
    localStorage.removeItem('liangxuan_user')
    const { votes } = useCompanies()
    Object.keys(votes).forEach((k) => delete votes[k])
  }

  return {
    token,
    user,
    isAuthenticated,
    showAuth,
    mode,
    loading,
    error,
    form,
    openLogin,
    openRegister,
    closeAuth,
    switchMode,
    submitAuth,
    logout,
  }
}
