FROM eclipse-temurin:22-jre AS builder
WORKDIR /build
COPY target/mausam-service-0.0.1-SNAPSHOT.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:22-jre
WORKDIR /app

COPY --from=builder build/dependencies/ ./
COPY --from=builder build/spring-boot-loader/ ./
COPY --from=builder build/snapshot-dependencies/ ./
COPY --from=builder build/application/ ./

EXPOSE 0
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]