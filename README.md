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
