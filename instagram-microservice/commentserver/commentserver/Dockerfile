FROM amazoncorretto:17-alpine AS build
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -DskipTests

FROM  amazoncorretto:17-alpine
WORKDIR app
COPY --from=build target/*.jar commentserver.jar
ENTRYPOINT ["java", "-jar", "commentserver.jar"]
##Start with a base image containing Java runtime
#FROM openjdk:17-jdk-slim as build
## Add the application's jar to the container
#WORKDIR app
#COPY target/commentserver-0.0.1-SNAPSHOT.jar app.jar
#
##execute the application
#ENTRYPOINT ["java","-jar","app.jar"]