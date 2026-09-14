<script setup lang="ts">
import { useCompanies } from '../composables/useCompanies'

const { query, activeFilter, filters, stats } = useCompanies()
</script>

<template>
  <section
    class="relative overflow-hidden pt-32 sm:pt-40 pb-20 sm:pb-28"
    id="top"
  >
    <div
      class="absolute inset-x-0 top-0 -z-10 h-[560px] pointer-events-none"
      style="
        background: radial-gradient(
          120% 80% at 50% 0%,
          #f0faf3 0%,
          rgba(240, 250, 243, 0) 60%
        );
      "
    ></div>
    <div
      class="absolute -top-24 -right-24 w-[34rem] h-[34rem] rounded-full -z-10 floaty"
      style="background: radial-gradient(circle, rgba(52, 199, 89, 0.12), transparent 62%)"
    ></div>
    <div class="max-w-[1024px] mx-auto px-6 text-center">
      <div
        class="rise text-[12px] font-medium tracking-[.02em] text-apple-green inline-flex items-center gap-2"
      >
        <span class="w-1.5 h-1.5 rounded-full bg-apple-green"></span>
        良币驱逐劣币 · 用消费投票
      </div>
      <h1
        class="rise mt-6 text-[2.6rem] leading-[1.05] sm:text-6xl lg:text-[4.6rem] font-semibold tracking-[-.025em]"
        style="animation-delay: 0.05s"
      >
        把钱花在<br class="sm:hidden" />值得的公司身上。
      </h1>
      <p
        class="rise mt-6 mx-auto max-w-2xl text-[17px] sm:text-[21px] leading-relaxed text-ink-soft font-normal"
        style="animation-delay: 0.12s"
      >
        拒绝内卷，从选择开始。为坚持体面作息的公司点赞，购买它们的产品 ——
        让认真做事的公司活得更好。
      </p>

      <!-- search -->
      <div class="rise mt-10 max-w-2xl mx-auto" style="animation-delay: 0.18s">
        <div class="relative">
          <svg
            class="absolute left-5 top-1/2 -translate-y-1/2 text-ink-faint"
            fill="none"
            height="18"
            viewBox="0 0 24 24"
            width="18"
          >
            <circle cx="11" cy="11" r="7" stroke="currentColor" stroke-width="1.8"></circle>
            <path
              d="m20 20-3.5-3.5"
              stroke="currentColor"
              stroke-linecap="round"
              stroke-width="1.8"
            ></path>
          </svg>
          <input
            v-model="query"
            class="w-full pl-12 pr-12 h-[54px] rounded-full bg-mist border border-transparent focus:border-apple-blue/40 focus:bg-white outline-none text-[17px] placeholder:text-ink-faint transition"
            placeholder="搜索公司名称或产品"
            type="text"
          />
          <button
            v-if="query"
            @click="query = ''"
            class="absolute right-4 top-1/2 -translate-y-1/2 w-7 h-7 grid place-items-center rounded-full bg-mist-300/60 text-ink-soft hover:bg-mist-300 transition"
          >
            <svg fill="none" height="11" viewBox="0 0 24 24" width="11">
              <path
                d="M6 6l12 12M18 6 6 18"
                stroke="currentColor"
                stroke-linecap="round"
                stroke-width="2.4"
              ></path>
            </svg>
          </button>
        </div>

        <!-- segmented control -->
        <div class="mt-5 inline-flex p-1 rounded-full bg-mist border border-black/5">
          <button
            v-for="f in filters"
            :key="f.k"
            @click="activeFilter = f.k"
            :class="
              activeFilter === f.k
                ? 'bg-white text-ink shadow-card'
                : 'text-ink-soft hover:text-ink'
            "
            class="px-5 py-1.5 rounded-full text-[13px] font-medium transition"
          >
            {{ f.label }}
          </button>
        </div>
      </div>

      <!-- stats -->
      <div
        class="rise mt-16 grid grid-cols-2 md:grid-cols-4 gap-y-10"
        style="animation-delay: 0.24s"
      >
        <div v-for="(s, i) in stats" :key="i" class="px-4">
          <div :class="s.color" class="text-4xl sm:text-5xl font-semibold tracking-tight mono">
            {{ s.value }}
          </div>
          <div class="text-[12px] text-ink-faint mt-2 tracking-wide">{{ s.label }}</div>
        </div>
      </div>
    </div>
  </section>
</template>
