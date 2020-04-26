cd platform
cd live-trip-service
docker build -f src/main/docker/Dockerfile.jvm -t r00ta/live-trip-service-jvm:latest .

cd ../
cd scoring-service
docker build -f src/main/docker/Dockerfile.jvm -t r00ta/scoring-service-jvm:latest .

cd ..
cd user-service
docker build -f src/main/docker/Dockerfile.jvm -t r00ta/user-service-jvm:latest .
