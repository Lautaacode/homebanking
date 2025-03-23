
FROM gradle:8-jdk19-alpine AS build
WORKDIR /app


COPY . .


RUN ./gradlew build --no-daemon


FROM amazoncorretto:19-alpine-jdk
WORKDIR /app


COPY --from=build /app/build/libs/*.jar app.jar


ENTRYPOINT ["java", "-jar", "/app.jar"]