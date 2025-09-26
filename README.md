# A25 GLO-4003 - Projet Trotti-ul Eq-10

Laval University’s electric scooter rental service for optimal transportation.

## Table of Contents

- [Technological Environment](#technological-environment)
- [Features](#features)
    - [Account management](#account-management)
    - [Buy electric scooter passes](#buy-electric-scooter-passes)
- [Installation](#installation)


## Technological Environment

- **Language**: Java 21+
- **Web Server**:
    - Jetty (servlet container to run the app)
- **Frameworks**:
    - **Jersey** for REST API
    - **Jackson** for JSON processing
- **Persistence**: In-memory storage
- **Build & Deployment**: Maven, Docker


## Features

### Account management

The **Account Management** module allows students to create and manage their accounts for the electric scooter service. Features include:

- Create a personal account with identity details (name, birthdate, gender, age, University Laval ID)
- Set up login credentials (email and secure password) with sessions expiring after 60 minutes


### Buy electric scooter passes

The **Scooter Pass** module enables students to purchase passes for using electric scooters on campus. Features include:

- Initiate a session pass purchase and select the academic session
- Choose daily travel duration and billing method (per trip or monthly)
- Complete payment by credit card
- Handle failed transactions and store payment info for future trips
- Receive a transaction invoice
- Pass is valid for a single academic session (from session start to the next session)


## Installation

### Prerequisites

- **Java 21+**
- **Docker**
- **Maven** (optional, see note below)

> **Note:** Maven is required only if you want to run the application without Docker.


### Clone the repository:

-
   ```bash
   git https://github.com/GLO4003UL/a25-projet-trotti-ul-a25-eq-10.git
   cd a25-projet-trotti-ul-a25-eq-10
    ```
## Starting the app

The app can be run in a Docker Environment or directly with Maven.

### Running with Maven

- Compile the project:
   ```bash
   mvn clean install
    ```
- Run the application:
   ```bash
   # with custom token expiration duration
   TOKEN_EXPIRATION_DURATION=PT60M mvn exec:java
    ```
   ```bash
    # with default token expiration duration (PT60M)
    mvn exec:java # with default token expiration duration (PT60M)
    ```

### Running in Docker

- Build the Docker image:
   ```bash
   docker build -t application-glo4003 .
    ```
- Run the Docker container:
   ```bash
    # with custom token expiration duration
    docker run -p 8080:8080 -e TOKEN_EXPIRATION_DURATION=PT60M application-glo4003
    ```

    ```bash
    # with default token expiration duration (PT60M)
    docker run -p 8080:8080 application-glo4003 
    ```
> [!IMPORTANT]
> The `TOKEN_EXPIRATION_DURATION` environment variable sets the duration for which a token remains valid.
> The value required follows the [ISO-8601 format](https://en.wikipedia.org/wiki/ISO_8601) for durations.
> <br>For example :
> * **PT20M** → Period of Time: 20 Minutes
> * **PT1H30M** → Period of Time: 1 Hour 30 Minutes
> * **P2D** → Period of 2 Days (no T, because no time part)
> * **P1DT2H** → Period of 1 Day and 2 Hours
>
> The default value for our app is **PT60M** (60 minutes).