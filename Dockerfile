FROM ubuntu:14.04
RUN apt-get update -y
RUN apt-get install -y openjdk-7-jdk 
COPY build/libs/FibonacciService-1.0.jar /home/FibonacciService-1.0.jar

CMD ["java","-jar","/home/FibonacciService-1.0.jar"]

