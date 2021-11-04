
## About 

This is a learning project that implements simple social network.

### How to build

To build the project run the next maven command:

```
mvn clean package
```

## Load testing

#### Data generation

Java-faker is used for data generation. To generate user data build the project with profile `datagen` like:

```mvn clean package -P datagen```

As result it will generate and insert 1M records into users table.

#### Prerequisites 

It is required to install wrk and wrk2img on your machince. 

#### Runnning tests

Start sn application. If it is running on your local machine exexcute the next commands to build the report.

```
wrk --latency -t1 -c1 -d30s "http://127.0.0.1:8080/search?firstName=s%25&lastName=j%25" > t1
wrk --latency -t10 -c10 -d30s "http://127.0.0.1:8080/search?firstName=s%25&lastName=j%25" > t2
wrk --latency -t100 -c100 -d30s "http://127.0.0.1:8080/search?firstName=s%25&lastName=j%25" > t3
wrk --latency -t1000 -c1000 -d30s --timeout 5s "http://127.0.0.1:8080/search?firstName=s%25&lastName=j%25" > t4
cat t1 t2 t3 t4 | wrk2img output.png
rm t1 t2 t3 t4 
```