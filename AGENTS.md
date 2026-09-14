# AI Liangxuan Agent Guide

## Repository Shape

- `frontend/` is the active Vue 3 + TypeScript + Vite application.
- `backend/` is currently empty. Do not assume an API, database, persistence, or server command exists.
- `backend/` now contains the Spring Boot 4.1 Maven API. Authentication uses JWT; registration and login accept an email or mainland China mobile number, and `POST /api/companies` requires a Bearer token.
- The project concept and initial setup notes are in [readme.txt](readme.txt).

## Commands

Run commands from `frontend/` with pnpm:

```text
pnpm install
pnpm dev
pnpm build
pnpm preview
```

`pnpm build` runs `vue-tsc --noEmit` before the Vite production build. There is no test script or test framework currently configured. If install blocks `esbuild` scripts, follow the approval workaround documented in [readme.txt](readme.txt).

Run the backend from `backend/` with Maven after configuring the variables in [`backend/.env.example`](backend/.env.example). Initialize MySQL with [`backend/src/main/resources/db/schema.sql`](backend/src/main/resources/db/schema.sql), then run `mvn spring-boot:run`.

## Frontend Architecture

- [App.vue](frontend/src/App.vue) composes the page from section and overlay components.
- Reusable UI belongs in `frontend/src/components/`; use the existing `<script setup lang="ts">` pattern and relative imports.
- Shared company-board state is a module-level singleton in [useCompanies.ts](frontend/src/composables/useCompanies.ts), including filtering, voting, submission, modal, and toast behavior.
- Domain types live in `frontend/src/types/`; seed data lives in `frontend/src/data/`; shared calculations and formatting live in `frontend/src/utils/`.
- Prefer existing Vue state and helper APIs over introducing a new store or state library for local features.

## UI and Styling Conventions

- Keep the existing Tailwind CSS plus global rules in [style.css](frontend/src/style.css) approach.
- Preserve the established Chinese product copy and visual language unless the task explicitly changes them.
- Reuse the existing component boundaries and inline SVG icon style rather than adding a new UI library for a small change.
- Keep generated output such as `frontend/dist/` out of source edits.

## Behavior and Data Notes

- Current company data and votes are in memory and reset on page reload.
- Website values may use `example.com` placeholders; do not treat them as real integrations.
- When changing form or voting behavior, update the owning composable and the closest component-level behavior together, then run `pnpm build`.
- Authentication state is shared through [useAuth.ts](frontend/src/composables/useAuth.ts); keep token persistence and logout behavior there rather than in individual components.

## Verification

- For frontend changes, run `cd frontend; pnpm build` from PowerShell (or the equivalent directory-aware command for the active shell).
- With no configured test runner, do not claim tests passed; report the build/type-check result instead.

## API REST & response rules
When creating or modifying HTTP API endpoints:
1. Follow Result<T> global response structure
2. Follow RESTful URL & HTTP method rules defined in .github/instructions/api-response.instructions.md
3. Do NOT customize response or pagination structure