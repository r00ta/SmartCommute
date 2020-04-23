cd platform
cd live-trip-service/target
java -jar *runner.jar &

cd ../../scoring-service/target
java -jar *runner.jar &

cd ../../route-analytics/target
java -jar *runner.jar &

cd ../../user-service/target
java -jar *runner.jar &
