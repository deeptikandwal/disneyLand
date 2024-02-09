@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.ktlint)
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.paparazzi)
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

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()

    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmVersion.get()
    }
}

dependencies {
    implementation(project(":domain"))
    // compose
    api(libs.lifecycleRuntime)
    api(libs.activity.compose)
    api(platform(libs.composeBom))
    api("androidx.compose.ui:ui")
    api("androidx.compose.ui:ui-graphics")
    api("androidx.compose.ui:ui-tooling-preview")
    api(libs.materialLibrary)
    // coil
    implementation(libs.coil.compose)
    api(libs.navigation.compose)

    // coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.coroutines)

    androidTestImplementation(platform(libs.composeBom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.monitor)
    implementation(libs.junit.ktx)
    implementation(libs.retrofit)

    // test
    testImplementation(libs.bundles.testBundle)

    // Mockk
    testImplementation(libs.bundles.mockkBundle)

    // hilt
    implementation(libs.hiltAndroid)
    kapt(libs.hiltAndroidCompiler)
    implementation(libs.hilt.navigation.fragment)
    kapt(libs.kotlinMetaData)
    api(libs.hiltNavigation)
}
