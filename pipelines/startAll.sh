curl -sL https://github.com/shyiko/jabba/raw/master/install.sh | bash && . ~/.jabba/jabba.sh

jabba install adopt@1.11.0-6
jabba use adopt@1.11.0-6

cd platform
cd live-trip-service/target
java -jar *runner.jar &

cd ../../scoring-service/target
java -jar *runner.jar &

cd ../../route-analytics/target
java -jar *runner.jar &

cd ../../user-service/target
java -jar *runner.jar &

