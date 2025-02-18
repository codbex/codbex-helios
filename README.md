# codbex-helios

Helios Edition contains JavaScript programming support standard components.

It is good for exploration pro-code scenarios based on JavaScript language and Enterprise API.

#### Docker

```
docker pull ghcr.io/codbex/codbex-helios:latest
docker run --name codbex-helios --rm -p 80:80 ghcr.io/codbex/codbex-helios:latest
```

#### Build

```
mvn clean install
```
	
#### Run

```
java -jar application/target/codbex-helios-application-*.jar
```

#### Debug

```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000 -jar application/target/codbex-helios-application-*.jar
```
	
#### Web

```
http://localhost
```

#### REST API

```
http://localhost/swagger-ui/index.html
```

