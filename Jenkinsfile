@Library('github.com/kekru-cd-microservice-swarm-example/jenkins-shared@master')
import steps.CDMain

def an
def newspageWebport
def newspageImageName
def newspageMongoImageName
def traefikPort
def stack

try{

	node {
	   def commit_id    
	  
	   stage('Preparation') { 
		  git 'https://github.com/kekru-cd-microservice-swarm-example/newspage'
		  
		  an = new CDMain(steps)
		  an.init()
		  

	   }
	   stage('Build') {
		  
		  sh 'chmod 777 mvnw'
		  sh './mvnw clean package'
		  newspageImageName = an.buildAndPush('newspage')
		  newspageMongoImageName = an.buildAndPush('newspage-mongo', 'mongodb/mongodocker')
		  
	   }
	 
	  
	   def seleniumName = 'newspage-myselenium' + an.commitId
	   def mongoPort
	   def seleniumPort80
	   def seleniumPort4444

	   try{      

			stage('Starte Testumgebung') {            
			
				stack = an.startTestenvironment('news')
				sh './docker service update --replicas 1 --image ' + newspageImageName + ' ' + stack.fullServiceName('newspage')
				sh 'echo "" | ./redi.sh -s newspage-mongo-primary'
				sh './docker service update --replicas 1 --image ' + newspageMongoImageName + ' ' + stack.fullServiceName('newspage-mongo')

				newspageWebport = stack.getPublishedPort('newspage', 8081)
				echo '8081 -> ' + newspageWebport
				traefikPort = stack.getPublishedPort('traefik', 80)
				echo 'Traefik -> ' + traefikPort
				mongoPort = stack.getPublishedPort('newspage-mongo', 27017)
				echo '27017 -> ' + mongoPort 
				def network = stack.getNetworkName()
				sh './docker service create --name '+seleniumName+' -p 0:80 -p 0:4444 --network '+network+' manager1:5000/whiledo/selenium-firefox-webvnc:latest'
				seleniumPort80 = an.getPublishedPortOfService(seleniumName, 80)
				seleniumPort4444 = an.getPublishedPortOfService(seleniumName, 4444)
				echo 'Selenium Viewer: ' + seleniumPort80
				echo 'Selenium Interface: ' + seleniumPort4444

				
				an.waitForTCP(newspageWebport)
				an.waitForTCP(mongoPort)
				an.waitForTCP(seleniumPort4444)     
			
			}

			stage('Integrationstests') {        
					sh './mvnw test-compile surefire:test@run-selenium -Dwebsite.host=traefik -Dwebsite.port=80 -Dselenium.host=10.1.6.210 -Dselenium.port='+seleniumPort4444+' -Dmongo.host=10.1.6.210 -Dmongo.port='+mongoPort
			}

	   }finally{
		   sh './docker service rm ' + seleniumName       
	   }
	}


	stage ('Manuelle Tests'){
		def userInput = input(
			id: 'userInput', message: 'Erfolgreich getestete Version erreichbar unter http://10.1.6.210:'+traefikPort+'/newspage/ Live Deployment?'
		)

	}

}finally{    
	node {
		stage ('Entferne Testumgebung'){
			stack.removeStack()
		}
	}
}

node {
    stage ('Live Deployment'){
        an.deployInProduction('newspage', newspageImageName)
        an.deployInProduction('newspage-mongo', newspageMongoImageName)
    }
}
