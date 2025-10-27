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
set STATION_FILE=campus-delivery-location.json
set CONTAINER_NAME=trottiul_container

REM Stop and remove any existing container with the same name
docker rm -f %CONTAINER_NAME% >nul 2>&1

REM Build the Docker image, failing if there are any errors
echo Building Docker image...
docker build -t %IMAGE_NAME% .
if errorlevel 1 (
    echo Error: Docker image build failed!
    exit /b 1
)

REM Run the Docker container with a specified container name
docker run ^
    --env-file config.env ^
    -v "%RESOURCE_DIR%%EMPLOYEES_IDUL_FILE%:/app/data/Employe.e.s.csv" ^
    -v "%RESOURCE_DIR%%SEMESTER_FILE%:/app/data/semesters-252627.json" ^
    -v "%RESOURCE_DIR%%STATION_FILE%:/app/data/campus-delivery-location.json" ^
    --name %CONTAINER_NAME% ^
    -p 8080:8080 ^
    %IMAGE_NAME%
