plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.rim.droid.data"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        val baseUrl = project.findProperty("BASE_URL") as? String ?: "https://alpha.syazy.com:1180/api/"
        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            // R8 runs on the app module; keep library minify off for project deps.
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        buildConfig = true
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
}

kotlin {
    jvmToolchain(libs.versions.jdk.get().toInt())
}

dependencies {
    api(project(":domain"))

    // Network
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Paging
    implementation(libs.paging.runtime)

    // Secure storage
    implementation(libs.security.crypto)
    implementation(libs.core.ktx)

    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(project(":core-test"))
}
