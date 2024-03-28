# External Dependencies

## Technologies Used

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-green)](https://spring.io/projects/spring-boot)
[![React.tsx](https://img.shields.io/badge/React.js-16.x-purple)](https://reactjs.org/)
[![Taildwind](https://img.shields.io/badge/tailwindcss.css-3.4.1-drakpink)](https://tailwindcss.com/)
[![MySQL](https://img.shields.io/badge/MySQL-v8.0-yellow)](https://www.mysql.com/)
[![gitlab](https://img.shields.io/badge/GitLab-v14.0-blue)](https://img.shields.io/badge/GitLab-v14.0-blue)

## Getting Started

### Prerequisites

- [Java v17.0.2](https://www.oracle.com/java/)
- [JDK v17](https://www.oracle.com/java/)
- [Maven v3.9.6](https://maven.apache.org/)
- [NodeJs v16.x](https://nodejs.org/en) 
- [npm v6.x](https://www.npmjs.com/) 
- [Vite v5.x](https://vitejs.dev/) 
- [ReactJS v20.x](https://reactjs.org/) 



## Prerequisite Setup

Before you can build and deploy the application, you will need to install the following dependencies on your virtual machine:

### **Java**
To install Java, run the following commands:

```bash
sudo apt-get update  
sudo apt-get install openjdk-17-jdk  
```

### **Maven**
To install maven, run the following commands:
```bash
sudo apt install maven
```
Once the command has finished executing, confirm its installation by running:
```bash
maven --v
```

### **Node.js and npm**
To install Node.js and run the following commands:

```bash
sudo apt-get update  
sudo apt-get install nodejs  
sudo apt install npm
```

After installation, verify Node.js, npm and React are installed by running:

```bash
node -v  
npm -v  
``` 

# Project Setup

### Clone the Repository
```bash
git clone git@git.cs.dal.ca:courses/2024-winter/csci5308/Group13.git
OR
git clone https://git.cs.dal.ca/courses/2024-winter/csci5308/Group13.git
```

## Backend Setup

### 1. Navigate to the project directory
```bash
cd Group13/backend/Furrever_Home
```

### 2. Build the project
- Run the following command to install backend dependencies:
```bash
mvn clean install
```

### 3. Run the application
- After installing dependencies, start the Spring Boot backend server by running:
```bash
mvn spring-boot:run
```
The application will be accessible at http://localhost:8080.

## Backend Dependencies


| Dependency                         | Version   | ArtifactId           |
|------------------------------------|-----------|----------------------|
| org.springframework.boot           |           |                      |
| org.springframework.boot           |           |                      |
| org.springframework.boot           |           |                      |
| com.mysql                          | runtime   | mysql-connector-j    |
| org.projectlombok                  | optional  | lombok               |
| org.springframework.boot           | test      | spring-boot-starter-test |
| org.passay                         | 1.0       | passay               |
| org.springframework.boot           |           | spring-boot-starter-security |
| com.google.guava                   | 10.0.1    | guava                |
| io.jsonwebtoken                    | 0.11.5    | jjwt-api             |
| io.jsonwebtoken                    | 0.11.5    | jjwt-impl            |
| io.jsonwebtoken                    | 0.11.5    | jjwt-jackson         |
| org.junit.jupiter                 | 5.10.0    | junit-jupiter-engine |
| org.springframework.security       | test      | spring-security-test |
| io.jsonwebtoken                    | 0.9.1     | jjwt                 |
| com.vaadin.external.google         | compile   | android-json         |
| org.apache.commons                 |           | commons-lang3        |
| org.springframework.boot           |           | spring-boot-starter-mail |
| io.getstream                       | 1.20.1    | stream-chat-java     |
| commons-codec                      | 1.16.0    | commons-codec        |
| org.apache.commons                 | 3.0       | commons-lang3        |
| io.rest-assured                    | test      | rest-assured         |
| org.hamcrest                       | test      | hamcrest-all         |
| javax.xml.bind                     |           | jaxb-api             |
| org.glassfish.jaxb                 |           | jaxb-runtime         |


## Frontend Setup


### 1. Navigate to the project directory
```bash
cd Group13/frontend/furrever_home
```

### 2. Install the dependencies
- Run the following command to install frontend dependencies:
```bash
npm install
```

### 3. Run the application
- After installing dependencies, start the React frontend server by running:
```bash
npm run dev
```
The application will be accessible at http://localhost:5173.


## Runtime Dependencies

| Dependency               | Version    |
|--------------------------|------------|
| @emotion/react           | ^11.11.3   |
| @emotion/styled          | ^11.11.0   |
| @heroicons/react         | ^2.1.1     |
| @material-tailwind/react | ^2.1.9     |
| @mui/material            | ^5.15.11   |
| @mui/x-data-grid         | ^6.19.5    |
| @mui/x-date-pickers      | ^6.19.5    |
| axios                    | ^1.6.7     |
| react                    | ^18.2.0    |
| react-dom                | ^18.2.0    |
| react-icons              | ^5.0.1     |
| react-initials-avatar    | ^1.1.2     |
| react-router-dom         | ^6.22.0    |
| react-toastify           | ^10.0.4    |
| stream-chat              | ^8.24.0    |
| stream-chat-react        | ^11.12.0   |
| swiper                   | ^11.0.6    |

## Development Dependencies

| Dependency             | Version   |
|------------------------|-----------|
| @tailwindcss/forms     | ^0.5.7    |
| @types/react           | ^18.2.43  |
| @types/react-dom       | ^18.2.17  |
| @vitejs/plugin-react   | ^4.2.1    |
| autoprefixer           | ^10.4.17  |
| dotenv                 | ^16.4.1   |
| eslint                 | ^8.55.0   |
| eslint-plugin-react    | ^7.33.2   |
| eslint-plugin-react-hooks | ^4.6.0 |
| eslint-plugin-react-refresh | ^0.4.5 |
| postcss                | ^8.4.33   |
| tailwindcss            | ^3.4.1    |
| vite                   | ^5.0.8    |


### **GitLab Runner**
To install your own GitLab Runner instance, follow the official documentation: https://docs.gitlab.com/runner/install/  
A private GitLab runner is recommended to ensure the security and reliability of your CI/CD pipeline.
By using a private runner, you can have more control over the environment and resources used for your builds.  
After installation, verify runner is installed by running:

```bash
gitlab-runner --version  
```

### **Docker**
To host your Spring Boot app using Docker, you will need to have Docker installed on your virtual machine.

Here are the steps to install Docker on Ubuntu:

* Install the necessary packages to allow apt to use a repository over HTTPS:

```bash
sudo apt-get install -y apt-transport-https ca-certificates curl software-properties-common
```

* Add the GPG key for the official Docker repository:

```bash
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```

* Add the Docker repository to apt:

```bash
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"  
sudo apt-get update
```

* Install the latest version of Docker:

```bash
sudo apt-get install -y docker-ce
```


### **Set up Variables**
To set up variables for your repository in your CI/CD pipeline, go to your repository settings, then CI/CD, then variables. Now add the following variables:
```bash
DEPLOY_DIR – Type: Variable – Directory to store pipeline generated artifacts
DEPLOY_HOST – Type: Variable - Hostname
DEPLOY_USER – Type: Variable - Username
DEPLOY_SSH_KEY – Type: File – Use generated Private key
```
### **Set Up SSH Registration**
In deployment stage, we need a SSH key to access the VM. To set up SSH, run the following commands on your VM:
```bash
ssh-keygen -t ecdsa -f ~/.ssh/<<key-name>>
cd .ssh
cat <<key-name>>.pub >> ~/.ssh/authorized_keys
eval "$(ssh-agent -s)"
ssh-add <<absolute_path_of_priv_key>>
cat <<key-name>>
```
Now, copy this private key and add it to your repository’s ```env``` variable for SSH key.



[**Go back to README.md**](../README.md)