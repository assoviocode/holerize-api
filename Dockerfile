FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} holerize-api.jar
ENTRYPOINT ["java","-jar","/holerize-api.jar"]
EXPOSE 8080