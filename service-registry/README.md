# Run in Docker

```bash
mvn clean install
```

```bash
docker build -t microservices/serviceregistry:0.0.1 .
```

```bash
docker run -d -p8761:8761 --name serviceregistry [image_id]
```