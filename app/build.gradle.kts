import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("org.jetbrains.kotlin.plugin.serialization") version "2.3.0"
}

android {
    namespace = "com.cypress.xingcodechallengeapplication"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.cypress.xingcodechallengeapplication"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            localProperties.load(localPropertiesFile.inputStream())
        }

        val xingToken = localProperties.getProperty("XING_API_TOKEN") ?: ""
        buildConfigField("String", "XING_TOKEN", "\"$xingToken\"")

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
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
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
    implementation(libs.androidx.foundation.layout)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coil Compose
    implementation("io.coil-kt:coil-compose:2.2.2")

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")

    // Paging
    implementation("androidx.paging:paging-runtime-ktx:3.4.0")
    implementation("androidx.paging:paging-compose:3.4.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Moshi
    implementation("com.squareup.retrofit2:converter-moshi:3.0.0")
    // Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:5.3.2")

    // Kotlinx-Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.10.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    // Room
    implementation( "androidx.room:room-ktx:2.8.4")
    kapt("androidx.room:room-compiler:2.8.4")
    implementation("androidx.room:room-paging:2.8.4")

    // Koin
    implementation("io.insert-koin:koin-core:4.1.1")
    implementation("io.insert-koin:koin-android:4.1.1")
    implementation("io.insert-koin:koin-androidx-compose:4.1.1")
    implementation("io.insert-koin:koin-android:4.1.1")
    implementation("io.insert-koin:koin-androidx-compose:4.1.1")

    //Navigation Control
    implementation("androidx.navigation:navigation-compose:2.9.7")

    // Icons
    implementation("androidx.compose.material:material-icons-extended")

    // SwipeRefresh
    implementation("androidx.compose.material3:material3:1.4.0")

    // Test
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.3.0")
    testImplementation(kotlin("test"))

    // MockWebServer
    testImplementation("com.squareup.okhttp3:mockwebserver:5.3.2")

    // runTest
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")

    // Roboelctric
    testImplementation("org.robolectric:robolectric:4.16.1")

    // Mockk
    testImplementation("io.mockk:mockk:1.14.9")
    androidTestImplementation("io.mockk:mockk-android:1.14.9")
    testImplementation("androidx.paging:paging-common-ktx:3.4.0")
    androidTestImplementation("app.cash.turbine:turbine:1.2.1")

}