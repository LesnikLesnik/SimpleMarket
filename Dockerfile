FROM adoptopenjdk/openjdk11

ARG JAR_FILE=target/Simple_market-V1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
