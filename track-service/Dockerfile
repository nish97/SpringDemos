FROM adoptopenjdk/openjdk11-openj9:alpine-jre

ADD ./target/track-service-0.0.1-SNAPSHOT.jar /usr/src/track-service-0.0.1-SNAPSHOT.jar

WORKDIR /usr/src

ENTRYPOINT ["java","-jar", "/usr/src/track-service-0.0.1-SNAPSHOT.jar"]