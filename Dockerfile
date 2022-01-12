FROM gradle:6.9-jdk11 as builder
COPY --chown=gradle:gradle . /home/src
WORKDIR /home/src
RUN gradle build

FROM openjdk:11-jre-slim
ENV VERSION=0.0.1-SNAPSHOT
CMD mkdir /app
COPY --from=builder /home/src/build/libs/spring-boot-batch-${VERSION}.jar /app.jar
EXPOSE 8080
ENTRYPOINT java -jar /app.jar