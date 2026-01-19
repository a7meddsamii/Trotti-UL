# A25 GLO-4003 - Projet Trotti-ul Eq-10

Laval University’s electric scooter rental service for optimal transportation.

## Table of Contents

- [Technological Environment](#technological-environment)
- [Features](#features)
    - [Account management](#account-management)
    - [Buy electric scooter passes](#buy-electric-scooter-passes)
    - [Ride activation & unlock](#ride-activation--unlock)
    - [Scooter usage at stations](#scooter-usage-at-stations)
    - [Trip Completion](#trip-completion)
    - [Trip history & usage summary](#trip-history--usage-summary)
    - [Station maintenance & technician operations](#station-maintenance--technician-operations)
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

### Ride activation & unlock

- Account is activated after a successful purchase
- User receives an email notification when the account is activated
- User is authorized to perform scooter trips only if the active session matches the purchased pass
- User can request a unique unlock code to start a trip
  - Code is numeric (4 to 6 digits)
  - Code is valid for 60 seconds
  - Code is sent by email
  - A new code is required for each trip

### Scooter usage at stations

- User can unlock a scooter at a station using a valid unlock code
- Scooters are identified by station and location number (e.g., Station X – spot #1)
- Scooter can only be unlocked if battery level is above 15%
- Stations are initialized with 80% of their maximum scooter capacity
- Only operational stations allow scooter unlock and return

### Trip completion

- User can return a scooter to any available spot on campus
- Trips under 15 minutes do not generate additional fees
- Trips are recorded in the user’s trip history
- Each trip includes:
  - Departure time
  - Departure station
  - Arrival station
  - Travel duration
- If daily unlimited usage is exceeded, an additional $5 fee is applied

### Trip history & usage summary

- User can view their trip history for the last month by default
- User can consult full details of each trip
- A usage summary is provided, including:
  - Total travel time
  - Average travel time per trip
  - Number of trips
  - Favorite station

### Station maintenance & technician operations

- Users or project managers can report station issues
- Maintenance requests are sent by email to all technicians
- A station under maintenance is paused:
  - No trips can start
  - No scooters can be returned
  - Battery charging is stopped
- Only a technician can manually restore a station to service
- Technicians can transfer scooters between stations during maintenance
  - Transfers are not considered trips
  - Scooters are placed in an *In Transfer* state
  - No battery discharge occurs during transport
- Technicians have unrestricted access to the service while employed


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

INITIAL_ADMIN_NAME=Admin Initial
INITIAL_ADMIN_EMAIL=admin@ulaval.ca
INITIAL_ADMIN_IDUL=admin00
INITIAL_ADMIN_BIRTHDATE=1990-01-01
INITIAL_ADMIN_GENDER=female
INITIAL_ADMIN_PASSWORD=ChangeMe123!@#
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