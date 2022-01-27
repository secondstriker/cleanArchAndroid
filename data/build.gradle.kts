import com.codewithmohsen.gradle.DependenciesPlugin

plugins {
    id("com.android.application")
    id("kotlin-kapt")
    id("kotlin-android")
    id("com.codewithmohsen.dependencies")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = DependenciesPlugin.CompileSdk

    defaultConfig {
        minSdk = DependenciesPlugin.MinSdk
        targetSdk = DependenciesPlugin.TargetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "${project.rootDir}/tools/proguard-rules-debug.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(DependenciesPlugin.AndroidxCore)
    implementation(DependenciesPlugin.Material)
    implementation(DependenciesPlugin.AndroidxAppCompat)
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

    implementation(DependenciesPlugin.MultiDexApplication)

    implementation(DependenciesPlugin.Retrofit)
    implementation(DependenciesPlugin.RetrofitMoshiConverter)
    implementation(DependenciesPlugin.OkhttpInterceptor)
}
