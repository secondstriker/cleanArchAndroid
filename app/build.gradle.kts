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
        applicationId = "com.codewithmohsen.insurancecomparator"
        minSdk = DependenciesPlugin.MinSdk
        targetSdk = DependenciesPlugin.TargetSdk
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true

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
    implementation(project(DependenciesPlugin.ModuleDomain))
    implementation(project(DependenciesPlugin.ModuleCommonAndroid))
    implementation(project(DependenciesPlugin.ModuleRemote))
    implementation(project(DependenciesPlugin.ModuleData))
    implementation(project(DependenciesPlugin.ModulePresentation))
    implementation(project(DependenciesPlugin.ModuleFeatures))

    implementation(DependenciesPlugin.AndroidxCore)
    implementation(DependenciesPlugin.Material)
    implementation(DependenciesPlugin.AndroidxAppCompat)
    androidTestImplementation(DependenciesPlugin.AndroidxJUnit)
    androidTestImplementation(DependenciesPlugin.AndroidxEspresso)

    implementation(DependenciesPlugin.Retrofit)
    implementation(DependenciesPlugin.RetrofitMoshiConverter)
    implementation(DependenciesPlugin.OkhttpInterceptor)

    implementation(DependenciesPlugin.KotlinXCoroutines)
    implementation(DependenciesPlugin.KotlinXCoroutinesAndroid)
    implementation(DependenciesPlugin.Timber)

    //hilt
    implementation(DependenciesPlugin.HiltAndroid)
    kapt(DependenciesPlugin.HiltCompiler)
}
