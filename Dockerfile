FROM adoptopenjdk:11-jre-hotspot
VOLUME /tmp
COPY target/*.jar app.jar
ENV JAVA_OPTS=""
CMD ["java", "-jar", "/app.jar"]
