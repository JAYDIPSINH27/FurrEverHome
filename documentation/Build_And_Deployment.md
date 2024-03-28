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



## Deploying The Application

The deployment process is automated through a CI/CD pipeline, leveraging Docker for containerization and GitLab CI for continuous integration and deployment tasks. The process encompasses building the application, running tests, ensuring code quality, publishing the Docker images, and deploying the application to the server.

### Prerequisites
- Docker installed on your local machine and server.
- GitLab CI/CD configured with appropriate runners.
- VPN Configured and setup to connect to network
- SSH access to the deployment server.
- Docker Hub account for storing Docker images.

### **Deploying Backend**
The following are the steps to deploy Spring Boot Application using Docker and CI/CD pipeline. Once docker is installed in virtual machine:


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

### Deployment
The deployment stage is where the newly created Docker image is deployed to our server. This involves a series of steps to ensure that the application is updated securely and efficiently.

**Preparing SSH Connection**

1. **SSH Key Configuration**:
    - Setting correct permissions for the SSH key to protect it.
    ```bash
    chmod 600 $ID_RSA
    ```

2. **SSH Agent Setup**:
    - Starting the SSH agent and adding the private SSH key for passwordless server authentication.
    ```bash
    eval $(ssh-agent -s)
    ssh-add $ID_RSA
    ```

**Deploying the Application**

1. **Pulling the Latest Docker Image**:
    - Ensures the server uses the latest Docker image for the deployment.
    ```bash
    ssh -i $ID_RSA -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker pull em492028/furrever-backend-api:$IMAGE_TAG"
    ```

2. **Stopping and Removing the Old Container**:
    - Prevents conflicts by stopping and forcibly removing the existing container.
    ```bash
    ssh -i $ID_RSA -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker container rm -f $CONTAINER_NAME || true"
    ```

3. **Running the New Docker Container**:
    - Starts the new container with the specified configuration, including port mappings and environment variables.
    ```bash
    ssh -i $ID_RSA -o StrictHostKeyChecking=no $SERVER_USER@$SERVER_IP "docker run -d -p $SERVER_PORT:$SERVER_PORT --name $CONTAINER_NAME --restart=always -e SERVER_PORT=$SERVER_PORT -e SPRING_DATASOURCE_URL=$DB_URL -e SPRING_DATASOURCE_USERNAME=$DB_USER -e SPRING_DATASOURCE_PASSWORD=$DB_PASSWORD em492028/furrever-backend-api:$IMAGE_TAG"
    ```

### Deploying Frontend
