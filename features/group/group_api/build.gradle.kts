plugins {
    id("commonAndroid")
}

android {
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(project(Modules.Common.core))

    implementation(Deps.Coroutines.core)
    implementation(Deps.Koin.koinAndroid)
    implementation(Deps.Navigation.cicerone)
    implementation(Deps.Koin.koinCore)
}