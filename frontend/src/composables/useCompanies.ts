import { ref, reactive, computed, watch } from 'vue'
import type { Company, VoteType, Filter, Stat, Pillar, CompanyForm } from '../types'
import { net } from '../utils/helpers'
import { useAuth } from './useAuth'
import { parseJson } from '../utils/json'

// ========== 单例共享状态 ==========
const companies = ref<Company[]>([])
const votes = reactive<Record<string, VoteType>>({})
const loading = ref(false)
const loadError = ref('')
const query = ref('')
const activeFilter = ref('all')
const showAdd = ref(false)
const toast = ref('')
const formError = ref('')
const form = reactive<CompanyForm>({
  name: '',
  website: '',
  product: '',
  start: '09:00',
  end: '18:00',
  days: 5,
})

const filters: Filter[] = [
  { k: 'all', label: '全部' },
  { k: 'green', label: '绿榜' },
  { k: 'red', label: '红榜' },
]

const allTimes: string[] = Array.from({ length: 24 }, (_, i) =>
  (i < 10 ? '0' + i : '' + i) + ':00',
)
const startTimes = allTimes
const endTimes = computed(() =>
  allTimes.filter((t) => parseInt(t, 10) >= parseInt(form.start, 10)),
)

const pillars: Pillar[] = [
  { n: '用脚投票', t: '你的每一次购买与点赞，都在帮好公司赢得市场竞争。' },
  { n: '用数据说话', t: '真实口碑被量化成榜单，让坚持体面的公司被看见。' },
  { n: '良币驱逐劣币', t: '当良币获得更多利润，劣币自然失去生存空间。' },
]

let toastTimer: ReturnType<typeof setTimeout> | undefined
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '/api'
let companiesLoaded = false

// 下班点不能早于上班点：上班点变化时自动修正
watch(
  () => form.start,
  (s) => {
    if (parseInt(form.end, 10) < parseInt(s, 10)) form.end = s
  },
)

