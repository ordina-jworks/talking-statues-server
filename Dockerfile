FROM openjdk:8-jre-alpine
ADD talking-statues-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-Dspring.profiles.active=tst -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n"
ENTRYPOINT exec java $JAVA_OPTS -jar /app.jar
