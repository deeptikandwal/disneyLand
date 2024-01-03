plugins {
    id("com.android.library")
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
        minSdk = rootProject.extra.get("minSdk") as Int

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
        kotlinCompilerExtensionVersion =
            rootProject.extra.get("kotlinCompilerExtensionVersion") as String
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kotlinOptions {
        jvmTarget = rootProject.extra.get("jvmVersion") as String
    }
}

dependencies {
    implementation(project(":domain"))
    // compose
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${rootProject.extra.get("lifecycleRuntime")}")
    implementation("androidx.activity:activity-compose:${rootProject.extra.get("activityCompose")}")
    implementation(platform("androidx.compose:compose-bom:${rootProject.extra.get("composeBom")}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    // coil
    implementation("io.coil-kt:coil-compose:${rootProject.extra.get("coil")}")
    implementation("androidx.navigation:navigation-compose:${rootProject.extra.get("navigation")}")

    // coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${rootProject.extra.get("coroutines")}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${rootProject.extra.get("coroutines")}")

    androidTestImplementation(platform("androidx.compose:compose-bom:${rootProject.extra.get("composeBom")}"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.core:core-ktx:${rootProject.extra.get("ktx")}")
    implementation("androidx.appcompat:appcompat:${rootProject.extra.get("appCompat")}")
    implementation("com.google.android.material:material:${rootProject.extra.get("material_version")}")
    implementation("androidx.test:monitor:${rootProject.extra.get("testMonitor")}")
    implementation("androidx.test.ext:junit-ktx:${rootProject.extra.get("junitTest")}")
    implementation("com.squareup.retrofit2:retrofit:${rootProject.extra.get("retrofit")}")

    // test
    testImplementation("com.squareup.okhttp3:okhttp:${rootProject.extra.get("retrofit")}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.extra.get("coroutineTesting")}")
    testImplementation("androidx.arch.core:core-testing:${rootProject.extra.get("archCoreTesting")}")
    testImplementation("app.cash.turbine:turbine:${rootProject.extra.get("turbine")}")
    testImplementation("junit:junit:${rootProject.extra.get("junit")}")
    testImplementation("androidx.test.ext:junit:${rootProject.extra.get("junitTest")}")

    androidTestImplementation("androidx.arch.core:core-testing:${rootProject.extra.get("archCoreTesting")}")
    androidTestImplementation("junit:junit:${rootProject.extra.get("junit")}")
    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra.get("junitTest")}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra.get("espresso")}")

    // Mockk
    testImplementation("io.mockk:mockk:${rootProject.extra.get("mockk")}")
    testImplementation("io.mockk:mockk-android:${rootProject.extra.get("mockk")}")
    testImplementation("pl.pragmatists:JUnitParams:1.1.1")

    // hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra.get("hilt")}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra.get("hilt")}")
    implementation("androidx.hilt:hilt-navigation-fragment:${rootProject.extra.get("hiltNavigation")}")
    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:${rootProject.extra.get("kotlinMetaData")}")
    implementation("androidx.hilt:hilt-navigation-compose:${rootProject.extra.get("hiltCompose")}")
}
