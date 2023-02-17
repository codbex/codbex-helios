# codbex-helios

Helios Edition contains JavaScript programming support standard components.

It is good for exploration pro-code scenarios based on JavaScript language and Enterprise API.

#### Docker

```
docker pull ghcr.io/codbex/codbex-helios:latest
docker run --name codbex-helios --rm -p 8080:8080 ghcr.io/codbex/codbex-helios:latest
```

#### Build

```
mvn clean install
```
	
#### Run

```
java -jar application/target/codbex-helios-application-1.0.0-SNAPSHOT.jar
```

#### Debug

```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -jar application/target/codbex-helios-application-1.0.0-SNAPSHOT.jar
```
	
#### Web

```
http://localhost:8080
```

#### REST API

```
http://localhost:8080/swagger-ui/index.html
```
