<script setup lang="ts">
import { useAuth } from '../composables/useAuth'

const {
  showAuth,
  mode,
  loading,
  error,
  form,
  closeAuth,
  switchMode,
  submitAuth,
} = useAuth()
</script>

<template>
  <transition name="fade">
    <div v-if="showAuth" class="fixed inset-0 z-[80] flex items-center justify-center p-4 sm:p-6">
      <div class="absolute inset-0 bg-black/35 backdrop-blur-md" @click="closeAuth"></div>
      <transition name="pop">
        <section
          v-if="showAuth"
          class="relative w-full max-w-[430px] overflow-hidden rounded-[32px] border border-white/70 bg-white/90 shadow-[0_30px_90px_rgba(0,0,0,.18)] backdrop-blur-2xl"
          aria-labelledby="auth-title"
        >
          <div class="absolute inset-x-0 top-0 h-1 bg-gradient-to-r from-apple-blue via-apple-purple to-apple-orange"></div>
          <div class="px-7 pb-8 pt-9 sm:px-10 sm:pt-11">
            <div class="mb-9 flex items-start justify-between">
              <div>
                <div class="mb-4 grid h-10 w-10 place-items-center rounded-[13px] bg-black text-white shadow-lg shadow-black/10">
                  <svg fill="none" height="20" viewBox="0 0 24 24" width="20">
                    <path d="M4 15c3-1 5-4 5-9 4 2 7 5 7 9a5 5 0 0 1-10 0Z" fill="currentColor"></path>
                    <path d="M14 3c4 1 6 5 6 9" stroke="currentColor" stroke-linecap="round" stroke-width="2"></path>
                  </svg>
                </div>
                <p class="text-[11px] font-semibold uppercase tracking-[.22em] text-ink-faint">大道消息 · 会员入口</p>
                <h2 id="auth-title" class="mt-2 text-[30px] font-semibold tracking-[-.04em] text-ink">
                  {{ mode === 'login' ? '欢迎回来。' : '加入大道。' }}
                </h2>
                <p class="mt-2 text-[14px] leading-relaxed text-ink-soft">
                  {{ mode === 'login' ? '登录后，推荐值得被看见的公司。' : '创建账号，参与一座城市的选择。' }}
                </p>
              </div>
              <button
                class="grid h-8 w-8 place-items-center rounded-full bg-mist text-ink-soft transition hover:bg-mist-300"
                aria-label="关闭"
                @click="closeAuth"
              >
                <svg fill="none" height="14" viewBox="0 0 24 24" width="14">
                  <path d="M6 6l12 12M18 6 6 18" stroke="currentColor" stroke-linecap="round" stroke-width="2.2"></path>
                </svg>
              </button>
            </div>

            <form class="space-y-4" @submit.prevent="submitAuth">
              <label class="block">
                <span class="mb-2 block text-[12px] font-medium text-ink-2">邮箱或手机号</span>
                <input
                  v-model="form.account"
                  autocomplete="username"
                  class="w-full rounded-2xl border border-black/[.07] bg-white/80 px-4 py-3.5 text-[15px] outline-none transition placeholder:text-ink-faint focus:border-apple-blue/50 focus:ring-4 focus:ring-apple-blue/10"
                  placeholder="name@example.com / 13800000000"
                />
              </label>
              <label class="block">
                <span class="mb-2 block text-[12px] font-medium text-ink-2">密码</span>
                <input
                  v-model="form.password"
                  autocomplete="current-password"
                  class="w-full rounded-2xl border border-black/[.07] bg-white/80 px-4 py-3.5 text-[15px] outline-none transition placeholder:text-ink-faint focus:border-apple-blue/50 focus:ring-4 focus:ring-apple-blue/10"
                  placeholder="至少 8 位字符"
                  type="password"
                />
              </label>
              <label v-if="mode === 'register'" class="block">
                <span class="mb-2 block text-[12px] font-medium text-ink-2">确认密码</span>
                <input
                  v-model="form.confirmPassword"
                  autocomplete="new-password"
                  class="w-full rounded-2xl border border-black/[.07] bg-white/80 px-4 py-3.5 text-[15px] outline-none transition placeholder:text-ink-faint focus:border-apple-blue/50 focus:ring-4 focus:ring-apple-blue/10"
                  placeholder="再次输入密码"
                  type="password"
                />
              </label>

              <p v-if="error" class="rounded-2xl bg-apple-red/8 px-4 py-3 text-[12px] leading-relaxed text-apple-redd">
                {{ error }}
              </p>

              <button
                class="flex w-full items-center justify-center gap-2 rounded-full bg-apple-blue py-3.5 text-[15px] font-medium text-white shadow-lg shadow-apple-blue/20 transition hover:bg-apple-blue2 disabled:cursor-wait disabled:opacity-60"
                :disabled="loading"
                type="submit"
              >
                <span v-if="loading" class="h-4 w-4 animate-spin rounded-full border-2 border-white/40 border-t-white"></span>
                {{ loading ? '请稍候' : mode === 'login' ? '登录' : '创建账号' }}
              </button>
            </form>

            <div class="mt-7 flex items-center justify-center gap-2 text-[13px] text-ink-soft">
              <span>{{ mode === 'login' ? '还没有账号？' : '已经有账号？' }}</span>
              <button class="font-medium text-apple-blue hover:underline" @click="switchMode(mode === 'login' ? 'register' : 'login')">
                {{ mode === 'login' ? '立即注册' : '返回登录' }}
              </button>
            </div>
          </div>
        </section>
      </transition>
    </div>
  </transition>
</template>
