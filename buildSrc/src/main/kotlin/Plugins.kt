object Plugins {
    object Versions {
        const val ANDROID_GRADLE = "7.0.3"
        const val KOTLIN = Libraries.Versions.KOTLIN
    }

    object Dependencies {
        const val ANDROID_GRADLE = "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE}"
        const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    }

    object Ids {
        const val ANDROID_APPLICATION = "com.android.application"
        const val KOTLIN_ANDROID = "android"
        const val KOTLIN_KAPT = "kapt"
    }
}
