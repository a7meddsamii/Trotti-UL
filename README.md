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
- Pass is valid for a  single academic session (from session start to the next session)


## Installation

### Prerequisites

- **Java 21+**
- **Docker**

### Clone the repository:

-
   ```bash
   git https://github.com/GLO4003UL/a25-projet-trotti-ul-a25-eq-10.git
   cd a25-projet-trotti-ul-a25-eq-10
    ```
## Starting the app

The app requires certain environment variables to be set for proper operation, therefore you need to provide an `.env` file.

#### 1. Create an `.env` file

- At the root of your project, create a file named `config.env`.

#### 2. Add required environment variables

Edit the `config.env` file and add the required variables, for example:

```env
TOKEN_EXPIRATION_DURATION=PT60M
SMTP_USER="email@adress.ca"
SMTP_PASS="password"
SMTP_HOST="smtp.example.com"
SMTP_PORT:"587"
```
Replace the values with information relevant to your setup.

### Running in Docker
There is a runner script available to simplify the process of building and running the Docker container.
Simply make sure you are at the root of the project before executing it.

- On Unix-based systems (Linux, macOS):
   ```bash
    chmod +x run.sh
    ./run.sh
    ```
- On Windows:
   ```bash
    .\run.bat
    ```