plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
}
ktlint {
    disabledRules.set(setOf("no-wildcard-imports"))
}

android {
    namespace = rootProject.extra.get("appId") as String
    compileSdk = rootProject.extra.get("compileSdk") as Int
    defaultConfig {
        applicationId = "com.disneyland"
        minSdk = rootProject.extra.get("minSdk") as Int
        targetSdk = rootProject.extra.get("targetSdk") as Int
        versionCode = rootProject.extra.get("androidAppVersionCode") as Int
        versionName = rootProject.extra.get("androidAppVersionName") as String

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
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion =
            rootProject.extra.get("kotlinCompilerExtensionVersion") as String
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":presentation"))

    implementation("com.google.dagger:hilt-android:${rootProject.extra.get("hilt")}")
    implementation("androidx.test.ext:junit-ktx:${rootProject.extra.get("junitTest")}")
    implementation("androidx.test:monitor:${rootProject.extra.get("testMonitor")}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra.get("hilt")}")
    testImplementation("junit:junit:${rootProject.extra.get("junit")}")
    androidTestImplementation("junit:junit:${rootProject.extra.get("junit")}")
}
