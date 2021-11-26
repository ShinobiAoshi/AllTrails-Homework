import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(Plugins.Ids.ANDROID_APPLICATION)
    kotlin(Plugins.Ids.KOTLIN_ANDROID)
    kotlin(Plugins.Ids.KOTLIN_KAPT)
    id(Plugins.Ids.HILT)
    id(Plugins.Ids.GOOGLE_MAPS_SECRETS_GRADLE)
}

val MAPS_API_KEY: String = gradleLocalProperties(rootDir).getProperty("MAPS_API_KEY")

android {
    compileSdk = App.COMPILE_SDK
    buildToolsVersion = App.BUILD_TOOLS

    defaultConfig {
        applicationId = App.ID
        minSdk = App.MIN_SDK
        targetSdk = App.TARGET_SDK
        versionCode = App.VERSION_CODE
        versionName = App.VERSION_NAME

        testInstrumentationRunner = App.TEST_RUNNER
    }

    compileOptions {
        sourceCompatibility = App.JAVA
        targetCompatibility = App.JAVA
    }

    kotlinOptions.jvmTarget = App.JVM_TARGET

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    kapt {
        correctErrorTypes = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        forEach { buildType ->
            buildType.buildConfigField(type = "String", name = "MAPS_API_KEY", value = MAPS_API_KEY)
        }
    }
}

dependencies {
    implementation(Libraries.Dependencies.AirBnB.EPOXY)
    implementation(Libraries.Dependencies.AirBnB.EPOXY_DATABINDING)
    implementation(Libraries.Dependencies.AirBnB.MAVERICKS)
    implementation(Libraries.Dependencies.AirBnB.MAVERICKS_NAVIGATION)
    implementation(Libraries.Dependencies.AndroidX.APP_COMPAT)
    implementation(Libraries.Dependencies.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Libraries.Dependencies.AndroidX.CORE)
    implementation(Libraries.Dependencies.AndroidX.LIFECYCLE_RUNTIME_KTX)
    implementation(Libraries.Dependencies.AndroidX.NAVIGATION_FRAGMENT)
    implementation(Libraries.Dependencies.AndroidX.NAVIGATION_UI)
    implementation(Libraries.Dependencies.COIL)
    implementation(Libraries.Dependencies.Google.HILT)
    implementation(Libraries.Dependencies.Google.LOCATION)
    implementation(Libraries.Dependencies.Google.MAPS)
    implementation(Libraries.Dependencies.Google.MAPS_KTX)
    implementation(Libraries.Dependencies.Google.MATERIAL)
    implementation(Libraries.Dependencies.Moshi.MOSHI)
    implementation(Libraries.Dependencies.Moshi.MOSHI_CONVERTER)
    implementation(Libraries.Dependencies.Moshi.MOSHI_KOTLIN)
    implementation(Libraries.Dependencies.OkHttp.LOGGING_INTERCEPTOR)
    implementation(Libraries.Dependencies.OkHttp.OK_HTTP)
    implementation(Libraries.Dependencies.RETROFIT)
    implementation(Libraries.Dependencies.TED_PERMISSIONS_COROUTINE)
    kapt(Libraries.Dependencies.AirBnB.EPOXY_PROCESSOR)
    kapt(Libraries.Dependencies.Google.HILT_COMPILER)
    kapt(Libraries.Dependencies.Moshi.MOSHI_CODEGEN)

    androidTestImplementation(Testing.Dependencies.Espresso.CORE)
    androidTestImplementation(Testing.Dependencies.JUNIT_EXTENSION)
    testImplementation(Testing.Dependencies.JUNIT)
}