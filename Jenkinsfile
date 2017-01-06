@Library('github.com/kekru-cd-microservice-swarm-example/cd-main@master')
import steps.CDMain

def an = new CDMain(steps)

node {
   def commit_id    
  
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/kekru-cd-microservice-swarm-example/newsservice'
      //sh 'rm -R cd-main'
      
      an.init()
	  //sh 'tree .'
	  an.docker 'ps'
	  def pwd = sh (script: 'pwd', returnStdout:true).trim()
	  println "PWD: " + pwd


   }
   stage('Build') {
      // Run the maven build
      sh 'chmod 777 mvnw'
      //sh './mvnw clean package'
      //sh './docker build -t manager1:5000/cd/newspage:2 .'
      //sh './docker push manager1:5000/cd/newspage:2'
      //sh './docker build -t manager1:5000/cd/newspage-mongo:2 mongodb/mongodocker'
      //sh './docker push manager1:5000/cd/newspage-mongo:2'
   }
   stage('Starte Testumgebung') {
      an.startTestenvironment()
      println '8081 -> ' + an.getPublishedPort('newspage', 8081)
      println '7379 -> ' + an.getPublishedPort('webdis', 7379)
   }
}
