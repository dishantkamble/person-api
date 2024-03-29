[![CodeQL](https://github.com/dishantkamble/person-api/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/dishantkamble/person-api/actions/workflows/codeql-analysis.yml)
[![CI - Create Release and Publish](https://github.com/dishantkamble/person-api/actions/workflows/release-publish.yml/badge.svg)](https://github.com/dishantkamble/person-api/actions/workflows/release-publish.yml)
# Getting Started: 

### Technology
Person API is a basic JAVA-Springboot implementation of a RESTful API with CRUD operations and an entry level authentication mechanism.

Below are the libraries used to achieve this.
* Springboot
* Spring Doc Open API
* Spring Security
* Spring Data JPA
* H2 data base for local execution

### Deployment
For deploying this application on any system, follow the below steps. Please ensure uninterrupted internet connection till the time your local docker image is prepared.

* Clone or download this repository on your system.
* Ensure Docker is running in your system.
* Open terminal and navigate to the location where your clone/download is stored.
* run **docker build -t <TAG_NAME> .**

  e.g. **docker build -t dishant/person-api .**

* This will take few seconds to download the base and other required images from dockerhub and prepare this application's final image and tag it as <TAG_NAME>
* Final image created will be identified as <TAG_NAME>:latest
* run **docker run -p 8080:8080 <TAG_NAME>:latest**

  e.g. **docker run -p 8080:8080 dishant/person-api**


### Test Application
For the sake of simplicity to run application in a more pleasing way, Spring Doc has been generated. Ones the application is deployed, you can access the Spring Docs on the following URL

**http://<HOST_NAME>:<PORT_NUMBER>/persons/api/v1/swagger-ui/index.html?configUrl=/persons/api/v1/docs/swagger-config#/person-controller**

So if you run your application on local on the above 8080 port then the URL will turn out to be

[Person API Spring Doc URL](http://localhost:8080/persons/api/v1/swagger-ui/index.html?configUrl=/persons/api/v1/docs/swagger-config#/person-controller)

**Admin User:**
* **User:** admin.dishant
* **Pass:** password
* **Access:** [Management](http://localhost:8080/persons/api/v1/management), [Health](http://localhost:8080/persons/api/v1/management/health), GET, POST, PUT, DELETE

User:
* **User:** user.dishant
* **Pass:** password
* **Access:** [Management](http://localhost:8080/persons/api/v1/management), [Health](http://localhost:8080/persons/api/v1/management/health), GET, POST
