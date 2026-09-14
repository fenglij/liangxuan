const MAX_SAFE_INTEGER = BigInt(Number.MAX_SAFE_INTEGER)

export function parseJson<T>(text: string): T {
  return JSON.parse(protectLargeIntegers(text)) as T
}

function protectLargeIntegers(text: string): string {
  let result = ''
  let index = 0
  let inString = false
  let escaped = false

  while (index < text.length) {
    const character = text[index]
    if (inString) {
      result += character
      if (escaped) escaped = false
      else if (character === '\\') escaped = true
      else if (character === '"') inString = false
      index += 1
      continue
    }
    if (character === '"') {
      inString = true
      result += character
      index += 1
      continue
    }
    if (character === '-' || (character >= '0' && character <= '9')) {
      const match = text.slice(index).match(/^-?\d+(?:\.\d+)?(?:[eE][+-]?\d+)?/)
      if (match) {
        const token = match[0]
        if (/^-?\d+$/.test(token) && BigInt(token) > MAX_SAFE_INTEGER || /^-\d+$/.test(token) && BigInt(token) < -MAX_SAFE_INTEGER) {
          result += `"${token}"`
        } else {
          result += token
        }
        index += token.length
        continue
      }
    }
    result += character
    index += 1
  }
  return result
}