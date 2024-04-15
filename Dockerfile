FROM openjdk:17
ARG JAR_FILE=./build/libs/*.jar
EXPOSE 8080

RUN mkdir -p /home/run/res/videos
RUN mkdir -p /home/run/res/images

COPY ./build/libs/hr_backend_server-0.0.1-SNAPSHOT.jar /home/run/app.jar
COPY entrypoint.sh /home/run/entrypoint.sh
RUN chmod +x /home/run/entrypoint.sh

ENTRYPOINT ["/home/run/entrypoint.sh"]