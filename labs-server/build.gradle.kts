dependencies {
    implementation(project(":labs-domain"))
    implementation(project(":labs-domain-jpa"))
    implementation(project(":labs-domain-mongo"))
    implementation(project(":labs-common"))
    implementation(project(":labs-auth"))
    implementation(project(":labs-cache"))
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.webflux)

    testImplementation(libs.wiremock)
    testImplementation(libs.h2.database)
    testImplementation(libs.de.flapdoodle.embed.mongo.spring30x)
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
