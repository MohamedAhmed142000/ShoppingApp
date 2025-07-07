plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id ("dagger.hilt.android.plugin")// ✅ أضف هذا
    id ("com.google.gms.google-services")
    id ("kotlin-kapt")
}

android {
    namespace = "com.example.shoppingapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.shoppingapp"
        minSdk = 24
        targetSdk = 36
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    implementation(libs.room.runtime)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.androidx.navigation.compose)
// Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
// OkHttp (اختياري للتسجيل واللوج)
    implementation(libs.logging.interceptor)
//عشان نعرض صور المنتجات من الإنترنت نحتاج مكتبة Coil.
    implementation(libs.coil.compose)
    // Hilt
    implementation (libs.hilt.android)
    kapt (libs.hilt.compiler)

    // Hilt with ViewModel
    implementation (libs.androidx.hilt.navigation.compose)

    implementation (libs.firebase.auth.ktx)
    implementation (platform(libs.firebase.bom)) // BOM
    implementation (libs.firebase.firestore.ktx)       // Firestore

    implementation(libs.coil.compose.v250)
}