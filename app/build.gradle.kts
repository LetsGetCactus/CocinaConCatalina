plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")

}

android {
    namespace = "com.letsgetcactus.cocinaconcatalina"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.letsgetcactus.cocinaconcatalina"
        minSdk = 26
        targetSdk = 36
        versionCode = 2
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    signingConfigs {
        create("release") {
            storeFile = (System.getenv("MY_KEYSTORE_PATH") ?: project.findProperty("MY_KEYSTORE_PATH"))?.let { file(it) }
            storePassword = project.findProperty("MY_KEYSTORE_PASSWORD") as String
            keyAlias = project.findProperty("MY_KEY_ALIAS") as String
            keyPassword = project.findProperty("MY_KEY_PASSWORD") as String

        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.ui)
    implementation(libs.firebase.appcheck.playintegrity)
    implementation(libs.firebase.appcheck.debug)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.ui.tooling.preview.v193)
    implementation(libs.androidx.compose.material.icons.extended)
    debugImplementation(libs.androidx.compose.ui.tooling.v193)
    implementation("androidx.compose.material:material-icons-extended")

    // Coil para Compose
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    // Google Play Services Auth
    implementation(libs.play.services.auth)

    // Material3
    implementation(libs.androidx.compose.ui.v170)
    implementation(libs.androidx.compose.ui.tooling.preview.v170)
    implementation(libs.activity.compose.v190)
    implementation(libs.androidx.lifecycle.runtime.ktx.v280)
    implementation(libs.material)

    // DataStore
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.ui.v160)
    implementation(libs.androidx.compose.ui.ui.tooling.preview)
    debugImplementation(libs.ui.tooling)

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.5.0"))
    implementation("com.google.firebase:firebase-storage")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation(libs.firebase.functions)
    implementation(libs.firebase.analytics)


    //Google
    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")


    //Coroutines
    implementation(libs.kotlinx.coroutines.play.services)

}


