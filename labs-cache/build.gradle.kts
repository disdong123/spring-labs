dependencies {
    implementation(project(":labs-domain"))
    implementation(project(":labs-common"))
    implementation(libs.spring.boot.starter.data.redis)
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")
}
