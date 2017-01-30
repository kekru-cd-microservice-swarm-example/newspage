@Library('github.com/kekru-cd-microservice-swarm-example/jenkins-shared@master')
import steps.CDMain

def an
def newspageWebport
def newspageImageName
def newspageMongoImageName

node {
   def commit_id    
  
   stage('Preparation') { 
      git 'https://github.com/kekru-cd-microservice-swarm-example/newsservice'
      
      an = new CDMain(steps)
      an.init()
	  

   }
   stage('Build') {
      
      sh 'chmod 777 mvnw'
      sh './mvnw clean package'
      newspageImageName = an.buildAndPush('newspage')
      newspageMongoImageName = an.buildAndPush('newspage-mongo', 'mongodb/mongodocker')
      
   }
   stage('Starte Testumgebung') {
       def commitId = an.commitId;
      
        def e1 = an.startTestenvironment('news')
        sh './docker service update --replicas 1 --image ' + newspageImageName + ' ' + e1.fullServiceName('newspage')
        sh 'echo "" | ./redi.sh -s newspage-mongo-primary'
        sh './docker service update --replicas 1 --image ' + newspageMongoImageName + ' ' + e1.fullServiceName('newspage-mongo')

        newspageWebport = e1.getPublishedPort('newspage', 8081)
        echo '8081 -> ' + newspageWebport
        echo '7379 -> ' + e1.getPublishedPort('webdis', 7379)
        def mongoPort = e1.getPublishedPort('newspage-mongo', 27017)
        echo '27017 -> ' + mongoPort 
        def network = e1.getNetworkName()
        sh './docker service create --name newspage-myselenium'+commitId+' -p 0:80 -p 0:4444 --network '+network+' whiledo/selenium-firefox-webvnc:latest'
        def seleniumPort80 = an.getPublishedPortOfService('newspage-myselenium', 80)
        def seleniumPort4444 = an.getPublishedPortOfService('newspage-myselenium', 4444)
        echo 'Selenium Viewer: ' + seleniumPort80
        echo 'Selenium Interface: ' + seleniumPort4444

        try{
            an.waitForTCP(newspageWebport)
            an.waitForTCP(mongoPort)
            an.waitForTCP(seleniumPort4444)     
        }catch(Exception e){
            sh './docker service rm newspage-myselenium'+commitId        
        }
   }

   stage('Integrationstests') {         
       try{
            sh './mvnw test-compile surefire:test@run-selenium -Dwebsite.host=traefik -Dwebsite.port=80 -Dselenium.host=10.1.6.210 -Dselenium.port='+seleniumPort4444+' -Dmongo.host=10.1.6.210 -Dmongo.port='+mongoPort
        }finally{
            sh './docker service rm newspage-myselenium'
        }
          
   }
}


stage ('Manuelle Tests'){
    def userInput = input(
        id: 'userInput', message: 'Erfolgreich getestete Version erreichbar unter http://10.1.6.210:'+newspageWebport+'/newspage/ Live Deployment?'
    )

}

node {
    stage ('Live Deployment'){
        an.deployInProduction('newspage', newspageImageName)
        an.deployInProduction('newspage-mongo', newspageMongoImageName)
    }
}
