@Library('github.com/kekru-cd-microservice-swarm-example/cd-main@master')
import steps.AlphaNachrichten

node {
  
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/kekru-cd-microservice-swarm-example/newsservice'
      sh 'chmod +x setup-dockerclient'
      sh './setup-dockerclient'
      def an = new AlphaNachrichten(steps)
      an.docker 'ps'
	  an.printTree
   }
   stage('Build') {
      // Run the maven build
      sh 'chmod 777 mvnw'
      sh './mvnw clean package'
      sh './docker build -t alphanachrichten/newspage .'
   }
   stage('Starte Testumgebung') {
      //sh './docker stack deploy --compose-file base-setup.stack.yml test1'
      //sh './docker stack rm test1'
   }
}
