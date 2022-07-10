job("Build and publish") {
    container("gradle:6.9.2") {
        env["MAVEN_USERNAME"] = Secrets("maven_username")
        env["MAVEN_PASSWORD"] = Secrets("maven_password")
        kotlinScript { api ->
            api.gradlew("build")
            //api.gradlew("publish")
        }
    }
}