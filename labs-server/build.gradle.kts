dependencies {
    api(project(":labs-jpa"))
    implementation(project(":labs-common"))
    implementation(project(":labs-auth"))
    implementation(project(":labs-cache"))
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.webflux)

    testImplementation("org.wiremock:wiremock:3.3.1")
    testImplementation(libs.h2.database)
    testImplementation("it.ozimov:embedded-redis:0.7.2")
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
