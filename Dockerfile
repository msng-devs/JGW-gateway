FROM openjdk:11-jdk-alpine as builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootjar


FROM openjdk:11-jdk-alpine
COPY --from=builder build/libs/*.jar app.jar

ARG EVN
ENV SPRING_PROFILES_ACTIVE=${ENV}

EXPOSE 50001
ENTRYPOINT ["java","-jar","/app.jar"]
