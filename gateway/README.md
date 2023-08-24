# Run in Docker

```bash
mvn clean install
```

```bash
docker build -t microservices/cloudgateway:0.0.1 .
```

```bash
docker run -d -p9090:9090 -e CONFIG_SERVER_URL=host.docker.internal -e EUREKA_SERVER_ADDRESS=http://host.docker.internal:8761/eureka --name cloudgateway [image_id]
```