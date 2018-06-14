node('maven') {
    stage('Build Artifact') {
        checkout scm
        sh 'mvn clean install'
        stash name: "artifact", includes: "target/talking-statues-0.0.1-SNAPSHOT.jar"
    }
    stage('Build Image') {
        unstash name: "artifact"
        sh "cp src/main/docker/Dockerfile target/Dockerfile"
        sh "oc start-build talking-statues --from-dir=target"
    }
}