export interface Company {
  id: string
  name: string
  website: string
  product: string
  start: string
  end: string
  days: number
  likes: number
  dislikes: number
}

export type VoteType = 'like' | 'dislike'

export interface Filter {
  k: string
  label: string
}

export interface Stat {
  label: string
  value: string | number
  color: string
}

export interface Pillar {
  n: string
  t: string
}

export interface CompanyForm {
  name: string
  website: string
  product: string
  start: string
  end: string
  days: number
}
