plugins {
    id(Plugins.Ids.ANDROID_APPLICATION)
    kotlin(Plugins.Ids.KOTLIN_ANDROID)
    kotlin(Plugins.Ids.KOTLIN_KAPT)
    id(Plugins.Ids.HILT)
}

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
    }
}

dependencies {
    implementation(Libraries.Dependencies.Google.HILT)
    implementation(Libraries.Dependencies.RETROFIT)
    implementation(Libraries.Dependencies.Moshi.MOSHI)
    implementation(Libraries.Dependencies.Moshi.MOSHI_KOTLIN)
    implementation(Libraries.Dependencies.Moshi.MOSHI_CONVERTER)
    implementation(Libraries.Dependencies.OkHttp.OK_HTTP)
    implementation(Libraries.Dependencies.OkHttp.LOGGING_INTERCEPTOR)
    implementation(Libraries.Dependencies.AirBnB.EPOXY)
    implementation(Libraries.Dependencies.AirBnB.EPOXY_DATABINDING)
    implementation(Libraries.Dependencies.TED_PERMISSIONS_COROUTINE)
    implementation(Libraries.Dependencies.Google.LOCATION)
    implementation(Libraries.Dependencies.COIL)
    kapt(Libraries.Dependencies.AirBnB.EPOXY_PROCESSOR)
    kapt(Libraries.Dependencies.Google.HILT_COMPILER)
    kapt(Libraries.Dependencies.Moshi.MOSHI_CODEGEN)
    implementation(Libraries.Dependencies.AndroidX.APP_COMPAT)
    implementation(Libraries.Dependencies.AndroidX.CORE)
    implementation(Libraries.Dependencies.Google.MATERIAL)
    implementation(Libraries.Dependencies.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Libraries.Dependencies.AndroidX.NAVIGATION_FRAGMENT)
    implementation(Libraries.Dependencies.AndroidX.NAVIGATION_UI)
    implementation(Libraries.Dependencies.AirBnB.MAVERICKS)
    implementation(Libraries.Dependencies.AirBnB.MAVERICKS_NAVIGATION)
    testImplementation(Testing.Dependencies.JUNIT)
    androidTestImplementation(Testing.Dependencies.JUNIT_EXTENSION)
    androidTestImplementation(Testing.Dependencies.Espresso.CORE)
}