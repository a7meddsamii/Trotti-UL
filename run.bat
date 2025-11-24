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
set "IMAGE_NAME=trottiul"
set "RESOURCE_DIR=%cd%\src\main\resources\data\"
set "EMPLOYEES_IDUL_FILE=Employe.e.s.csv"
set "SEMESTER_FILE=semesters-252627.json"
set "STATION_FILE=campus-delivery-location.json"
set "CONTAINER_NAME=trottiul_container"
set "DEBUG_PORT=5005"
set "DEBUG_MODE=false"

REM ========== Parse command line arguments ==========
:parse_args
if "%~1"=="" goto after_parse
if "%~1"=="--debug" (
    set "DEBUG_MODE=true"
    shift
    goto parse_args
)
if "%~1"=="--debug-port" (
    if "%~2"=="" (
        echo Error: Missing port value after --debug-port
        exit /b 1
    )
    set "DEBUG_PORT=%~2"
    shift & shift
    goto parse_args
)
if "%~1"=="--help" (
    echo Usage: %~nx0 [--help] [--debug] [--debug-port PORT]
    echo Options:
    echo   no options          Start the application in normal mode
    echo   --debug             Start the application in DEBUG mode
    echo   --debug-port PORT   Specify the debug port (default: 5005)
    echo   --help              Show this help message and exit
    exit /b 0
)
echo Unknown option: %~1
exit /b 1

:after_parse

REM Stop and remove any existing container with the same name
echo Stopping and removing existing container...
docker stop %CONTAINER_NAME% >nul 2>&1
docker rm %CONTAINER_NAME% >nul 2>&1


REM Build the Docker image, failing if there are any errors
echo Building Docker image...
docker build -t %IMAGE_NAME% .
if errorlevel 1 (
    echo Error: Docker image build failed!
    exit /b 1
)

REM ========== Run container depending on debug mode ==========
if /i "%DEBUG_MODE%"=="true" (
    echo ##############################################
    echo # Starting container with JVM debug on port %DEBUG_PORT%...
    echo # Connect your IDE debugger to localhost:%DEBUG_PORT%
    echo ##############################################
    docker run ^
        --env-file config.env ^
        -e "JAVA_TOOL_OPTIONS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:%DEBUG_PORT%" ^
        -v "%RESOURCE_DIR%%EMPLOYEES_IDUL_FILE%:/app/data/Employe.e.s.csv" ^
        -v "%RESOURCE_DIR%%SEMESTER_FILE%:/app/data/semesters-252627.json" ^
        -v "%RESOURCE_DIR%%STATION_FILE%:/app/data/campus-delivery-location.json" ^
        --name %CONTAINER_NAME% ^
        -p 8080:8080 ^
        -p %DEBUG_PORT%:%DEBUG_PORT% ^
        %IMAGE_NAME%
) else (
    echo Starting container normally...
    docker run ^
        --env-file config.env ^
        -v "%RESOURCE_DIR%%EMPLOYEES_IDUL_FILE%:/app/data/Employe.e.s.csv" ^
        -v "%RESOURCE_DIR%%SEMESTER_FILE%:/app/data/semesters-252627.json" ^
        -v "%RESOURCE_DIR%%STATION_FILE%:/app/data/campus-delivery-location.json" ^
        --name %CONTAINER_NAME% ^
        -p 8080:8080 ^
        %IMAGE_NAME%
)
