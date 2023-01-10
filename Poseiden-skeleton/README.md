# Poseidon

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
You can clone the project on your local machine by opening a Git Bash or a Terminal in your working folder, then:
- Type `git clone https://github.com/ernhollam/Poseidon.git`
- Enter `Poseiden-skeleton` folder

You must first create the database before running the application.

#### Database
- Create a database named "demo"
- Configure it as in `application.properties`
- Run the SQL script under `doc/data.sql`

Database consists in six tables `bidlist`, `curvepoint`, `rating`, `trade`, `rulename` and `users`.

#### Running
Once the data is created and the application is cloned properly, and once the tests passed, run the application by running `src/main/java/com/nnk/springboot/Application.java`
You can then access the app at the default port with the link http:/localhost:8080/

### Testing
Poseidon has unit tests and integration tests. Anytime an update is made, please run tests in `src/test/java/com/nnk/springboot` folder

### Logs
Logs are registered after each request is done. All logs are registered in a file which you can find the in the `logs` folder.

The rolling policy is each file should be at most 5MB, kept for 30 days worth of history, but at most 2GB. 
A new file is generated every day.

## Usage

### CRUD endpoints

For every entity listed in the [database](#database) section, you will be able to do any crud operation.
For example, with the bid entity :
- you can access the list of bids with the following endpoint: `/bidList/list`
- from this endpoint, you can either delete or create a new bid
- if you know the id of the element you want to update, you can directly type `/bidList/update/{id}`

### Security
The user management page is accessible only to admin users.
You can either create a user via GitHub. GitHub login gives you simple user authority. Therefore, if you need to update your account, please contact the administrators to give you access to your user page.

#### Disclaimer
For the purpose of the training, the GitHub client secret is exposed but in a real-life environment, this configuration would have been externalized for security reasons.

