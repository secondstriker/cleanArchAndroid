import com.codewithmohsen.gradle.DependenciesPlugin

plugins {
    id("com.android.library")
    id("kotlin-kapt")
    id("com.codewithmohsen.dependencies")
}

dependencies {

    implementation(DependenciesPlugin.KotlinXCoroutines)
    //use dagger instead of hilt for platform independence
    implementation(DependenciesPlugin.Dagger)
    implementation(DependenciesPlugin.Moshi)
    implementation(DependenciesPlugin.MoshiCodeGenerator)
    implementation(DependenciesPlugin.MoshiKotlin)
}