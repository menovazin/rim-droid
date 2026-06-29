plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.rim.droid.coretest"
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
    api(libs.junit)
    api(libs.mockito.kotlin)
    api(libs.mockito.inline)
    api(libs.core.testing)
    api(libs.turbine)
    api(libs.truth)
    api(libs.kotlinx.coroutines.test)
    api(libs.paging.common.test)
}
