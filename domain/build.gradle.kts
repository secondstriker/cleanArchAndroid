import com.codewithmohsen.gradle.DependenciesPlugin

plugins {
    id("com.android.library")
    id("kotlin-kapt")
    id("kotlin-android")
    id("com.codewithmohsen.dependencies")
}

android {
    compileSdk = DependenciesPlugin.CompileSdk

    defaultConfig {
        minSdk = DependenciesPlugin.MinSdk
        targetSdk = DependenciesPlugin.TargetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(DependenciesPlugin.AndroidxCore)
    androidTestImplementation(DependenciesPlugin.AndroidxJUnit)
    androidTestImplementation(DependenciesPlugin.AndroidxEspresso)


    implementation(DependenciesPlugin.KotlinXCoroutines)
    implementation(DependenciesPlugin.KotlinXCoroutinesAndroid)
    implementation(DependenciesPlugin.Timber)

    //hilt
    implementation(DependenciesPlugin.HiltAndroid)
    kapt(DependenciesPlugin.HiltCompiler)

    // For local unit tests (robolectric)
    testImplementation(DependenciesPlugin.HiltAndroidTesting)
    kaptTest(DependenciesPlugin.HiltAndroidCompiler)

    // For instrumentation tests
    androidTestImplementation(DependenciesPlugin.HiltAndroidTesting)
    kaptAndroidTest(DependenciesPlugin.HiltAndroidCompiler)

}