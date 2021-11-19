plugins {
    id(Plugins.Ids.ANDROID_APPLICATION)
    kotlin(Plugins.Ids.KOTLIN_ANDROID)
    kotlin(Plugins.Ids.KOTLIN_KAPT)
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
    implementation(Libraries.Dependencies.AndroidX.APP_COMPAT)
    implementation(Libraries.Dependencies.AndroidX.CORE)
    implementation(Libraries.Dependencies.Google.MATERIAL)
    implementation(Libraries.Dependencies.AndroidX.CONSTRAINT_LAYOUT)
    implementation(Libraries.Dependencies.AndroidX.NAVIGATION_FRAGMENT)
    implementation(Libraries.Dependencies.AndroidX.NAVIGATION_UI)
    testImplementation(Testing.Dependencies.JUNIT)
    androidTestImplementation(Testing.Dependencies.JUNIT_EXTENSION)
    androidTestImplementation(Testing.Dependencies.Espresso.CORE)
}