FROM openjdk
EXPOSE 8080
COPY JRXML /JRXML
COPY REPORT /REPORT
ADD sandbox-web/target/*.jar /opt/app.jar
ENTRYPOINT [ "java", "-jar", "/opt/app.jar" ]