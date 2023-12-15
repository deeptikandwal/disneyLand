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
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:${rootProject.extra.get("ktx")}")
    implementation("androidx.appcompat:appcompat:${rootProject.extra.get("appCompat")}")
    implementation("com.google.android.material:material:${rootProject.extra.get("material_version")}")
    androidTestImplementation("androidx.test.ext:junit:${rootProject.extra.get("junitTest")}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${rootProject.extra.get("espresso")}")
    // paging
    api("androidx.paging:paging-runtime-ktx:${rootProject.extra.get("pagingVersion")}")
    api("androidx.paging:paging-compose:${rootProject.extra.get("pagingVersion")}")

    // hilt
    implementation("com.google.dagger:hilt-android:${rootProject.extra.get("hilt")}")
    kapt("com.google.dagger:hilt-android-compiler:${rootProject.extra.get("hilt")}")

    // test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${rootProject.extra.get("coroutineTesting")}")
    androidTestImplementation("androidx.arch.core:core-testing:${rootProject.extra.get("archCoreTesting")}")
    testImplementation("androidx.arch.core:core-testing:${rootProject.extra.get("archCoreTesting")}")
    testImplementation("app.cash.turbine:turbine:${rootProject.extra.get("turbine")}")
    testImplementation("junit:junit:${rootProject.extra.get("junit")}")

    // Mockk
    testImplementation("io.mockk:mockk:${rootProject.extra.get("mockk")}")
    testImplementation("io.mockk:mockk-android:${rootProject.extra.get("mockk")}")
}
