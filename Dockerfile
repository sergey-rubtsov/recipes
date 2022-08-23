FROM gradle:jdk11-focal AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

FROM openjdk:slim-buster as runtime

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/recipes.jar

ENV SERVER_PORT 8080
ENV SPRING_PROFILES_ACTIVE prod

CMD ["/bin/sh", "-c", "java $JAVA_OPTS -jar /app/recipes.jar"]
