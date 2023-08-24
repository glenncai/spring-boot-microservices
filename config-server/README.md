# Run in Docker

```bash
mvn clean install
```

```bash
docker build -t microservices/configserver:0.0.1 .
```

```bash
docker run -d -p9296:9296 -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name configserver [image_id]
```