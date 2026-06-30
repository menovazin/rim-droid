plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.rim.droid.domain"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
}

dependencies {
    // Domain references PagingData only (no Android UI framework).
    implementation(libs.paging.common)
    implementation(libs.kotlinx.coroutines.android)

    // Serialization (for type-safe navigation routes passing domain entities)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(project(":core-test"))
}
