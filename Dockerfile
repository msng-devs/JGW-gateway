RUN curl -fsSLO https://get.docker.com/builds/Linux/x86_64/docker-17.04.0-ce.tgz \
  && tar xzvf docker-17.04.0-ce.tgz \
  && mv docker/docker /usr/local/bin \
  && rm -r docker docker-17.04.0-ce.tgz
FROM openjdk:11 as builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +x ./gradlew
RUN ./gradlew bootjar


FROM openjdk:11
COPY --from=builder build/libs/*.jar app.jar

ARG EVN
ENV SPRING_PROFILES_ACTIVE=${ENV}

EXPOSE 50001
ENTRYPOINT ["java","-jar","/app.jar"]
