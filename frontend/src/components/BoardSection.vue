<script setup lang="ts">
import { useCompanies } from '../composables/useCompanies'
import CompanyCard from './CompanyCard.vue'

const { greenList, redList } = useCompanies()
</script>

<template>
  <section class="bg-mist py-20 sm:py-28" id="boards">
    <div class="max-w-[1120px] mx-auto px-6">
      <div class="text-center max-w-2xl mx-auto mb-16 reveal">
        <h2 class="text-[2rem] sm:text-[2.75rem] font-semibold tracking-tight leading-tight">
          实时口碑榜单
        </h2>
        <p class="mt-4 text-[17px] text-ink-soft">
          票数实时流动，每一票都在重新排序这座城市的良心。
        </p>
      </div>

      <div class="grid lg:grid-cols-2 gap-6">
        <!-- GREEN -->
        <div class="reveal rounded-4xl bg-white shadow-card overflow-hidden" id="green">
          <div class="px-7 py-6 flex items-center justify-between border-b border-black/5">
            <div class="flex items-center gap-3.5">
              <span
                class="w-10 h-10 rounded-2xl grid place-items-center bg-apple-green/12 text-apple-greend"
              >
                <svg fill="none" height="18" viewBox="0 0 24 24" width="18">
                  <path
                    d="M7 11v9H4a1 1 0 0 1-1-1v-7a1 1 0 0 1 1-1h3Zm0 0 4.5-8a2 2 0 0 1 3.7 1.4L14 9h4.6a2 2 0 0 1 1.96 2.4l-1.2 6A2 2 0 0 1 17.4 19H7"
                    stroke="currentColor"
                    stroke-linejoin="round"
                    stroke-width="1.7"
                  ></path>
                </svg>
              </span>
              <div>
                <div class="text-[17px] font-semibold tracking-tight">绿榜</div>
                <div class="text-[11px] text-ink-faint tracking-[.14em] uppercase mt-0.5">
                  Good Money
                </div>
              </div>
            </div>
            <div class="text-right">
              <div class="text-2xl font-semibold mono text-apple-greend">
                {{ greenList.length }}
              </div>
              <div class="text-[10px] text-ink-faint tracking-[.16em] uppercase">家上榜</div>
            </div>
          </div>
          <div class="p-4 sm:p-5">
            <div
              v-if="greenList.length === 0"
              class="py-20 text-center text-ink-faint text-sm"
            >
              没有匹配的绿榜公司
            </div>
            <transition-group class="space-y-3" name="list" tag="div">
              <CompanyCard
                v-for="(c, idx) in greenList"
                :key="c.id"
                :company="c"
                :index="idx"
                board-type="green"
              />
            </transition-group>
          </div>
        </div>

        <!-- RED -->
        <div
          class="reveal rounded-4xl bg-white shadow-card overflow-hidden"
          id="red"
          style="transition-delay: 0.1s"
        >
          <div class="px-7 py-6 flex items-center justify-between border-b border-black/5">
            <div class="flex items-center gap-3.5">
              <span
                class="w-10 h-10 rounded-2xl grid place-items-center bg-apple-red/10 text-apple-redd"
              >
                <svg fill="none" height="18" viewBox="0 0 24 24" width="18">
                  <path
                    d="M12 3c.6 2.6-.4 4.2-1.8 5.6C8.5 10.3 7 11.7 7 14.2A5 5 0 0 0 17 15c.6-2.2-.5-3.7-1.4-5.1.9.3 1.7.9 2.2 1.7C18.4 9.7 16.6 5.2 12 3Z"
                    stroke="currentColor"
                    stroke-linejoin="round"
                    stroke-width="1.7"
                  ></path>
                </svg>
              </span>
              <div>
                <div class="text-[17px] font-semibold tracking-tight">红榜</div>
                <div class="text-[11px] text-ink-faint tracking-[.14em] uppercase mt-0.5">
                  Watch Out
                </div>
              </div>
            </div>
            <div class="text-right">
              <div class="text-2xl font-semibold mono text-apple-redd">
                {{ redList.length }}
              </div>
              <div class="text-[10px] text-ink-faint tracking-[.16em] uppercase">家上榜</div>
            </div>
          </div>
          <div class="p-4 sm:p-5">
            <div
              v-if="redList.length === 0"
              class="py-20 text-center text-ink-faint text-sm"
            >
              没有匹配的红榜公司
            </div>
            <transition-group class="space-y-3" name="list" tag="div">
              <CompanyCard
                v-for="(c, idx) in redList"
                :key="c.id"
                :company="c"
                :index="idx"
                board-type="red"
              />
            </transition-group>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>
