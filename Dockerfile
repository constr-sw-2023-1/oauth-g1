FROM maven:3.8-openjdk-17 as maven
WORKDIR /usr/oauth
ARG PORT=80
ENV SERVER_PORT=$PORT
EXPOSE $PORT
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN mvn clean package -Dmaven.test.skip=true -q
ENTRYPOINT java -jar target/oauth-0.0.1-SNAPSHOT.jar
