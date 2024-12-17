Microservices app for E-Commerce domain
Modules
1. product-service
2. order-service
3. inventory-service


---  Keyclock ---

1. Start keycloak on personal port
kc.bat start-dev --http-port=8180

-----  Zipkin ----------
docker run -d -p 9411:9411 openzipkin/zipkin

----  Docker -------

First command to pull docker images - docker compose up -d
To check running containers - docker ps
To check logs of broker - docker logs -f broker

1. To create docker image of api gateway using Dockerfile
   docker build -t api-gateway-docker .

2. To create docker image of api gateway using Dockerfile.layered (To reduce size of docker image and to perform multistage build)
   docker build -t api-gateway-layered-docker -f Dockerfile.layered

3. jib-maven-plugin
   Use it to create docker images without using docker file

4. 




------  Remaining ------

Notification service consumer throwing error regarding deserialization


