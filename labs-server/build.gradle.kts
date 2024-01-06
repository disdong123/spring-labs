dependencies {
    implementation(project(":labs-domain"))
    implementation(project(":labs-jpa"))
    implementation(project(":labs-common"))
    implementation(libs.spring.boot.starter.web)
}

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}
