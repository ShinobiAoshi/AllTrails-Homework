object Plugins {
    object Versions {
        const val ANDROID_GRADLE = "7.0.3"
        const val GOOGLE_MAPS_SECRETS_GRADLE = "2.0.0"
        const val KOTLIN = Libraries.Versions.KOTLIN
    }

    object Dependencies {
        const val ANDROID_GRADLE = "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE}"
        const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
        const val HILT = "com.google.dagger:hilt-android-gradle-plugin:${Libraries.Versions.Google.HILT}"
        const val GOOGLE_MAPS_SECRETS_GRADLE = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:${Versions.GOOGLE_MAPS_SECRETS_GRADLE}"
    }

    object Ids {
        const val ANDROID_APPLICATION = "com.android.application"
        const val KOTLIN_ANDROID = "android"
        const val KOTLIN_KAPT = "kapt"
        const val HILT = "dagger.hilt.android.plugin"
        const val GOOGLE_MAPS_SECRETS_GRADLE = "com.google.android.libraries.mapsplatform.secrets-gradle-plugin"
    }
}
