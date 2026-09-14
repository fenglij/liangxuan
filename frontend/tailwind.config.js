/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  theme: {
    extend: {
      fontFamily: {
        sans: [
          '-apple-system',
          'BlinkMacSystemFont',
          '"SF Pro Display"',
          'Inter',
          '"PingFang SC"',
          '"Noto Sans SC"',
          '"Helvetica Neue"',
          'sans-serif',
        ],
      },
      colors: {
        apple: {
          blue: '#0071e3',
          blue2: '#0066cc',
          orange: '#ff9500',
          purple: '#af52de',
          green: '#34c759',
          greend: '#248a3d',
          red: '#ff3b30',
          redd: '#c9302c',
        },
        ink: {
          DEFAULT: '#1d1d1f',
          2: '#424245',
          soft: '#6e6e73',
          faint: '#86868b',
        },
        mist: {
          DEFAULT: '#f5f5f7',
          200: '#e8e8ed',
          300: '#d2d2d7',
        },
      },
      boxShadow: {
        card: '0 4px 24px -12px rgba(0,0,0,.14)',
        lift: '0 18px 50px -20px rgba(0,0,0,.28)',
        sheet: '0 40px 90px -30px rgba(0,0,0,.45)',
      },
      borderRadius: {
        '4xl': '2rem',
        '5xl': '2.6rem',
      },
    },
  },
  plugins: [],
}
