@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("dagger.hilt.android.plugin")
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.ktlint)
}
ktlint {
    android.set(true)
    ignoreFailures.set(false)
}

android {
    namespace = libs.versions.appId.get()
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = libs.versions.appId.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.androidAppVersionCode.get().toInt()
        versionName = libs.versions.androidAppVersionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
}

dependencies {
    implementation(project(":data"))
    implementation(project(":presentation"))

    implementation(libs.hiltAndroid)
    kapt(libs.hiltAndroidCompiler)
}
