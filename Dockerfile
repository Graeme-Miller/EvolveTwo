FROM openjdk:8
COPY target/evolutiontwo-1.0-SNAPSHOT.jar /usr/src/evolutiontwo.jar
WORKDIR /usr/src
ENTRYPOINT java -jar evolutiontwo.jar