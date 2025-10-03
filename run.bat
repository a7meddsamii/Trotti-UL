@echo off
REM This script sets up and runs a Docker container for a web application.

echo Make sure to run this script from the root directory of the project.
echo Starting the web application...

REM check if config.env file exists
if not exist config.env (
    echo Error: config.env file not found!
    echo You can create one by filling the config.env.example with the required values then renaming it to config.env
    exit /b 1
)

REM Define variables
set IMAGE_NAME=trottiul
set RESOURCE_DIR=%cd%\src\main\resources\data\
set EMPLOYEES_IDUL_FILE=Employe.e.s.csv
set SEMESTER_FILE=semesters-252627.json

REM Build the Docker image
docker build -t %IMAGE_NAME% .

REM Run the Docker container
docker run ^
    --env-file config.env ^
    -v "%RESOURCE_DIR%%EMPLOYEES_IDUL_FILE%:/app/data/Employe.e.s.csv" ^
    -v "%RESOURCE_DIR%%SEMESTER_FILE%:/app/data/semesters-252627.json" ^
    -p 8080:8080 ^
    %IMAGE_NAME%
