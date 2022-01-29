import com.codewithmohsen.gradle.DependenciesPlugin

plugins {
    id("java-library")
    id("kotlin")
    id("kotlin-kapt")
    id("com.codewithmohsen.dependencies")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
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