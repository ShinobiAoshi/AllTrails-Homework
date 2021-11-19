object Libraries {

    object Versions {

        const val KOTLIN = "1.6.0"

        object AirBnB {
            const val MAVERICKS = "2.4.0"
        }

        object AndroidX {
            const val APP_COMPAT = "1.3.1"
            const val CONSTRAINT_LAYOUT = "2.1.1"
            const val CORE = "1.6.0"
            const val NAVIGATION = "2.3.5"
        }

        object Google {
            const val MATERIAL = "1.4.0"
        }
    }

    object Dependencies {

        object AirBnB {
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
            const val MATERIAL = "com.google.android.material:material:${Versions.Google.MATERIAL}"
        }
    }
}
