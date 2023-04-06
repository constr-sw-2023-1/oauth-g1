FROM maven:3.8-openjdk-17 as maven
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package -Dmaven.test.skip=true -q
FROM maven:3.8-openjdk-17
EXPOSE 8080
COPY --from=maven target/oauth-0.0.1-SNAPSHOT.jar server.jar
ENTRYPOINT ["java","-jar","server.jar"]