export function useCompanies() {
  const { isAuthenticated, openLogin } = useAuth()

  async function loadCompanies() {
    if (companiesLoaded || loading.value) return
    loading.value = true
    loadError.value = ''
    try {
      const response = await fetch(`${API_BASE_URL}/companies?pageNum=1&pageSize=100`)
      const result = parseJson<{ code: number; message?: string; data: { records: BackendCompany[] } }>(await response.text())
      if (!response.ok || result.code !== 200) throw new Error(result.message || '公司数据加载失败')
      companies.value = (result.data.records as BackendCompany[]).map(toCompany)
      companiesLoaded = true
    } catch (requestError) {
      loadError.value = requestError instanceof Error ? requestError.message : '公司数据加载失败，请稍后重试'
      showToast(loadError.value)
    } finally {
      loading.value = false
    }
  }

  async function loadUserVotes() {
    const token = localStorage.getItem('liangxuan_token')
    if (!token) return
    try {
      const response = await fetch(`${API_BASE_URL}/companies/user-votes`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      const result = parseJson<{ code: number; message?: string; data: UserVoteRecord[] }>(await response.text())
      if (!response.ok || result.code !== 200) return
      Object.keys(votes).forEach((k) => delete votes[k])
      result.data.forEach((v) => {
        votes[String(v.companyId)] = v.voteType.toLowerCase() as VoteType
      })
    } catch {
      // 静默失败，不影响主流程
    }
  }

  // ========== 计算属性 ==========
  const matched = computed(() => {
    const q = query.value.trim().toLowerCase()
    return companies.value.filter(
      (c) =>
        !q ||
        c.name.toLowerCase().includes(q) ||
        (c.product || '').toLowerCase().includes(q),
    )
  })

  const greenList = computed(() => {
    if (activeFilter.value === 'red') return []
    return matched.value
      .filter((c) => net(c) >= 0)
      .sort((a, b) => net(b) - net(a) || b.likes - a.likes)
  })

  const redList = computed(() => {
    if (activeFilter.value === 'green') return []
    return matched.value
      .filter((c) => net(c) < 0)
      .sort((a, b) => net(a) - net(b) || b.dislikes - a.dislikes)
  })

  const stats = computed<Stat[]>(() => [
    { label: '收录公司', value: companies.value.length, color: 'text-ink' },
    {
      label: '绿榜席位',
      value: companies.value.filter((c) => net(c) >= 0).length,
      color: 'text-apple-greend',
    },
    {
      label: '红榜席位',
      value: companies.value.filter((c) => net(c) < 0).length,
      color: 'text-apple-redd',
    },
    {
      label: '累计投票',
      value: companies.value
        .reduce((a, c) => a + c.likes + c.dislikes, 0)
        .toLocaleString(),
      color: 'text-ink',
    },
  ])

  const previewParts = computed(() => {
    let s = parseInt(form.start, 10)
    if (isNaN(s)) s = 9
    let e = parseInt(form.end, 10)
    if (isNaN(e)) e = 18
    if (e > 12) e -= 12
    return { s, e, d: form.days }
  })

  // ========== 方法 ==========
  function showToast(msg: string) {
    toast.value = msg
    if (toastTimer) clearTimeout(toastTimer)
    toastTimer = setTimeout(() => {
      toast.value = ''
    }, 2200)
  }

  function openAdd() {
    formError.value = ''
    if (!isAuthenticated.value) {
      openLogin()
      return
    }
    showAdd.value = true
  }

  function closeAdd() {
    showAdd.value = false
  }

  function myVote(c: Company): VoteType | null {
    return votes[c.id] || null
  }

  async function vote(c: Company, type: VoteType) {
    const token = localStorage.getItem('liangxuan_token')
    if (!token) {
      openLogin()
      return
    }
    const cur = votes[c.id]
    try {
      const response = await fetch(`${API_BASE_URL}/companies/${c.id}/vote?voteType=${type.toUpperCase()}`, {
        method: 'PUT',
        headers: { Authorization: `Bearer ${token}` },
      })
      const result = parseJson<{ code: number; message?: string; data: { company: BackendCompany; myVote: string | null } }>(await response.text())
      if (!response.ok || result.code !== 200) throw new Error(result.message || '投票失败，请稍后重试')
      const data = result.data
      Object.assign(c, toCompany(data.company))
      if (data.myVote) votes[c.id] = data.myVote.toLowerCase() as VoteType
      else delete votes[c.id]
      showToast(cur === type ? '已取消这次投票' : type === 'like' ? '已为它投出支持的一票' : '已记录你的不满')
    } catch (voteError) {
      showToast(voteError instanceof Error ? voteError.message : '投票失败，请稍后重试')
    }
  }

  async function submitForm() {
    if (!form.name.trim()) {
      formError.value = '请填写公司名称'
      return
    }
    formError.value = ''
    const token = localStorage.getItem('liangxuan_token')
    if (!token) {
      openLogin()
      return
    }
    try {
      const response = await fetch(`${API_BASE_URL}/companies`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          name: form.name.trim(),
          website: form.website.trim(),
          product: form.product.trim(),
          startTime: form.start,
          endTime: form.end,
          workDays: form.days,
        }),
      })
      const result = parseJson<{ code: number; message?: string; data: BackendCompany }>(await response.text())
      if (!response.ok || result.code !== 200) {
        throw new Error(result.message || '提交失败，请稍后重试')
      }
      const company = toCompany(result.data as BackendCompany)
      companies.value.unshift(company)
      votes[company.id] = 'like'
      showAdd.value = false
      query.value = ''
      activeFilter.value = 'all'
      showToast('已上榜，等待更多人来投票')
      form.name = ''
      form.website = ''
      form.product = ''
      form.start = '09:00'
      form.end = '18:00'
      form.days = 5
      setTimeout(() => {
        document.getElementById('boards')?.scrollIntoView({ behavior: 'smooth' })
      }, 120)
    } catch (submitError) {
      formError.value = submitError instanceof Error ? submitError.message : '提交失败，请稍后重试'
    }
  }

  return {
    // state
    companies,
    loading,
    loadError,
    votes,
    query,
    activeFilter,
    showAdd,
    toast,
    formError,
    form,
    filters,
    startTimes,
    endTimes,
    pillars,
    // computed
    greenList,
    redList,
    stats,
    previewParts,
    // methods
    openAdd,
    closeAdd,
    vote,
    myVote,
    submitForm,
    loadCompanies,
    loadUserVotes,
  }
}

interface BackendCompany {
  id: string | number
  name: string
  website: string
  product: string
  startTime: string
  endTime: string
  workDays: number
  likes: number
  dislikes: number
}

interface UserVoteRecord {
  companyId: string | number
  voteType: 'LIKE' | 'DISLIKE'
}

function toCompany(company: BackendCompany): Company {
  return {
    id: String(company.id),
    name: company.name,
    website: company.website,
    product: company.product,
    start: company.startTime,
    end: company.endTime,
    days: company.workDays,
    likes: company.likes,
    dislikes: company.dislikes,
  }
}
