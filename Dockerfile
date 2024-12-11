FROM eclipse-temurin:21-jdk AS build
COPY . /app
WORKDIR /app

RUN chmod +x mvnw
RUN ./mvn package - DsikipTests
RUN mv -f target/*.jar app.jar


FROM eclipse-temurin:21-jre

ARG PORT

ENV PORT=${PORT}

COPY --from=build /app/app-jar .

RUN useradd runtime
USER runtime
ENTRYPOINT ["java","-Dserver.port=${PORt}","-jar","app.jar"]




















