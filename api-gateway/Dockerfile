FROM adoptopenjdk/openjdk11-openj9:alpine-jre

ADD ./target/api-gateway-0.0.1-SNAPSHOT.jar /usr/src/api-gateway.jar

WORKDIR /usr/src

ENTRYPOINT ["java","-jar", "api-gateway.jar"]