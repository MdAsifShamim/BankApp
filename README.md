# BankApp
Develop Microservice Application for Banking Solution

Microservices Details:

    - accounts
    - cards
    - loans

Details used in each microservice:

    * Used H2 Database.
    * Documentation using open-doc-api.
    * Used actuator for health checkup.
    * Used Validator for validate each request.
    * Used Configuration file as YML.
    * Profiles are maintained on outside of project using config server.
    * For do communication for other microservices used Open Feign Client.
    * Register Microservice to Eureka Server for service Discovery and Service Registry.
    * Create Docker compose file for each microservice.

Configuration server Details:

    - configServer

Config server has been used for manage configuration of each microservice from outside of project.
To manage configuration i am using git repository to change the configuration.
and enable actuator #cloud bus for refresh configuration inside each microservice.

Service Discovery and Service Registry Details:

    - eurekaServer
    

* eurekaServer has been used for service discovery and service registry.
* Used Load balancer to manage load between each docker container server file.

Gateway Sevrer Details:

    - gatewayServer

* Gateway Server or Edge server work as API Gateway.
* Gateway server register them self into server registry.
* Gateway server handle configuration from outside of project using @configserver.
* Used Resilience design patterns into Gateway server.
* Circuit breaker pattern has been used in account microservice.
* retry pattern has been used on loans microservice.
* ratelimiter has been configured for cards microservice.
* Implement Spring oAuth2 security into Edge server.
* Disable communication from microservice network, edge server used to handel outside traffic.
* Used KeyClock as OAuth2 Server.
* Used Spring Security and added Cors and csrf attack configuration.
* USed Client credential grant type flow for internal communication.
* Used Authentication Code Grant type flow to handle user communication.
* Enable Actuator to check healthy status.

RabbitMq Message Service:

    - message

* Added Rabbit MQ for Event Driven Microservice communication.
* While account has been created send notification through mail or sms.


Docker Compose Files:

    - docker-compose

* used docker compose file for each environment i.e (default,qa & prod).
* once docker file created for each microservice. we don't want to generate different image file. just with the help of docker compose file we can start microservice container in different environment.
* Inside docker compose file created docker-compose.yml file and common-config.yml file.
* Inside docker compose file added health checkup of microservices and depedecy has been added.


Other dependency:

    - Grafana
    - Grafana Loki
    - Allow
    - promitus
    - 

* Above all used for Observability and monitoring. 


Postman EndPoint:

    - BankApp.postman_collection

snaps:

    - snaps of each services



   
    



