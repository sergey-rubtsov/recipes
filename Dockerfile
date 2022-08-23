FROM openjdk:11.0.7-jre-slim as runtime

WORKDIR /opt/recipes

COPY recipes/build/libs/recipes.jar .

EXPOSE 8080

ENV SERVER_PORT 8080

CMD ["/bin/sh", "-c", "java $JAVA_OPTS -jar recipes.jar"]
