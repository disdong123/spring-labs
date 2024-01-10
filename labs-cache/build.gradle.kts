dependencies {
    api(project(":labs-domain"))
    api(project(":labs-common"))
    implementation(libs.spring.boot.starter.data.redis)
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")

    testImplementation("it.ozimov:embedded-redis:0.7.2")
}
