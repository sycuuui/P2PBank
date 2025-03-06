FROM openjdk:21-slim
ARG JAR_FILE=./build/libs/P2PBank-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["sh", "-c", "java -Duser.timezone=Asia/Seoul -jar /app.jar"]