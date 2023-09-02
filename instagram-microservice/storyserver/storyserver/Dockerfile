FROM amazoncorretto:17-alpine AS build
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package -DskipTests

FROM  amazoncorretto:17-alpine
WORKDIR app
COPY --from=build target/*.jar story.jar
ENTRYPOINT ["java", "-jar", "story.jar"]