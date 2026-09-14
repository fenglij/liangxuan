<script setup lang="ts">
import { useCompanies } from '../composables/useCompanies'

const { pillars } = useCompanies()
</script>

<template>
  <section class="py-24 sm:py-36" id="why">
    <div class="max-w-[1024px] mx-auto px-6">
      <div class="reveal max-w-3xl">
        <div
          class="text-[12px] font-medium tracking-[.02em] text-apple-green inline-flex items-center gap-2"
        >
          <span class="w-1.5 h-1.5 rounded-full bg-apple-green"></span>为什么投票
        </div>
        <h2
          class="mt-6 text-[2rem] sm:text-[3.25rem] font-semibold tracking-[-.02em] leading-[1.08]"
        >
          每一次购买，<br />都是一次<span class="text-apple-green">投票</span>。
        </h2>
        <p class="mt-7 text-[17px] sm:text-[21px] leading-relaxed text-ink-soft">
          内卷不会因为抱怨而消失，却会因为市场的选择而改变。当我们把消费力投向那些尊重员工时间、拒绝无意义加班的公司，它们的利润表就会更好看、招聘更有吸引力、行业话语权更强。良币驱逐劣币，从来不是一句口号，而是千万次选择的总和。
        </p>
      </div>
      <div class="grid sm:grid-cols-3 gap-5 mt-14">
        <div
          v-for="(p, i) in pillars"
          :key="i"
          :style="{ animationDelay: 0.15 + i * 0.12 + 's' }"
          class="reveal rounded-4xl bg-mist p-8"
        >
          <div class="text-3xl font-semibold tracking-tight">{{ p.n }}</div>
          <div class="text-[15px] text-ink-soft mt-3 leading-relaxed">{{ p.t }}</div>
        </div>
      </div>
    </div>
  </section>
</template>
