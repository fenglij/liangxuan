一个"反内卷"主题的网站。核心主题是倡导大家购买反内卷公司的产品，助力这类公司经营更好，达到良币驱逐劣币的目标。

========================================================
前端
```
cd D:\WorkSpace\VScode\ai-liangxuan\frontend
pnpm install
pnpm dev
```

> 注：pnpm v12 首次 install 时会提示 `esbuild` 构建脚本被忽略（安全策略），运行 `pnpm approve-builds` 选择 esbuild 即可，或直接 `node node_modules/.pnpm/esbuild@0.21.5/node_modules/esbuild/install.js` 手动安装二进制。当前项目已预装完成，`pnpm dev` 可直接运行。