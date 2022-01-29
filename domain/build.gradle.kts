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
    implementation(project(mapOf("path" to DependenciesPlugin.ModuleCommon)))

    implementation(DependenciesPlugin.KotlinXCoroutines)
    //use dagger instead of hilt for platform independence
    implementation(DependenciesPlugin.Dagger)
    implementation(DependenciesPlugin.Moshi)
    implementation(DependenciesPlugin.MoshiCodeGenerator)
    implementation(DependenciesPlugin.MoshiKotlin)
}