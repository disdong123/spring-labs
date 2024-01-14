dependencies {
    implementation(project(":labs-domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo:4.12.0")
}
