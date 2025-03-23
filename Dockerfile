FROM amazoncorretto:19-alpine-jdk

COPY build/libs/homebanking-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]