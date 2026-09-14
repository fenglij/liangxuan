---
applyTo: "src/main/java/**/mapper/**/*.java"
description: "Mapper layer specification"
---
# Mapper Layer Rules
Extend BaseMapper from MyBatis-Plus 3.5.17.
Only database access, no business logic.
No N+1. Explicit column select instead of select *.
Complex SQL write in XML file.
