FROM amazoncorretto:21-alpine

VOLUME /tmp

RUN apk add nodejs npm
RUN npm i -g esbuild
RUN npm i -g typescript

COPY target/*.jar codbex-helios.jar
ENTRYPOINT ["java", "--add-opens", "java.base/java.lang=ALL-UNNAMED", "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED", "--add-opens", "java.base/java.nio=ALL-UNNAMED", "-jar", "/codbex-helios.jar"]
