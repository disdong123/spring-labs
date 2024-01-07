dependencies {
    api(project(":labs-jpa"))
    implementation(project(":labs-common"))
    implementation(project(":labs-auth"))
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.webflux)

    testImplementation("org.wiremock:wiremock:3.3.1")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
