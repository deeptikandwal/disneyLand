buildscript {

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.8.10")
    }
}
apply("$rootDir/version.gradle")

plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.45" apply false
    id("com.android.library") version "8.1.1" apply false
}
