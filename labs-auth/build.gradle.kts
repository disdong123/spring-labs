dependencies {
    api(project(":labs-common"))
    api(project(":labs-cache"))
    api(project(":labs-jpa"))

    implementation("com.google.code.gson:gson:2.10.1")

    testImplementation(libs.h2.database)
}
