#
# Build stage
#
FROM maven:3-amazoncorretto-19 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml -DoutputDirectory=/home/app/target/ clean package

#
# Package stage
#
FROM amazoncorretto:19.0.1-alpine3.16
COPY --from=build /home/app/target/messaging-api.jar /usr/local/lib/messaging-api.jar
EXPOSE 4000
ENTRYPOINT ["java","-jar","/usr/local/lib/messaging-api.jar"]