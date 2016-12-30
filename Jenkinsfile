node {
   stage 'Stage 1'
     echo 'Hello World 1'

   stage 'Build'
     sh 'rm -R continuousdelivery-microservices-dockerswarm-example'
     sh 'git clone https://github.com/kekru/continuousdelivery-microservices-dockerswarm-example.git'
     dir('continuousdelivery-microservices-dockerswarm-example/microservices/newspage'){
        sh 'ls -la'
        sh 'chmod 555 mvnw'
        sh './mvnw clean install'
     }
     
   stage 'promotion'
     def userInput = input(
       id: 'userInput', message: 'Let\'s promote?', parameters: [
       [$class: 'TextParameterDefinition', defaultValue: 'uat', description: 'Environment', name: 'env'],
       [$class: 'TextParameterDefinition', defaultValue: 'uat1', description: 'Target', name: 'target']
      ]) 
      echo ("Env: "+userInput['env'])
      echo ("Target: "+userInput['target'])
   
   stage 'Stage 2'
     echo 'Hello World 2'
}
