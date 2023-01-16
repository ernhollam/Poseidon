# Poseidon
7th project from OpenClassrooms' Backend developer training
-------------------------------------

Poseidon essentially streamlines the communication and use of post-trade information between the front and back office by  information from many sources on the fixed income markets.
It is an API which aims to generate more transactions for institutional investors buying and selling fixed-income securities.

## Getting started

### Technical stack

- Framework: Spring boot v2.0.4
- Java 8
- Thymeleaf
- Bootstrap v.4.3.1

### Running Poseidon

#### Cloning the project to your local environment
To clone the project to your local machine:
- Go to the folder in which you want to clone the project
- Open a Git Bash or a Terminal in your working folder
- Type `git clone https://github.com/ernhollam/Poseidon.git` and press Enter
- Enter the `Poseidon` folder

You must first create the database before running the application.

#### Database
- Open an SGBD (such as HeidiSQL or MySQL Workbench
- Create a database named `demo`
- Run the SQL script under `Poseidon/Poseiden-skeleton/doc/schema.sql`

Database consists in six tables `bidlist`, `curvepoint`, `rating`, `trade`, `rulename` and `users`.

To be able to test the application with an admin user, run the SQL script under
`Poseidon/Poseiden-skeleton/doc/data.sql`

#### Running
Once the data is created and the application is cloned properly, open your IDE and open the file
`Poseidon/Poseiden-skeleton/pom.xml` as a project.
Once the project is opened, run `src/main/java/com/nnk/springboot/Application.java`

You can then access the app at the default port with the link http:/localhost:8080/

You can either log in to the app your GitHub account or with the following user:
- username: `test`
- password: `~fK9:Lt.`

### Testing
Poseidon has unit tests and integration tests. Anytime an update is done, please run tests in `src/test/java/com/nnk/springboot` folder.

### Logs
Logs are registered after each request is done. All logs are registered in a file which you can find the in the `logs` folder.
Logs also appear in the console.
```2023-01-16 10:07:57.828 [restartedMain] INFO  com.nnk.springboot.Application - Started Application in 4.608 seconds (JVM running for 5.086)
2023-01-16 10:08:27.407 [http-nio-8080-exec-1] DEBUG o.s.w.f.CommonsRequestLoggingFilter - Before request [uri=/]
2023-01-16 10:08:27.606 [http-nio-8080-exec-1] DEBUG o.s.w.f.CommonsRequestLoggingFilter - After request [uri=/]
```

The rolling policy is each file should be at most 5MB, kept for 30 days worth of history, but at most 2GB. 
A new file is generated every day.

## Usage

### CRUD endpoints

For every entity listed in the [database](#database) section, you will be able to do any crud operation.
For example, with the bid entity :
- you can access the list of bids with the following endpoint: `/bidList/list`
- from this endpoint, you can either create, update or a bid
- if you know the id of the element you want to update, you can directly type `/bidList/update/{id}` after the `http://localhost:8080` in address bar

### Security
The user management page is accessible only to admin users.
You can either create a user via GitHub. GitHub login gives you simple user authority. Therefore, if you need to update your account, please contact the administrators to give you access to your user page.

#### Disclaimer
For the purpose of the training, the GitHub client secret is exposed but in a real-life environment, this configuration would have been externalized for security reasons.

