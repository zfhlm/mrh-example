FROM openjdk
WORKDIR /usr/local/application/
COPY target/application.jar application.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar","/usr/local/application/application.jar"]