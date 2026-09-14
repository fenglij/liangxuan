---
applyTo: "src/main/java/**/controller/**/*.java"
description: "Controller layer specification"
---
# Controller Layer Rules
Controller only handles http entry, parameter validation, call service, return ApiResponse<T>.
No business logic, no database access.
Use jakarta validation annotation for parameter check (@NotBlank, @NotNull etc).
Do not catch business exception here, delegate to global exception handler.
Add api doc annotation for api description.
