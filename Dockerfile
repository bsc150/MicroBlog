FROM openjdk:8
ADD target/micro-blog-app.jar micro-blog-app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","micro-blog-app.jar"]