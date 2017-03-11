FROM openjdk:8
COPY target/evolutiontwo-1.0-SNAPSHOT.jar /usr/src/evolutiontwo.jar
WORKDIR /usr/src
ENTRYPOINT java -jar evolutiontwo.jar --xSize=30 --ySize=30 --vegetationSpawnRate=4 --vegetationMaxAge=20 --vegetationNutrition=50

