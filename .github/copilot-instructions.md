# Project Global Instructions for GitHub Copilot
## Tech Stack
Java 25, SpringBoot 4.1, MyBatis-Plus 3.5.17, Lombok, MySQL 8, Forest 1.8.1, Jackson 3.
Use SpringBoot4's native virtual thread support where appropriate.
Jackson 3 package is `tools.jackson`, NOT old com.fasterxml.jackson. Use JsonMapper instead of ObjectMapper for Jackson3.

## Architecture & Package Layer Rules
Standard 4-layer architecture: Controller → Service → Mapper → Entity/DO
1. Controller: Only do parameter validation, request deserialization, invoke Service, return unified ApiResponse<T>.
   NO business logic inside Controller. Do NOT write SQL / DB access in Controller.
2. Service: Business logic, transaction control, assemble DTO/VO, call Mapper or Forest remote client.
3. Mapper: MyBatis-Plus BaseMapper, pure DB access, avoid complex business logic.
4. Entity(DO): Database table mapping, use Lombok annotations, @TableName, @TableId, logic delete field.

## Lombok Rules
Use Lombok to reduce boilerplate. Preferred annotations: @Data, @NoArgsConstructor, @AllArgsConstructor, @Builder.
Avoid mixing @Data + @ToString(callSuper=true) unless explicitly required.
Do NOT manually write getter/setter when Lombok can generate them.

## MyBatis-Plus 3.5.17 & MySQL8 Rules
1. Use mybatis-plus-spring-boot4-starter.
2. Use logical delete via MP global config, do not write `is_deleted=0` manually everywhere.
3. Strictly forbid N+1 query. Use MP join wrapper or custom XML for join queries.
4. Page query use Page<> object from MyBatis-Plus, do NOT implement pagination manually.
5. DO NOT write `select *`, explicitly list columns.
6. Entity use Long for primary key, use @TableId.
7. Avoid heavy SQL inside mapper interface; complex SQL put in XML.

## Forest 1.8.1 Remote Call Rules
Forest for third-party HTTP API invoke.
1. Define Forest interface annotated @ForestClient, configure baseUrl via configuration properties.
2. Add retry, timeout, exception handling for remote invoke.
3. Use Jackson3 for Forest request/response serialization.
4. Catch remote API exceptions, log request/response detail with traceId, do NOT swallow exception silently.
5. Never hardcode remote url, ak, sk, token in code; use spring config / nacos.

## Jackson3 Rules
Jackson3, package `tools.jackson`. Use JsonMapper.
1. Configure JavaTimeModule for LocalDateTime / LocalDate.
2. Date format: yyyy-MM-dd HH:mm:ss.
3. Avoid old Jackson2 `com.fasterxml.jackson` imports.
4. Custom serializer/deserializer register via @JacksonComponent.

## Return & Exception Specification
- All HTTP API return unified `ApiResponse<T>`. Code, msg, data, traceId.
- Use custom business exception `BizException`, avoid generic RuntimeException directly.
- Global @RestControllerAdvice handles exception, do not write duplicated try-catch in every controller.
- Log with @Slf4j (SLF4J), NEVER use System.out / printStackTrace.
- Log sensitive data masking: mobile, idcard, token must be masked.

## Naming & Code Style
- Class: PascalCase, variable/method: camelCase, constant: UPPER_SNAKE_CASE.
- Magic number / magic string must be extracted to Constant or Enum.
- Use Java25 features appropriately (virtual thread, pattern matching, records for simple DTO if suitable).
- Single responsibility: method only do one thing, keep method short.

## Testing Rule
Unit test use JUnit5 + Mockito.
- Test Service layer first, mock Mapper / Forest client.
- Cover normal, null, empty, exception boundary cases.
- Put test in src/test/java, same package structure as main code.

## ❌ Forbidden (Hard Rules)
1. No hardcode password, secret, ak/sk, token, database connection string.
2. No dangerous SQL: drop, truncate, delete without where.
3. No N+1 queries, no SQL inside loop.
4. Do NOT invent non-existing class, method, repository. Check existing code before writing.
5. Do NOT use JPA, MyBatis starter for SB2/SB3.
6. Do NOT import old Jackson2 `com.fasterxml.jackson` packages.
7. Do NOT push sensitive information into code or comment.

## Documentation
Add Javadoc for public method: purpose, param, return, throws.
Comment only for complex business logic; do not repeat obvious code.

## API REST & response rules
```markdown
See .github/instructions/api-response.instructions.md. All controllers must follow Result<T> structure and REST rules defined there.
```

## Environment & Database Configuration Rules
1. Use `${ENV_VAR:default}` placeholder in application-*.yml to read **environment variables**.
2. Database url, username, password, redis credentials MUST NOT be hard-coded in any committed yml files.
3. Local development may use .env file at project root to load environment variables. The .env file must be added to .gitignore and NEVER committed to git.
4. .env is for local development ONLY. Production environment uses Nacos or K8s Secret instead of .env.
5. spring-dotenv is a runtime scope dependency only; do NOT load .env in production.
6. MAVEN_HOME is D:\Soft\Install\Dev\Maven\apache-maven-3.8.1