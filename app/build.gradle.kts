plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.example.aston_intensiv_final_project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.aston_intensiv_final_project"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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

    buildFeatures {
        viewBinding = true
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.core:core-splashscreen:1.0.1")
    implementation("com.airbnb.android:lottie:4.1.0")

    implementation("com.squareup.moshi:moshi-kotlin:1.15.0")
    // Retrofit with Moshi Converter
    implementation("com.squareup.retrofit2:converter-moshi:2.10.0")

    //rxjava3
    implementation ("io.reactivex.rxjava3:rxjava:3.1.8")
    implementation ("com.squareup.retrofit2:adapter-rxjava3:2.10.0")
    implementation ("io.reactivex.rxjava3:rxandroid:3.0.2")
//    implementation ("com.squareup.retrofit2:retrofit:2.10.0")
//    implementation ("com.squareup.retrofit2:converter-gson:2.10.0")

    //Moxy
    implementation ("com.github.moxy-community:moxy:2.2.2")
    kapt ("com.github.moxy-community:moxy-compiler:2.2.2")
    implementation ("com.github.moxy-community:moxy-android:2.2.2")
    implementation ("com.github.moxy-community:moxy-androidx:2.2.2")
    implementation ("com.github.moxy-community:moxy-ktx:2.2.2")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    //coil
    implementation("io.coil-kt:coil:2.6.0")
    //glide
    implementation("com.github.bumptech.glide:glide:4.11.0")
    kapt ("com.github.bumptech.glide:compiler:4.11.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}