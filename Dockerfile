FROM openjdk:8-jdk-alpine
COPY target/*.jar account-api.jar
CMD ["java", "-jar", "account-api.jar"]
