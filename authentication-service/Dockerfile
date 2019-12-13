FROM adoptopenjdk/openjdk11-openj9:alpine-jre

ADD ./target/authentication-service-0.0.1-SNAPSHOT.jar /usr/src/authentication-service.jar

WORKDIR /usr/src

ENTRYPOINT ["java","-jar", "/usr/src/authentication-service.jar"]