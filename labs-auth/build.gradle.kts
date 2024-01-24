dependencies {
    implementation(project(":labs-common"))
    implementation(project(":labs-cache"))
    implementation(project(":labs-domain"))
    implementation(project(":labs-domain-jpa"))
    implementation(project(":labs-domain-mongo"))
    implementation(libs.gson)

    testImplementation(libs.h2.database)
    testImplementation("org.testcontainers:testcontainers:1.19.3")
}
