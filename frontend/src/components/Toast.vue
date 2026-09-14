<script setup lang="ts">
import { useCompanies } from '../composables/useCompanies'

const { toast } = useCompanies()
</script>

<template>
  <transition name="fade">
    <div
      v-if="toast"
      class="fixed bottom-8 left-1/2 -translate-x-1/2 z-[80] bg-ink text-white rounded-full pl-3.5 pr-5 py-3 text-[13px] flex items-center gap-2.5 shadow-sheet"
    >
      <span class="w-6 h-6 grid place-items-center rounded-full bg-apple-green">
        <svg fill="none" height="12" viewBox="0 0 24 24" width="12">
          <path
            d="m5 13 4 4L19 7"
            stroke="#fff"
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2.4"
          ></path>
        </svg>
      </span>
      {{ toast }}
    </div>
  </transition>
</template>
