node {
    def mvnHome
    def JAVA_HOME
    def tempDir = "/VWMC/TempWorkDir"
    def currentProject = "publsub.publisher.demo"
    def project
    def topic

    stage('Preparation') { // for display purposes

        JAVA_HOME = tool name: 'JDK1.8-152', type: 'jdk'
        mvnHome = tool name: 'Maven-3.5.2', type: 'maven'
    }

    stage('Checkout from Github') {

        sh("rm -rf ${tempDir}/${currentProject}")
        sh("mkdir ${tempDir}/${currentProject}")

        dir("${tempDir}/${currentProject}") {
            git 'https://github.com/innovative-innovators/publsub.publisher.demo.git'
        }
    }

    stage('Build') {
        dir("${tempDir}/${currentProject}") {
            sh("${mvnHome}/bin/mvn clean compile")
        }
    }

    stage('Input Required Info'){

        def inputParams = input(message: 'Required Info',
                            parameters: [
                                    [$class: 'StringParameterDefinition',defaultValue: '', description: '', name: 'Project Name'],
                                    [$class: 'StringParameterDefinition',defaultValue: '', description: '', name: 'Topic Name']
                            ])

        project = inputParams['project']
        topic = inputParams['topic']

        echo("Project is : "+ project )
        echo("Topic is : "+ topic)
    }

//    stage("Execute Pubsub Stream") {
//        dir("${tempDir}/${currentProject}") {
//            sh("${mvnHome}/bin/mvn -Dtest=PublisherDemo#testArgLine -DargLine=\"-Dproject=fcr-it -Dtopic=BBB\" test")
//        }
//    }
}