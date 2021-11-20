object Libraries {

    object Versions {

        const val KOTLIN = "1.5.31"
        const val MOSHI = "1.12.0"
        const val RETROFIT = "2.9.0"
        const val OK_HTTP = "4.9.2"

        object AirBnB {
            const val EPOXY = "4.6.3"
            const val MAVERICKS = "2.4.0"
        }

        object AndroidX {
            const val APP_COMPAT = "1.3.1"
            const val CONSTRAINT_LAYOUT = "2.1.1"
            const val CORE = "1.6.0"
            const val NAVIGATION = "2.3.5"
        }

        object Google {
            const val HILT = "2.38.1"
            const val MATERIAL = "1.4.0"
        }
    }

    object Dependencies {

        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"

        object AirBnB {
            const val EPOXY = "com.airbnb.android:epoxy:${Versions.AirBnB.EPOXY}"
            const val EPOXY_PROCESSOR = "com.airbnb.android:epoxy-processor:${Versions.AirBnB.EPOXY}"
            const val MAVERICKS = "com.airbnb.android:mavericks:${Versions.AirBnB.MAVERICKS}"
            const val MAVERICKS_NAVIGATION = "com.airbnb.android:mavericks-navigation:${Versions.AirBnB.MAVERICKS}"
        }

        object AndroidX {
            const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.AndroidX.APP_COMPAT}"
            const val CONSTRAINT_LAYOUT =
                "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.CONSTRAINT_LAYOUT}"
            const val CORE = "androidx.core:core-ktx:${Versions.AndroidX.CORE}"
            const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:${Versions.AndroidX.NAVIGATION}"
            const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Versions.AndroidX.NAVIGATION}"
        }

        object Google {
            const val HILT = "com.google.dagger:hilt-android:${Versions.Google.HILT}"
            const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.Google.HILT}"
            const val MATERIAL = "com.google.android.material:material:${Versions.Google.MATERIAL}"
        }

        object OkHttp {
            const val OK_HTTP = "com.squareup.okhttp3:okhttp:${Versions.OK_HTTP}"
            const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OK_HTTP}"
        }

        object Moshi {
            const val MOSHI = "com.squareup.moshi:moshi:${Versions.MOSHI}"
            const val MOSHI_KOTLIN = "com.squareup.moshi:moshi-kotlin:${Versions.MOSHI}"
            const val MOSHI_CODEGEN = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.MOSHI}"
            const val MOSHI_CONVERTER = "com.squareup.retrofit2:converter-moshi:${Versions.RETROFIT}"
        }
    }
}
