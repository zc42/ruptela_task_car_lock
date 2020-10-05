install redis

install kafka

install mysql

create db schema
>mysql -u root -p
>
>CREATE DATABASE IF NOT EXISTS ruptela_car;

build jar and run test cases
>mvn clean package spring-boot:repackage

start service
>java -jar target/CarRepo-0.0.1-SNAPSHOT.jar
