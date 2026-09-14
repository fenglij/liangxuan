<script setup lang="ts">
import { useCompanies } from '../composables/useCompanies'

const {
  showAdd,
  closeAdd,
  form,
  formError,
  startTimes,
  endTimes,
  previewParts,
  submitForm,
} = useCompanies()
</script>

<template>
  <transition name="fade">
    <div
      v-if="showAdd"
      class="fixed inset-0 z-[70] flex items-center justify-center p-4 sm:p-6"
    >
      <div @click="closeAdd" class="absolute inset-0 bg-black/40 backdrop-blur-sm"></div>
      <transition name="pop">
        <div
          v-if="showAdd"
          class="relative w-full max-w-lg rounded-5xl bg-white shadow-sheet overflow-hidden max-h-[92vh] overflow-y-auto board-scroll"
        >
          <div class="px-7 sm:px-9 pt-8 pb-4 flex items-start justify-between">
            <div>
              <h3 class="text-[24px] font-semibold tracking-tight">推荐一家公司</h3>
              <p class="text-ink-faint text-[13px] mt-1">填写真实信息，它会立刻出现在榜单中。</p>
            </div>
            <button
              @click="closeAdd"
              class="w-8 h-8 grid place-items-center rounded-full bg-mist text-ink-soft hover:bg-mist-300 transition shrink-0"
            >
              <svg fill="none" height="14" viewBox="0 0 24 24" width="14">
                <path
                  d="M6 6l12 12M18 6 6 18"
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-width="2.2"
                ></path>
              </svg>
            </button>
          </div>

          <div class="px-7 sm:px-9 pb-9 space-y-5">
            <div>
              <label class="text-[12px] font-medium text-ink-2"
                >公司名称 <span class="text-apple-red">*</span></label
              >
              <input
                v-model="form.name"
                class="mt-2 w-full bg-mist rounded-2xl px-4 py-3 text-[15px] outline-none border border-transparent focus:border-apple-blue/40 focus:bg-white transition placeholder:text-ink-faint"
                placeholder="例如：云枢科技"
              />
            </div>

            <div>
              <label class="text-[12px] font-medium text-ink-2">公司网址</label>
              <input
                v-model="form.website"
                class="mt-2 w-full bg-mist rounded-2xl px-4 py-3 text-[15px] outline-none border border-transparent focus:border-apple-blue/40 focus:bg-white transition placeholder:text-ink-faint"
                placeholder="https://example.com"
              />
            </div>

            <div>
              <label class="text-[12px] font-medium text-ink-2">代表产品 / 业务</label>
              <textarea
                v-model="form.product"
                rows="2"
                class="mt-2 w-full bg-mist rounded-2xl px-4 py-3 text-[15px] outline-none border border-transparent focus:border-apple-blue/40 focus:bg-white transition resize-none placeholder:text-ink-faint"
                placeholder="一句话介绍它最值得被购买的产品"
              ></textarea>
            </div>

            <div class="grid grid-cols-3 gap-3">
              <div>
                <label class="text-[12px] font-medium text-ink-2">上班点</label>
                <div class="relative mt-2">
                  <select
                    v-model="form.start"
                    class="w-full bg-mist rounded-2xl px-3.5 py-3 text-[15px] outline-none border border-transparent focus:border-apple-blue/40 focus:bg-white transition"
                  >
                    <option v-for="t in startTimes" :key="t" :value="t">{{ t }}</option>
                  </select>
                  <svg
                    class="absolute right-3 top-1/2 -translate-y-1/2 text-ink-faint pointer-events-none"
                    fill="none"
                    height="11"
                    viewBox="0 0 24 24"
                    width="11"
                  >
                    <path
                      d="m6 9 6 6 6-6"
                      stroke="currentColor"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                    ></path>
                  </svg>
                </div>
              </div>

              <div>
                <label class="text-[12px] font-medium text-ink-2">下班点</label>
                <div class="relative mt-2">
                  <select
                    v-model="form.end"
                    class="w-full bg-mist rounded-2xl px-3.5 py-3 text-[15px] outline-none border border-transparent focus:border-apple-blue/40 focus:bg-white transition"
                  >
                    <option v-for="t in endTimes" :key="t" :value="t">{{ t }}</option>
                  </select>
                  <svg
                    class="absolute right-3 top-1/2 -translate-y-1/2 text-ink-faint pointer-events-none"
                    fill="none"
                    height="11"
                    viewBox="0 0 24 24"
                    width="11"
                  >
                    <path
                      d="m6 9 6 6 6-6"
                      stroke="currentColor"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                    ></path>
                  </svg>
                </div>
              </div>

              <div>
                <label class="text-[12px] font-medium text-ink-2">周工作天数</label>
                <div class="relative mt-2">
                  <select
                    v-model.number="form.days"
                    class="w-full bg-mist rounded-2xl px-3.5 py-3 text-[15px] outline-none border border-transparent focus:border-apple-blue/40 focus:bg-white transition"
                  >
                    <option v-for="d in 7" :key="d" :value="d">{{ d }} 天</option>
                  </select>
                  <svg
                    class="absolute right-3 top-1/2 -translate-y-1/2 text-ink-faint pointer-events-none"
                    fill="none"
                    height="11"
                    viewBox="0 0 24 24"
                    width="11"
                  >
                    <path
                      d="m6 9 6 6 6-6"
                      stroke="currentColor"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                    ></path>
                  </svg>
                </div>
              </div>
            </div>

            <!-- tag preview -->
            <div class="rounded-3xl bg-mist px-5 py-4 flex items-center justify-between">
              <div>
                <div class="text-[11px] font-medium text-ink-faint tracking-[.14em] uppercase">
                  自动生成标签
                </div>
                <div class="text-[12px] text-ink-soft mt-1">
                  <span class="text-apple-blue font-medium">上班点</span> ·
                  <span class="text-apple-orange font-medium">下班点</span> ·
                  <span class="text-apple-purple font-medium">工作天数</span>
                </div>
              </div>
              <span
                class="pin-tag text-xl font-bold px-4 py-1.5 rounded-xl bg-white mono tracking-wide"
              >
                <span class="text-apple-blue">{{ previewParts.s }}</span
                ><span class="text-apple-orange">{{ previewParts.e }}</span
                ><span class="text-apple-purple">{{ previewParts.d }}</span>
              </span>
            </div>

            <p v-if="formError" class="text-[12px] text-apple-redd flex items-center gap-1.5">
              <svg fill="none" height="13" viewBox="0 0 24 24" width="13">
                <circle cx="12" cy="12" r="9" stroke="currentColor" stroke-width="2"></circle>
                <path
                  d="M12 7v6M12 16.5v.5"
                  stroke="currentColor"
                  stroke-linecap="round"
                  stroke-width="2"
                ></path>
              </svg>
              {{ formError }}
            </p>

            <div class="flex items-center gap-3 pt-1">
              <button
                @click="submitForm"
                class="flex-1 py-3.5 rounded-full bg-apple-blue text-white font-medium text-[15px] hover:bg-apple-blue2 transition"
              >
                提交并上榜
              </button>
              <button
                @click="closeAdd"
                class="px-7 py-3.5 rounded-full bg-mist text-ink-2 text-[15px] font-medium hover:bg-mist-300 transition"
              >
                取消
              </button>
            </div>
          </div>
        </div>
      </transition>
    </div>
  </transition>
</template>
