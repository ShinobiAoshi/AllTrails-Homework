buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(Plugins.Dependencies.ANDROID_GRADLE)
        classpath(Plugins.Dependencies.KOTLIN)
        classpath(Plugins.Dependencies.HILT)
        classpath(Plugins.Dependencies.GOOGLE_MAPS_SECRETS_GRADLE)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}