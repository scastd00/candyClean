#!/bin/bash

# Executes SonarQube container at port 9000.
function initSonar() {
	sudo docker run -d -p 9000:9000 --name sonarqube -v sonarqube_conf:/opt/sonarqube/conf -v sonarqube_extensions:/opt/sonarqube/extensions -v sonarqube_logs:/opt/sonarqube/logs -v sonarqube_data:/opt/sonarqube/data sonarqube
	echo "SonarQube is now up at port 9000."
}

# Executes Jenkins container at port 9000. (Not needed because of persistence of the container)
function initJenkins() {
	sudo docker run -p 8080:8080 -p 50000:50000 -d --name jenkins -v jenkins_home:/var/jenkins_home jenkins/jenkins:lts
	echo "Jenkins is now up at port 8080."
}

# Deletes the Sonar container and makes a new connection with SonarQube.
function resetSonar() {
	echo "Restarting SonarQube"
	sudo docker container stop /sonarqube
	sudo docker container rm /sonarqube
	initSonar
}

# Deletes the Jenkins container and makes a new connection with Jenkins. (Not needed because of persistence of the container)
function resetJenkins() {
	echo "Restarting Jenkins"
	sudo docker container stop /jenkins
	sudo docker container rm /jenkins
	initJenkins
}

function resetAll() {
	resetSonar
	resetJenkins
}

echo "Initializing script"
printf "Select an option:\n0 - Initialize SonarQube and Jenkins\n1 - Initialize SonarQube\n2 - Initialize Jenkins\n3 - Reset SonarQube\n4 - Reset Jenkins\n5 - Reset All\n"
read -r option

if [ "$option" -eq "0" ]; then
	initSonar
	initJenkins
elif [ "$option" -eq "1" ]; then
	initSonar
elif [ "$option" -eq "2" ]; then
	initJenkins
elif [ "$option" -eq "3" ]; then
	resetSonar
elif [ "$option" -eq "4" ]; then
	resetJenkins
elif [ "$option" -eq "5" ]; then
	resetAll
else
	echo "Incorrect parameter" && exit 0
fi