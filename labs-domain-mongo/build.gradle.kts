dependencies {
    implementation(project(":labs-domain"))
    implementation(libs.spring.boot.starter.data.mongodb)

    testImplementation(libs.de.flapdoodle.embed.mongo.spring30x)
}
