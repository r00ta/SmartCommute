cd platform
cd live-trip-service
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/live-trip-service-jvm .

cd ../
cd scoring-service
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/scoring-service-jvm .

cd ..
cd user-service
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/user-service-jvm .
