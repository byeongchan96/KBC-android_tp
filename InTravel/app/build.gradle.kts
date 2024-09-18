import java.util.Properties

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.jetbrains.kotlin.android)
  alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

// local.properties 파일을 사용하여 Properties 객체 생성
val properties = Properties().apply {
  load(rootProject.file("local.properties").inputStream())
}

android {
  namespace = "com.example.intravel"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.intravel"
    minSdk = 21 // minSdk 버전을 낮추어 더 많은 기기 지원
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

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }

  buildFeatures {
    viewBinding = true
    dataBinding = true
  }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
  implementation(libs.androidx.runtime.saved.instance.state)
  implementation(libs.filament.android)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)

  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")
  implementation("com.google.android.material:material:1.9.0")
  implementation("androidx.viewpager2:viewpager2:1.0.0")
  implementation("androidx.fragment:fragment:1.6.1") // 최신 버전 사용

  // Google Maps 관련 라이브러리
  implementation("com.google.android.gms:play-services-maps:17.0.0")
  implementation("com.google.android.gms:play-services-location:19.0.1")

  // Places API
  implementation("com.google.android.libraries.places:places:2.5.0")

  // OkHttp 및 Interceptor
  implementation("com.squareup.okhttp3:okhttp:4.10.0")
  implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

  // Glide 라이브러리
  implementation("com.github.bumptech.glide:glide:4.15.1")
  implementation("com.google.android.datatransport:transport-runtime:3.0.0")
  // Google DataTransport 라이브러리
  implementation("com.google.android.datatransport:transport-api:3.0.0")



  // Material CalendarView
  implementation("com.github.prolificinteractive:material-calendarview:2.0.1")
}

