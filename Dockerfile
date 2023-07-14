FROM openjdk:19-jdk-alpine as builder
RUN apk add gradle

WORKDIR app
COPY build.gradle.kts /app/
COPY src /app/src
RUN set -x && gradle build

FROM openjdk:19-alpine
COPY --from=builder /app/build/libs/tsp_bot-1.0-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", "app/tsp_bot-1.0-SNAPSHOT.jar"]