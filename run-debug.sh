#!/bin/bash

# This script sets up and runs a Docker container for a web application in DEBUG mode.

echo "Make sure to run this script from the root directory of the project."
echo "Starting the web application in DEBUG mode..."

# check if config.env file exists
if [ ! -f config.env ]; then
    echo "Error: config.env file not found!"
    echo "You can create one by filling the config.env.example with the required values then renaming it to config.env"
    exit 1
fi


# Define variables
IMAGE_NAME="trottiul"
RESOURCE_DIR="$(pwd)/src/main/resources/data/"
EMPLOYEES_IDUL_FILE="Employe.e.s.csv"
SEMESTER_FILE="semesters-252627.json"
CONTAINER_NAME="trottiul_container_debug"
STATION_FILE="campus-delivery-location.json"
DEBUG_PORT=5005

# Parse command line arguments for custom debug port
while [[ $# -gt 0 ]]; do
    case $1 in
        --debug-port)
            DEBUG_PORT="$2"
            shift 2
            ;;
        *)
            echo "Unknown option: $1"
            echo "Usage: $0 [--debug-port PORT]"
            exit 1
            ;;
    esac
done

# stop and remove any existing container with the same name if it exists
function stop_container() {
    if [ "$(docker ps -aq -f name=$CONTAINER_NAME)" ]; then
        echo "Stopping and removing existing debug container..."
        docker stop $CONTAINER_NAME
        docker rm $CONTAINER_NAME
    fi
}

# Run the Docker container in debug mode
function run_image() {
    echo "Starting container with JVM debug on port $DEBUG_PORT..."
    echo "Connect your IDE debugger to localhost:$DEBUG_PORT"
    
    docker run \
        --env-file config.env \
        -e JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:$DEBUG_PORT" \
        -v "$RESOURCE_DIR$EMPLOYEES_IDUL_FILE:/app/data/Employe.e.s.csv" \
        -v "$RESOURCE_DIR$SEMESTER_FILE:/app/data/semesters-252627.json" \
        -v "$RESOURCE_DIR$STATION_FILE:campus-delivery-location.json" \
        --name $CONTAINER_NAME \
        -p 8080:8080 \
        -p $DEBUG_PORT:$DEBUG_PORT \
        $IMAGE_NAME
}

# Build the Docker image, failing if there are any errors
function start_app() {
    stop_container
    echo "Building Docker image..."
    docker build -t trottiul .
    
    if [ $? -ne 0 ]; then
        echo "Error: Docker image build failed!"
        exit 1
    fi
    
    run_image
}

start_app
