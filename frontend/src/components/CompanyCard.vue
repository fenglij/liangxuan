<script setup lang="ts">
import type { Company } from '../types'
import { useCompanies } from '../composables/useCompanies'
import { initial, pad, host, avatarStyle, tagDigits, bar } from '../utils/helpers'

defineProps<{
  company: Company
  index: number
  boardType: 'green' | 'red'
}>()

const { vote, myVote } = useCompanies()
</script>

<template>
  <article
    class="group relative rounded-3xl p-5 border border-black/[.06] hover:border-black/10 hover:shadow-card transition bg-white"
  >
    <span
      class="absolute right-5 top-3 text-5xl font-bold text-black/[.035] mono select-none"
    >
      {{ pad(index + 1) }}
    </span>
    <div class="relative flex items-start gap-4">
      <div
        :style="avatarStyle(company)"
        class="w-11 h-11 shrink-0 rounded-2xl grid place-items-center text-white font-semibold text-lg"
      >
        {{ initial(company.name) }}
      </div>
      <div class="min-w-0 flex-1 pr-8">
        <div class="flex items-center gap-2 flex-wrap">
          <h3 class="font-semibold text-[16px] tracking-tight truncate">
            {{ company.name }}
          </h3>
          <span
            class="pin-tag text-[11px] font-bold px-2 py-[3px] rounded-md bg-mist mono tracking-wide"
          >
            <span class="text-apple-blue">{{ tagDigits(company).s }}</span
            ><span class="text-apple-orange">{{ tagDigits(company).e }}</span
            ><span class="text-apple-purple">{{ tagDigits(company).d }}</span>
          </span>
        </div>
        <p class="text-[13px] text-ink-soft mt-1 line-clamp-2 leading-relaxed">
          {{ company.product }}
        </p>
        <a
          :href="company.website"
          class="inline-flex items-center gap-1.5 text-[12px] text-apple-blue hover:underline mt-2"
          rel="noopener"
          target="_blank"
        >
          <svg fill="none" height="12" viewBox="0 0 24 24" width="12">
            <circle cx="12" cy="12" r="9" stroke="currentColor" stroke-width="1.6"></circle>
            <path
              d="M3 12h18M12 3c3 3.5 3 14.5 0 18M12 3c-3 3.5-3 14.5 0 18"
              stroke="currentColor"
              stroke-width="1.2"
            ></path>
          </svg>
          {{ host(company.website) }}
        </a>
      </div>
    </div>

    <div class="relative mt-4">
      <div class="flex items-center justify-between text-[11px] mb-1.5">
        <span
          :class="
            boardType === 'green' ? 'text-apple-greend font-medium' : 'text-ink-faint'
          "
          class="mono"
        >
          {{ company.likes }} 点赞
        </span>
        <span
          :class="
            boardType === 'red' ? 'text-apple-redd font-medium' : 'text-ink-faint'
          "
          class="mono"
        >
          {{ company.dislikes }} 点踩
        </span>
      </div>
      <div class="h-1 rounded-full bg-mist overflow-hidden flex">
        <div
          :style="{ width: bar(company).g + '%' }"
          :class="boardType === 'green' ? 'bg-apple-green' : 'bg-apple-green/70'"
          class="h-full transition-all duration-500"
        ></div>
        <div
          :style="{ width: bar(company).r + '%' }"
          :class="boardType === 'red' ? 'bg-apple-red' : 'bg-apple-red/70'"
          class="h-full transition-all duration-500"
        ></div>
      </div>
    </div>

    <div class="relative mt-4 flex items-center gap-2.5">
      <button
        @click="vote(company, 'like')"
        :class="
          myVote(company) === 'like'
            ? 'bg-apple-green text-white border-apple-green'
            : boardType === 'green'
              ? 'text-apple-greend border-black/11 hover:bg-apple-green/8'
              : 'text-ink-soft border-black/11 hover:bg-black/[.03]'
        "
        class="flex-1 flex items-center justify-center gap-2 py-2.5 rounded-full border text-[13px] font-medium transition"
      >
        <svg
          :stroke="myVote(company) === 'like' ? '#fff' : 'currentColor'"
          fill="none"
          height="15"
          stroke-linejoin="round"
          stroke-width="1.9"
          viewBox="0 0 24 24"
          width="15"
        >
          <path
            d="M7 11v9H4a1 1 0 0 1-1-1v-7a1 1 0 0 1 1-1h3Zm0 0 4.5-8a2 2 0 0 1 3.7 1.4L14 9h4.6a2 2 0 0 1 1.96 2.4l-1.2 6A2 2 0 0 1 17.4 19H7"
          ></path>
        </svg>
        {{ myVote(company) === 'like' ? '已点赞' : '点赞' }}
      </button>
      <button
        @click="vote(company, 'dislike')"
        :class="
          myVote(company) === 'dislike'
            ? 'bg-apple-red text-white border-apple-red'
            : boardType === 'red'
              ? 'text-apple-redd border-black/11 hover:bg-apple-red/8'
              : 'text-ink-soft border-black/11 hover:bg-black/[.03]'
        "
        class="flex-1 flex items-center justify-center gap-2 py-2.5 rounded-full border text-[13px] font-medium transition"
      >
        <svg
          :stroke="myVote(company) === 'dislike' ? '#fff' : 'currentColor'"
          fill="none"
          height="15"
          stroke-linejoin="round"
          stroke-width="1.9"
          viewBox="0 0 24 24"
          width="15"
        >
          <path
            d="M17 13V4h3a1 1 0 0 1 1 1v7a1 1 0 0 1-1 1h-3Zm0 0-4.5 8a2 2 0 0 1-3.7-1.4L10 15H5.4a2 2 0 0 1-1.96-2.4l1.2-6A2 2 0 0 1 6.6 5H17"
          ></path>
        </svg>
        {{ myVote(company) === 'dislike' ? '已点踩' : '点踩' }}
      </button>
    </div>
  </article>
</template>
