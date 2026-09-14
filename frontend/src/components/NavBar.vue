<script setup lang="ts">
import { useCompanies } from '../composables/useCompanies'
import { useAuth } from '../composables/useAuth'

const { openAdd } = useCompanies()
const { isAuthenticated, user, openLogin, logout } = useAuth()
</script>

<template>
  <header class="fixed top-0 inset-x-0 z-50">
    <div class="backdrop-blur-xl bg-white/75 border-b border-black/[.07]">
      <div
        class="max-w-[1024px] mx-auto px-6 h-12 flex items-center justify-between"
      >
        <a class="flex items-center gap-2" href="#top">
          <span class="w-6 h-6 rounded-md grid place-items-center bg-black">
            <svg
              fill="none"
              height="13"
              viewBox="0 0 24 24"
              width="13"
            >
              <path
                d="M4 15c3-1 5-4 5-9 4 2 7 5 7 9a5 5 0 0 1-10 0Z"
                fill="#fff"
              ></path>
              <path
                d="M14 3c4 1 6 5 6 9"
                stroke="#fff"
                stroke-linecap="round"
                stroke-width="2"
              ></path>
            </svg>
          </span>
          <span class="text-[15px] font-semibold tracking-tight">良选集市</span>
        </a>
        <nav class="hidden md:flex items-center gap-8 text-[12px] text-ink-soft">
          <a class="hover:text-ink transition" href="#boards">实时榜单</a>
          <a class="hover:text-ink transition" href="#green">绿榜</a>
          <a class="hover:text-ink transition" href="#red">红榜</a>
          <a class="hover:text-ink transition" href="#why">为什么投票</a>
        </nav>
        <div class="flex items-center gap-2">
          <button
            v-if="!isAuthenticated"
            @click="openLogin"
            class="hidden sm:block text-[12px] font-medium px-3 py-1.5 text-ink-soft hover:text-ink transition whitespace-nowrap"
          >
            登录
          </button>
          <button
            v-else
            @click="logout"
            class="hidden sm:block max-w-[120px] truncate text-[12px] font-medium px-3 py-1.5 text-ink-soft hover:text-ink transition"
            :title="`${user?.account} · 退出登录`"
          >
            {{ user?.account }}
          </button>
          <button
            @click="openAdd"
            class="text-[12px] font-medium px-4 py-1.5 rounded-full bg-apple-blue text-white hover:bg-apple-blue2 transition whitespace-nowrap"
          >
            新增公司
          </button>
        </div>
      </div>
    </div>
  </header>
</template>
