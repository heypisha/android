import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { localProperties.load(it) }
}

android {
  namespace = "seneca.pmugisha3.cosmotracker"
  compileSdk = 36

  defaultConfig {
    applicationId = "seneca.pmugisha3.cosmotracker"
    minSdk = 25
    targetSdk = 36
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    
    // Add the NASA API Key from local.properties to BuildConfig
    buildConfigField("String", "NASA_API_KEY", "\"${localProperties.getProperty("NASA_API_KEY") ?: "DEMO_KEY"}\"")
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
    buildConfig = true // Enable BuildConfig generation
  }

  kotlin {
    jvmToolchain(17)
    compilerOptions {
      jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
  }
}

dependencies {
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.lifecycle.viewmodel.compose)

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.runtime)
  implementation(libs.androidx.material3)
  implementation(libs.compose.material.icons)

  // Retrofit
  implementation(libs.retrofit.core)
  implementation(libs.retrofit.converter.moshi)
  implementation(libs.okhttp.core)
  implementation(libs.okhttp.logging)

  // Moshi
  implementation(libs.moshi.core)
  implementation(libs.moshi.kotlin)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.tooling)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
}