FROM openjdk
EXPOSE 8080
ADD sandbox-web/target/*.jar /opt/app.jar
ENTRYPOINT [ "java", "-jar", "/opt/app.jar" ]