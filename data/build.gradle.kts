@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.ktlint)
}

ktlint {
    disabledRules.set(setOf("no-wildcard-imports"))
}

android {
    namespace = libs.versions.appId.get()
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmVersion.get()
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.core.ktx)

    // retrofit
    implementation(libs.bundles.retrofitBundle)

    // Mockk
    testApi(libs.bundles.mockkBundle)

    // test
    testImplementation(libs.bundles.testBundle)

    // hilt
    implementation(libs.hiltAndroid)
    kapt(libs.hiltAndroidCompiler)
}
