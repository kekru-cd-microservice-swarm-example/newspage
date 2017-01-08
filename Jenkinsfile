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
      parallel(
          'nummer 1': {
                def e1 = an.startTestenvironment('-1')
                newspageWebport = e1.getPublishedPort('newspage', 8081)
                echo '8081 -> ' + newspageWebport
                echo '7379 -> ' + e1.getPublishedPort('webdis', 7379)
          },
          'nummer 2': {
                def e1 = an.startTestenvironment('-2')
                echo '8081 -> ' + e1.getPublishedPort('newspage', 8081)
                echo '7379 -> ' + e1.getPublishedPort('webdis', 7379)
          }
      )
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
