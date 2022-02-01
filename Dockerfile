FROM openjdk:17
EXPOSE 8080
ADD sandbox-web_temp/target/sandbox-web-0.0.1-SNAPSHOT.jar /opt/sandbox-web-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java", "-jar", "/opt/sandbox-web-0.0.1-SNAPSHOT.jar" ]
#CMD ["--spring.profiles.active=docker"]