dependencies {
    api(project(":labs-domain"))
    implementation(libs.spring.boot.starter.data.redis)
    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")
}
