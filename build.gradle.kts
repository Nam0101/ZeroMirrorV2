// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application").version("8.5.0").apply(false)
    id("com.android.library").version("8.5.0").apply(false)
    id("org.jetbrains.kotlin.android").version("1.9.20").apply(false)
    id("org.jetbrains.kotlin.jvm").version("1.9.20").apply(false)
}

tasks.register("clean") {
    doFirst {
        delete(rootProject.layout.buildDirectory)
    }
}

// TODO 何故か必要。Android Gradle Plugin の更新で要らなくなるはず
// https://issuetracker.google.com/issues/278118298
subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
}