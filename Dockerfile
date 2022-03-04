FROM openjdk:8-jdk-alpine
COPY target/account-api-0.0.1-SNAPSHOT.jar account-api.jar
CMD ["java", "-jar", "account-api.jar"]