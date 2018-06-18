FROM openjdk:8-jdk-alpine
ADD /talking-statues-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-Dspring.profiles.active=prod -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n"
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar