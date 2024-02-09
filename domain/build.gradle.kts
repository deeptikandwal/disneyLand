@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    id("dagger.hilt.android.plugin")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmVersion.get()
    }
}

dependencies {

    implementation(libs.core.ktx)

    // hilt
    implementation(libs.hiltAndroid)
    kapt(libs.hiltAndroidCompiler)

    // test
    testImplementation(libs.bundles.testBundle)

    // Mockk
    testImplementation(libs.bundles.mockkBundle)
}
