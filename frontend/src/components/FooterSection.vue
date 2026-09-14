<script setup lang="ts">
import { useCompanies } from '../composables/useCompanies'

const { openAdd } = useCompanies()
</script>

<template>
  <footer class="bg-mist border-t border-black/[.07]">
    <div class="max-w-[1024px] mx-auto px-6 py-12">
      <div class="grid sm:grid-cols-3 gap-8 pb-8 border-b border-black/[.07]">
        <div>
          <div class="text-[13px] font-semibold">关于良选集市</div>
          <p class="text-[12px] text-ink-faint mt-3 leading-relaxed">
            让认真对待员工的公司，被更多人看见与选择。用消费力推动良币驱逐劣币。
          </p>
        </div>
        <div>
          <div class="text-[13px] font-semibold">榜单</div>
          <ul class="text-[12px] text-ink-faint mt-3 space-y-2">
            <li><a class="hover:text-ink transition" href="#green">绿榜 · 值得支持</a></li>
            <li><a class="hover:text-ink transition" href="#red">红榜 · 内卷典型</a></li>
            <li><a class="hover:text-ink transition" href="#boards">实时排名</a></li>
          </ul>
        </div>
        <div>
          <div class="text-[13px] font-semibold">参与</div>
          <ul class="text-[12px] text-ink-faint mt-3 space-y-2">
            <li>
              <button @click="openAdd" class="hover:text-ink transition">推荐一家公司</button>
            </li>
            <li><a class="hover:text-ink transition" href="#why">为什么投票</a></li>
          </ul>
        </div>
      </div>
      <div
        class="pt-6 flex flex-col sm:flex-row justify-between gap-3 text-[12px] text-ink-faint"
      >
        <span>© 2026 良选集市 · 数据来自游客共同投票</span>
        <span>榜单仅代表游客观点 · 排名实时变动</span>
      </div>
    </div>
  </footer>
</template>
