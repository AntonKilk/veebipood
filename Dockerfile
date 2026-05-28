FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /app/veebipood.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/veebipood.jar"]