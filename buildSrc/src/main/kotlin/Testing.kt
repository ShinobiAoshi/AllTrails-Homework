object Testing {

    object Versions {
        const val ESPRESSO = "3.4.0"
        const val JUNIT = "4.13.2"
        const val JUNIT_EXTENSION = "1.1.3"
    }

    object Dependencies {
        const val JUNIT = "junit:junit:${Versions.JUNIT}"
        const val JUNIT_EXTENSION = "androidx.test.ext:junit:${Versions.JUNIT_EXTENSION}"

        object Espresso {
            const val CORE = "androidx.test.espresso:espresso-core:${Versions.ESPRESSO}"
        }
    }
}
