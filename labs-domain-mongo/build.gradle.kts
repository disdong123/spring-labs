dependencies {
    implementation(project(":labs-domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

    testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo.spring30x:4.11.0")
}
