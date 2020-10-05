spring boot app
===========
use cases:
---
create car

list created cars

car lock/unlock

get car state (lock/unlock)

setup:
--

install kafka

install redis

install mysql

create db schema
>mysql -u root -p
>
>CREATE DATABASE IF NOT EXISTS ruptela_car;

build and test:
--

>cd <project_dir>
>
>mvn clean package spring-boot:repackage

run:
--
>java -jar target/CarRepo-0.0.1-SNAPSHOT.jar
