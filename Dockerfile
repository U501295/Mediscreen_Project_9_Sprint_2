# syntax=docker/dockerfile:1
FROM openjdk:8

ADD target/mediscreen_P9_JB_sprint_2-0.0.1-SNAPSHOT.jar patienthistory.jar

ENTRYPOINT ["java","-jar","/patienthistory.jar"]
