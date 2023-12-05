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
    namespace = "com.disneyLand"
    compileSdk = 33

    defaultConfig {
        minSdk = 21

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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    // paging
    val pagingVersion = "3.2.0-alpha06"
    api("androidx.paging:paging-runtime:$pagingVersion")
    api("androidx.paging:paging-compose:1.0.0-alpha20")

    // hilt
    api("com.google.dagger:hilt-android:2.45")
    kapt("com.google.dagger:hilt-android-compiler:2.45")
    api("androidx.hilt:hilt-navigation-fragment:1.0.0")
    kapt("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.5.0")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    // test
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("app.cash.turbine:turbine:1.0.0")
    testImplementation("junit:junit:4.13.2")
}
