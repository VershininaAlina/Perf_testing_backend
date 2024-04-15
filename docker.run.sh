#!/bin/bash

# Остановить контейнеры Docker
docker stop $(docker ps -q)

docker pull antonsibgatulin/hr_backend_server:latest
docker pull antonsibgatulin/hr_client_angular_node:latest

docker run -d -p 8080:8080 antonsibgatulin/hr_backend_server:latest
docker run -d -p 4200:4200 antonsibgatulin/hr_client_angular_node:latest

