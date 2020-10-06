demo app - car lock / unlock repo
===

use cases:
-
    - create car
    
    - list created cars
    
    - car lock / unlock
    
    - get car state (lock / unlock)

setup:
-
    install kafka

    install redis

    install mysql

create db schema
-
    > mysql -u root -p
    > CREATE DATABASE IF NOT EXISTS ruptela_car;

build and test:
--

    > cd <project_dir>
    > mvn clean package spring-boot:repackage

run:
--
    > java -jar target/CarRepo-0.0.1-SNAPSHOT.jar


app logic
== 
 create car
 --
    - check if maker and model exist
    - maker
	    redis find e
	    if found return e.exists() 
	    else
	        if redis maker repo is empty
	            get data from external api
	            redis store external api data
	            redis find e 
	            if found return e.exists()
	        redis store non existing e        
	        return e.exists()
	- model 
	    redis find e 
		    if found return e.exists() 
		    else
		        get data from external api
		        redis store external api data
		        redis find e
		        if found return e.exists()
		        redis store non existing e
		        return e.exists()
	
	if maker and model exists
		save car to
			persist repo
			redis repo

list created cars
--
	get all cars from persist repo

car lock / unlock
-- 
    redis find e
	if found
		if state not exist 
		    return err
		else 
		    lock/unlock
		    kafka send msg - persist db update e
		    return state
	else 
	    persistent db find e
	    if found
	        lock/unlock 
	        redis save e
	        return state
        else
            redis save e with state not exist

get car state (lock / unlock)
--
    redis find e
	if found return e state
	persistent db find e
	if found
	    redis save e
        return e state
	else
	    redis save e with state not exist