FROM openjdk:11
EXPOSE 8444
ADD target/app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"] 