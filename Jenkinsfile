@Library('github.com/kekru-cd-microservice-swarm-example/cd-main@master')
import steps.CDMain

def an
def newspageWebport

node {
   def commit_id    
  
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/kekru-cd-microservice-swarm-example/newsservice'
      //sh 'rm -R cd-main'
      
      an = new CDMain(steps)
      an.init()
	  //sh 'tree .'
	  //an.docker 'ps'
	  //def pwd = sh (script: 'pwd', returnStdout:true).trim()
	  //println "PWD: " + pwd
	  

   }
   stage('Build') {
      // Run the maven build
      sh 'chmod 777 mvnw'
      sh './mvnw clean package'
      sh './docker build -t manager1:5000/cd/newspage:'+an.commitId+' .'
      sh './docker push manager1:5000/cd/newspage:'+an.commitId
      sh './docker build -t manager1:5000/cd/newspage-mongo:'+an.commitId+' mongodb/mongodocker'
      sh './docker push manager1:5000/cd/newspage-mongo:'+an.commitId
   }
   stage('Starte Testumgebung') {
      //parallel(
        //  'nummer 1': {
                def e1 = an.startTestenvironment('-1')
                sh './docker service update --replicas 1 --image manager1:5000/cd/newspage:'+an.commitId+' ' + e1.fullServiceName('newspage')
                //sh './docker service update --replicas 1 --image manager1:5000/cd/newspage-mongo:'+an.commitId+' ' + e1.fullServiceName('newspage-mongo')
                //sh 'sed "s|,]|]|g" <<< "["$(docker service inspect --format=\'{"name": {{json .Spec.Name}}, "portmappings": {{json .Endpoint.Ports}}},\' $(docker stack services -q cd'+an.commitId+'))"]"'
                newspageWebport = e1.getPublishedPort('newspage', 8081)
                echo '8081 -> ' + newspageWebport
                echo '7379 -> ' + e1.getPublishedPort('webdis', 7379)
                def mongoPort = e1.getPublishedPort('newspage-mongo', 27017)
                echo '27017 -> ' + mongoPort 
                def network = e1.getNetworkName()
                sh './docker service create --name newspage-myselenium -p 0:80 -p 0:4444 --network '+network+' whiledo/selenium-firefox-webvnc:latest'
                def seleniumPort80 = an.getPublishedPortOfService('newspage-myselenium', 80)
                def seleniumPort4444 = an.getPublishedPortOfService('newspage-myselenium', 4444)
                echo 'Selenium Viewer: ' + seleniumPort80
                echo 'Selenium Interface: ' + seleniumPort4444
                
                try{
                    sh './docker run --rm wait 10.1.6.210 '+newspageWebport
                    sh './docker run --rm wait 10.1.6.210 '+mongoPort
                    sh './docker run --rm wait 10.1.6.210 '+seleniumPort4444
                    //sh './docker run --rm -e TIMEOUT=60 -e TARGETS=10.1.6.210:'+newspageWebport+',10.1.6.210:'+mongoPort+',10.1.6.210:'+seleniumPort4444+' waisbrot/wait'
                    
                    sh './mvnw test-compile surefire:test@run-selenium -Dselenium.host=10.1.6.210 -Dselenium.port='+seleniumPort4444+' -Dmongo.host=10.1.6.210 -Dmongo.port='+mongoPort
                }finally{
                    sh './docker service rm newspage-myselenium'
                }
          //},
          //'nummer 2': {
            //    def e1 = an.startTestenvironment('-2')
            //    echo '8081 -> ' + e1.getPublishedPort('newspage', 8081)
            //    echo '7379 -> ' + e1.getPublishedPort('webdis', 7379)
          //}
      //)
   }
}


   stage ('Live Deployment'){
     def userInput = input(
       id: 'userInput', message: 'Erfolgreich getestete Version erreichbar unter http://10.1.6.210:'+newspageWebport+' Live Deployment?', parameters: [
       [$class: 'TextParameterDefinition', defaultValue: 'uat', description: 'Environment', name: 'env'],
       [$class: 'TextParameterDefinition', defaultValue: 'uat1', description: 'Target', name: 'target']
      ]) 
      echo ("Env: "+userInput['env'])
      echo ("Target: "+userInput['target'])
   }
