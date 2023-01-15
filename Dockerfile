FROM maven:3.8.3-openjdk-17 AS build

LABEL author="Vangala Sai Rohith"

ADD . .

RUN mvn package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:17-alpine

RUN addgroup -S app-do-group && adduser -S app-user -G app-do-group
USER app-user:app-do-group

ARG DEPENDENCY=target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT [ "java", "-cp", "app:app/lib/*", "com.vanna.mastery.integration.IntegrationApplication" ]

EXPOSE 8080
