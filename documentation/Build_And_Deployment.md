# Build and Deployment


## Building the Application

Once you have installed all the dependencies, you can proceed with building the application.

### **Backend Application**
For the backend Spring Boot application, navigate to the project directory and run the following command to build the application:

```
mvn clean package
```

This creates a jar file that can be accessed in the `target` folder.

Or
To build the backend Spring Boot application in your CI/CD pipeline, you can use the following steps:
* Create a new job in your GitLab CI/CD pipeline and define it as a ```build``` stage.
* Choose an appropriate Docker image to run the job. In this case, we can use the ```maven:latest``` image to build the Spring Boot application.
* In the script section of the job, navigate to the project directory using the ```cd``` command.
* Use the ```mvn clean``` package command to build the application.
* Define the artifacts section to specify which files should be stored as pipeline artifacts. In this case, we want to store the target folder of the Spring Boot project
* Finally, specify the runner tags that should be used to execute the job, in this case ```furrever-gl-runner```.

You will be able to see
```
Furrever_Home-0.0.1-SNAPSHOT.jar
``` 
in this path:
```bash
"/home/gitlab-runner/builds/Furrever_Home/target"
```

### **Frontend Application**  

For the frontend React application, navigate to the project directory and run the following command to build the application:

```
npm run build
```

You will be able to see folder
```
dist
``` 
You can run preview of this build file by this command
```bash
npm run preview
```
For running in development environment run the below command
```bash
npm run dev
```

## Deploying The Application

The deployment process is automated through a CI/CD pipeline, leveraging Docker for containerization and GitLab CI for continuous integration and deployment tasks. The process encompasses building the application, running tests, ensuring code quality, publishing the Docker images, and deploying the application to the server.

### Prerequisites
- Docker installed on your local machine and server.
- GitLab CI/CD configured with appropriate runners.
- VPN Configured and setup to connect to network
- SSH access to the deployment server.
- Docker Hub account for storing Docker images.

# Furrever Home Deployment Guide

This document provides an overview and instructions for deploying the Furrever Home application using the provided GitLab CI/CD YAML script. The deployment pipeline includes building, testing, quality assurance, publishing Docker images, and deploying these images to the server.

## Overview

The deployment process is divided into multiple stages, each responsible for different aspects of getting the application up and running. These stages ensure the code is not only built but also tested and meets quality standards before being deployed.

### Stages and Their Order of Execution

1. **Build**: Compile and package the application.
2. **Test**: Execute automated tests to ensure the application functions as expected.
3. **Quality**: Assess code quality and perform static code analysis.
4. **Publish**: Build Docker images and push them to a Docker registry.
5. **Deploy**: Deploy the Docker images to a production environment.

### Jobs

- `build`: Compiles and packages the backend.
- `build-frontend`: Compiles and bundles the frontend.
- `test`: Executes the application's automated test suite.
- `quality`: Analyzes the codebase for code smells, bugs, and vulnerabilities.
- `publish`: Publishes the backend Docker image.
- `publish-frontend`: Publishes the frontend Docker image.
- `deploy`: Deploys the backend application.
- `deploy-frontend`: Deploys the frontend application.

## Prerequisites

- GitLab runner with Docker and Docker Compose installed.
- Docker Hub account for storing Docker images.
- Server with Docker installed for deployment.
- SSH access to the deployment server.

## Configuration

Ensure the following variables are configured in your GitLab project:

- `DOCKER_HUB_USER`: Your Docker Hub username.
- `DOCKER_HUB_PWD`: Your Docker Hub password.
- `SERVER_IP`: IP address of your deployment server.
- `SERVER_USER`: SSH user for the deployment server.
- `ID_RSA`: Private SSH key for the `SERVER_USER`, encoded in base64.
- Deployment and testing specific environment variables like `VITE_BACKEND_TEST_BASE_URL`, `DEVINT_DB_URL`, `PROD_DB_URL`, etc.

### Setting Up SSH Keys

Ensure the deployment server's SSH key is added to the known hosts on the GitLab runner. The private SSH key (`ID_RSA`) should be securely added to the GitLab CI/CD variables.


## Deployment Process

To deploy the application, push your code to the GitLab repository. The CI/CD pipeline, defined in the `.gitlab-ci.yml` file, will automatically execute the defined jobs in the order of the stages specified.

### Manual Steps

Some steps might require manual intervention, such as initial setup of environment variables or secrets in the GitLab project, or configuration changes on the Docker Hub or deployment server.

## Troubleshooting

- Check that all required environment variables are correctly set in the GitLab project.
- Verify the Docker and SSH services are running on the deployment server.
- Review the GitLab CI/CD pipeline logs for any errors during execution.

## Support

For support with the deployment process or if encountering any issues, please contact the development team or check the GitLab CI/CD documentation for more detailed guidance.


### **Deployment Process In Detail**
- The following are the steps to deploy Spring Boot Application using Docker and CI/CD pipeline. Once docker is installed in virtual machine:
- This Steps can be found in gitlab yml file.

### Publishing 
The publish stage is where our backend application is containerized and pushed to a Docker registry. This process involves several steps and commands, explained below:

**Docker Image Creation**
1. **Setting Up Docker:** The first step in our pipeline specifies the use of Docker with the image: docker:latest command. This command tells the CI/CD runner to use the latest Docker image as the environment for running subsequent commands. The docker:dind service is also specified to allow Docker commands to run within Docker containers.
```yaml
image: docker:latest
services:
- docker:dind
```

2. **Building the Backend Docker Image:** The docker build command is used to create a Docker image of our backend application. The `-t` flag tags our image with a unique name, incorporating both the branch name and the commit's short SHA, making every image easily identifiable and ensuring that each deployment uses the correct, up-to-date image.
```shell
docker build -t em492028/furrever-backend-api:$IMAGE_TAG . -f Dockerfile
```
In this command:
* `em492028/furrever-backend-api` is the name of our Docker image on DockerHub.
* `$IMAGE_TAG` is a variable holding the tag for our image, constructed from the branch name and commit SHA.
* `-f` Dockerfile specifies the Dockerfile to use for the build.
* The `.` denotes the context of the build, which is the current directory.

3. **Pushing the Image to Docker Registry:** After building the image, it is pushed to a Docker registry using the docker push command. This makes the image available for deployment across all our environments.
```bash
docker push em492028/furrever-backend-api:$IMAGE_TAG
```

### Deploying

1. **Pulling the Latest Docker Image**:
    - Ensures the server uses the latest Docker image for the deployment.
```
ssh -i $ID_RSA -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker pull em492028/furrever-backend-api:$IMAGE_TAG"
```
      


2. **Stopping and Removing the Old Container**:
    - Prevents conflicts by stopping and forcibly removing the existing container.
      
```
ssh -i $ID_RSA -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker container rm -f $CONTAINER_NAME || true"
```

3. **Running the New Docker Container**:
    - Starts the new container with the specified configuration, including port mappings and environment variables.
      
```
ssh -i $ID_RSA -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker run -d -p $SERVER_PORT:$SERVER_PORT --name $CONTAINER_NAME --restart=always -e SERVER_PORT=$SERVER_PORT -e SPRING_DATASOURCE_URL=$DB_URL -e SPRING_DATASOURCE_USERNAME=$DB_USER -e SPRING_DATASOURCE_PASSWORD=$DB_PASSWORD em492028/furrever-backend-api:$IMAGE_TAG"
```

[**Go back to README.md**](../README.md)



