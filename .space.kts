job("Build and publish") {
    container("gradle") {
        env["MAVEN_USERNAME"] = Secrets("maven_username")
        env["MAVEN_PASSWORD"] = Secrets("maven_password")
        kotlinScript { api ->
            api.gradle("build")
            api.gradle("publish")
        }
    }
}