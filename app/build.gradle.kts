plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}


android {
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId
        minSdkPreview = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

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
        viewBinding = true
    }
    namespace = Config.applicationId
}

dependencies {
    implementation(Deps.kotlin)

    implementation(project(Modules.Common.core))
    implementation(project(Modules.Common.data))
    implementation(project(Modules.Common.network))
    implementation(project(Modules.Common.sharedPreferences))
    implementation(project(Modules.Common.storage))

    implementation(project(Modules.Feature.home.api))
    implementation(project(Modules.Feature.home.impl))
    implementation(project(Modules.Feature.information.api))
    implementation(project(Modules.Feature.information.impl))
    implementation(project(Modules.Feature.system.api))
    implementation(project(Modules.Feature.system.impl))
    implementation(project(Modules.Feature.charts.api))
    implementation(project(Modules.Feature.charts.impl))
    implementation(project(Modules.Feature.connection.api))
    implementation(project(Modules.Feature.connection.impl))
    implementation(project(Modules.Feature.settings.api))
    implementation(project(Modules.Feature.settings.impl))
    implementation(project(Modules.Feature.auth.api))
    implementation(project(Modules.Feature.auth.impl))
    implementation(project(Modules.Feature.account.api))
    implementation(project(Modules.Feature.account.impl))

    implementation(Deps.Navigation.navigation)
    implementation(Deps.Navigation.cicerone)

    implementation(Deps.AndroidX.core)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.material)

    implementation(Deps.animation)

    testImplementation(Deps.Test.jUnit)
    androidTestImplementation(Deps.Test.test)
    androidTestImplementation(Deps.Test.espresso)

    implementation(Deps.Network.gson)

    implementation(Deps.Koin.koinCore)
    implementation(Deps.Koin.koinAndroid)

    implementation(Deps.Room.runtime)
    kapt(Deps.Room.compiler)
    implementation(Deps.Room.ktx)
}