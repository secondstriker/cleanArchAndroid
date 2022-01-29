import com.codewithmohsen.gradle.DependenciesPlugin

plugins {
    id("com.android.library")
    id("kotlin-kapt")
    id("kotlin-android")
    id("com.codewithmohsen.dependencies")
}

android {
    compileSdk = DependenciesPlugin.CompileSdk
}

dependencies {
    implementation(DependenciesPlugin.KotlinXCoroutines)
}
