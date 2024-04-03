FROM eclipse-temurin:17-jre-alpine@sha256:ff32f6a063be7a0dbdfcf5486968ed268d797ee45465cd2ea956bd42ba22cd9b
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

