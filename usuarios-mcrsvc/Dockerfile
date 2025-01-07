FROM openjdk:23-jdk  as builder

WORKDIR /app/usuarios-mcrsvc

COPY ./pom.xml /app
COPY ./usuarios-mcrsvc/.mvn ./.mvn
COPY ./usuarios-mcrsvc/mvnw .
COPY ./usuarios-mcrsvc/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./usuarios-mcrsvc/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:23-jdk

WORKDIR /app

COPY --from=builder /app/usuarios-mcrsvc/target/usuarios-mcrsvc-0.0.1-SNAPSHOT.jar .
EXPOSE 8001

ENTRYPOINT ["java", "-jar", "usuarios-mcrsvc-0.0.1-SNAPSHOT.jar"]

