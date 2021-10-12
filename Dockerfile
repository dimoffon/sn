FROM openjdk:14-jdk-slim
WORKDIR app
ADD /target/sn-*.jar app.jar
ENTRYPOINT ["java","-Dspring.config.location=classpath:application.yml", "-jar", "app.jar"]