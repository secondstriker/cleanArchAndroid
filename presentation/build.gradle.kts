import com.codewithmohsen.gradle.DependenciesPlugin

plugins {
    id("com.android.library")
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
    implementation(project(mapOf("path" to DependenciesPlugin.ModuleDomain)))
    implementation(project(mapOf("path" to DependenciesPlugin.ModuleCommonAndroid)))

    implementation(DependenciesPlugin.Timber)

    //hilt
    implementation(DependenciesPlugin.HiltAndroid)
    kapt(DependenciesPlugin.HiltCompiler)

    implementation(DependenciesPlugin.ViewModelRuntimeKtx)
    implementation(DependenciesPlugin.lifecycleViewModelKtx)
}
