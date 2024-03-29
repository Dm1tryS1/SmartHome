object Deps {

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.JetBrains.kotlin}"
    object AndroidX {
        const val core = "androidx.core:core-ktx:${Versions.AndroidX.core}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.AndroidX.appcompat}"
        const val material = "com.google.android.material:material:${Versions.AndroidX.material}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.AndroidX.fragment}"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:2.4.2"
        const val compiler = "androidx.room:room-compiler:2.4.2"
        const val ktx = "androidx.room:room-ktx:2.4.2"
    }

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1"
    }

    object Navigation {
        const val navigation = "androidx.navigation:navigation-fragment:2.5.1"
        const val cicerone = "com.github.terrakok:cicerone:7.0"
    }

    object Koin {
        const val koinCore = "io.insert-koin:koin-core:${Versions.Koin.koin_version}"
        const val koinAndroid = "io.insert-koin:koin-android:${Versions.Koin.koin_version}"
    }

    object Network {
        const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.Network.okHttp_version}"
        const val okHttpInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.Network.okHttp_version}"
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.Network.retrofit_version}"
        const val gson =
            "com.squareup.retrofit2:converter-gson:${Versions.Network.retrofit_version}"
    }

    object Test {
        const val jUnit = "junit:junit:4.13.2"
        const val test = "androidx.test.ext:junit:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }

    const val lottie = "com.airbnb.android:lottie:5.2.0"
    const val esptouch = "com.github.EspressifApp:lib-esptouch-android:1.1.1"
    const val animation = "com.daimajia.androidanimations:library:2.4@aar"
    const val recycler = "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-viewbinding:4.3.0"
    const val graphic = "com.github.PhilJay:MPAndroidChart:v3.1.0"

    const val kotlinXSerialization =
        "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlinXSerialization}"

    const val serializationJson =
        "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serializationJson}"

}