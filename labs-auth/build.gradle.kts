dependencies {
    implementation(project(":labs-common"))
    implementation(project(":labs-cache"))
    implementation(project(":labs-domain"))
    implementation(project(":labs-jpa"))
    implementation(libs.gson)

    testImplementation(libs.h2.database)
    testImplementation(libs.embedded.redis)
}
