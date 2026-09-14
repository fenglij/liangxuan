---
applyTo: "src/main/java/**/service/**/*.java"
description: "Service layer specification"
---
# Service Layer Rules
Put all business logic here.
Every Service must be defined as an interface, with its implementation in a same-package `*ServiceImpl` class.
Annotate only the `*ServiceImpl` class with `@Service`; Controllers and other layers must depend on the Service interface, never the implementation class.
Keep the interface focused on public business operations and keep implementation details, private helpers, and dependency fields in `*ServiceImpl`.
Use @Transactional for transaction, only on public method.
Check business precondition, throw BizException for business error.
Call Mapper or Forest remote client. Assemble DTO/VO.
Do not write HTTP response logic here.
