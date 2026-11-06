# Enterprise-style Spring Cloud OpenFeign integration demo

## Stack

- Java 21
- Spring Boot 3.5.x
- Spring Cloud OpenFeign
- Spring Cloud CircuitBreaker + Resilience4j
- Bean Validation
- Spring Boot Actuator
- Micrometer tracing/metrics integration

The Maven BOM aligns Spring Cloud dependencies with Spring Boot. This sample pins a compatible Boot 3.5 / Cloud 2025.0 baseline; before production use, select the latest supported service releases from the same compatibility train.

## Run

```bash
mvn clean test
mvn spring-boot:run
```

Override configuration without changing source:

```bash
POST_SERVICE_BASE_URL=https://jsonplaceholder.typicode.com \
SERVER_PORT=8080 \
FEIGN_CONNECT_TIMEOUT_MS=2000 \
FEIGN_READ_TIMEOUT_MS=5000 \
mvn spring-boot:run
```

## Endpoints

```http
GET http://localhost:8080/api/v1/integration/posts
GET http://localhost:8080/api/v1/integration/posts/1
```

Health:

```http
GET http://localhost:8080/actuator/health
```

## Why the fallback throws

Returning `Collections.emptyList()` or a fabricated DTO from a fallback makes an unavailable downstream service appear healthy to callers. This sample instead raises a typed `DownstreamServiceUnavailableException`, which the API advice maps to HTTP 502. A downstream 404 remains a 404 and is not mislabeled as an outage.

## Configuration isolation

`PostFeignConfiguration` intentionally has no `@Configuration` annotation and is referenced only from `@FeignClient`. Keep client-specific Feign configuration outside the application's component-scan packages if you choose to annotate it, otherwise its beans can become global Feign defaults.

## Production hardening checklist

- Use OAuth2 client credentials / workload identity for authenticated downstream APIs. Do not place tokens, passwords, or API keys in source control or plain YAML.
- Use a secret manager or platform secret injection for credentials.
- Set connect/read timeouts from the downstream SLO and end-to-end request budget.
- Retry only idempotent operations and only transient failures; retries amplify load during outages. This sample intentionally does not add automatic retries.
- Tune circuit-breaker thresholds using traffic volume and operational data.
- Add rate limiting/bulkheads when concurrency isolation is required.
- Restrict Actuator exposure and secure operational endpoints.
- Avoid FULL Feign logging in production because request/response bodies can contain secrets or personal data.
- Add contract tests and integration tests with WireMock/Testcontainers for the actual downstream contract.
- Configure TLS, DNS, proxy, connection-pool and deployment settings for your platform.
- Add dashboards and alerts for latency, error rate, circuit state, pool saturation and fallback counts.

## Notes

This is a clean reference implementation, not a drop-in production security platform. The downstream API used here is a public demo endpoint and does not require authentication.
