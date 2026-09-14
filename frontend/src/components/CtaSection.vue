<script setup lang="ts">
import { useCompanies } from '../composables/useCompanies'

const { openAdd } = useCompanies()
</script>

<template>
  <section class="bg-mist py-24 sm:py-32">
    <div class="max-w-[1024px] mx-auto px-6 text-center reveal">
      <h2 class="text-[2rem] sm:text-[2.75rem] font-semibold tracking-tight leading-tight">
        你心中有一家值得推荐的公司吗？
      </h2>
      <p class="mt-4 text-[17px] text-ink-soft">填写真实信息，让它被更多人看见与选择。</p>
      <div class="mt-9 flex flex-wrap items-center justify-center gap-4">
        <button
          @click="openAdd"
          class="px-7 py-3.5 rounded-full bg-apple-blue text-white text-[16px] font-medium hover:bg-apple-blue2 transition"
        >
          推荐一家公司
        </button>
        <a
          class="px-7 py-3.5 rounded-full text-apple-blue text-[16px] font-medium hover:underline inline-flex items-center gap-1"
          href="#boards"
        >
          查看榜单 <span class="text-lg leading-none">›</span>
        </a>
      </div>
    </div>
  </section>
</template>
