FROM openjdk:8-jdk-alpine
ADD /target/talking-statues-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-Dspring.profiles.active=prod"
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar