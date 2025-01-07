FROM openjdk:23-jdk as builder

WORKDIR /app/mcsvc-cursos

COPY ./pom.xml /app
COPY ./mcsvc-cursos/.mvn ./.mvn
COPY ./mcsvc-cursos/mvnw .
COPY ./mcsvc-cursos/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./mcsvc-cursos/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:23-jdk

WORKDIR /app

COPY --from=builder /app/mcsvc-cursos/target/mcsvc-cursos-0.0.1-SNAPSHOT.jar .
EXPOSE 8002

ENTRYPOINT ["java", "-jar", "mcsvc-cursos-0.0.1-SNAPSHOT.jar"]