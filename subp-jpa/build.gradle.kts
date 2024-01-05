@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.plugin.jpa)
}

dependencies {
    implementation(project(":subp-domain"))
    api(libs.spring.boot.starter.data.jpa)
    api(libs.querydsl.jpa) {
        artifact {
            classifier = "jakarta"
        }
    }
    kapt(libs.querydsl.apt) {
        artifact {
            classifier = "jakarta"
        }
    }
    kapt(libs.jakarta.persistence.api)
    kapt(libs.jakarta.annotation.api)
    runtimeOnly(libs.mysql.connector.java)
}
