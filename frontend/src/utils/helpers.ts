import type { Company } from '../types'

/** 取公司名首字 */
export function initial(n: string): string {
  return (n || '?').trim().slice(0, 1)
}

/** 数字补零 */
export function pad(n: number): string {
  return n < 10 ? '0' + n : '' + n
}

/** 从 URL 提取域名 */
export function host(u: string): string {
  try {
    return new URL(u).hostname.replace('www.', '')
  } catch {
    return u || '—'
  }
}

/** 根据公司名生成渐变头像样式 */
export function avatarStyle(c: Company): Record<string, string> {
  const hash = [...c.name].reduce((a, ch) => a + ch.charCodeAt(0), 0)
  const pairs: [string, string][] = [
    ['#34c759', '#248a3d'],
    ['#30d158', '#0e7a4a'],
    ['#ff9f0a', '#c76a00'],
    ['#5ac8fa', '#0071e3'],
    ['#bf7ddb', '#af52de'],
  ]
  const p = pairs[hash % pairs.length]
  return { backgroundImage: `linear-gradient(135deg, ${p[0]}, ${p[1]})` }
}

/** 标签数字：上班点 / 下班点(12小时制) / 工作天数 */
export function tagDigits(c: Company): { s: number; e: number; d: number } {
  const s = parseInt(c.start, 10) || 9
  let e = parseInt(c.end, 10) || 18
  if (e > 12) e -= 12
  return { s, e, d: c.days }
}

/** 净支持度 = 点赞 - 点踩 */
export function net(c: Company): number {
  return (c.likes || 0) - (c.dislikes || 0)
}

/** 点赞/点踩进度条百分比 */
export function bar(c: Company): { g: string; r: string } {
  const total = (c.likes || 0) + (c.dislikes || 0)
  if (total === 0) return { g: '0', r: '0' }
  return {
    g: ((c.likes / total) * 100).toFixed(1),
    r: ((c.dislikes / total) * 100).toFixed(1),
  }
}
