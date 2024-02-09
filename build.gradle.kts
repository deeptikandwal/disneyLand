buildscript {

    dependencies {
        classpath(libs.kotlin.serialization.v1810)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.kapt) apply false
}
