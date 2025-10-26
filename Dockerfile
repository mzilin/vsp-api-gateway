# --- Build stage ---
FROM gradle:8.14.1-jdk21 as build

WORKDIR /app
COPY . .
RUN gradle clean build -x test --no-daemon

# --- Runtime stage ---
FROM eclipse-temurin:21-jdk-alpine

RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app
COPY --from=build /app/build/libs/streamix-infra-gateway-*.jar app.jar
RUN chown appuser:appgroup app.jar

USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]