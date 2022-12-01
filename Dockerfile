FROM openjdk:17-jdk-alpine
COPY build/libs/*.jar authsvr-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "authsvr-0.0.1-SNAPSHOT.jar", "--spring.datasource.url=${DB_URL}", "--eureka.client.service-url.defaultZone=${EUREKAURL}", "--spring.datasource.username=${DB_USER}", "--spring.datasource.password=${DB_PW}", "--server.port=${TOMCATPORT}"]