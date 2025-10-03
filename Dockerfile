# ---- build ----
FROM maven:3-amazoncorretto-21 AS build
WORKDIR /app

# Cache deps
COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

# Compile/package
COPY src src
RUN mvn -B -DskipTests package

# ---- run ----
FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/target/trotti-ul.jar /app/trotti-ul.jar

#RUN adduser -D appuser
#USER appuser

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"
EXPOSE 8080
CMD ["java","-jar","/app/trotti-ul.jar"]