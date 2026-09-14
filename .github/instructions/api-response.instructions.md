---
applyTo: "src/main/java/**/controller/**/*.java,src/main/java/**/dto/**/*.java"
description: "REST API & Global Result Response Specification"
---
# REST API & Global Result Response Specification

## 1. Unified Global Response Structure
### 1.1 General Success Response
Use fixed `Result<T>` structure globally. Frontend and backend MUST NOT customize response fields.
```json
{
  "code": 200,
  "message": "success",
  "data": {},
  "traceId": "uuid"
}
```

### 1.2 Standard Paginated Response
All list and paginated queries MUST use this structure. Custom pagination fields are forbidden.
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "pageNum": 1,
    "pageSize": 20,
    "total": 128,
    "records": []
  },
  "traceId": "uuid"
}
```

### 1.3 Standard Exception Response
```json
{
  "code": business error code,
  "message": "i18n friendly prompt message",
  "data": null,
  "traceId": "uuid"
}
```
Constraints: HTTP status code always returns 200. Business errors are distinguished by the internal `code` field, for unified frontend interception logic.

## 2. RESTful API Design Specification
### 2.1 HTTP Method Semantics
- **GET**: Query data (list, detail, pagination). No side effects, cacheable.
- **POST**: Create new business data or complex query.
- **PUT**: Full update of business data.
- **PATCH**: Partial field update.
- **DELETE**: Logical deletion only. Physical deletion is forbidden in production.

### 2.2 URL Path Rules
- All lowercase, use kebab-case (hyphen separated).
- URL represents resources ONLY, MUST NOT contain action verbs. `/list`, `/add`, `/update` are forbidden.
- Clear module hierarchy: `/business-module/resource`
Examples:
- ✅ List query: `GET /api/user`
- ✅ Detail query: `GET /api/user/{id}`
- ✅ Create: `POST /api/user`
- ✅ Full update: `PUT /api/user/{id}`
- ✅ Partial update: `PATCH /api/user/{id}`
- ✅ Delete: `DELETE /api/user/{id}`
