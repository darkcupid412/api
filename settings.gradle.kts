@file:Suppress("UnstableApiUsage")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // First: upstream also publishes a cumulus 2.0.0-SNAPSHOT, and it does NOT
        // carry the datadrivenui package this fork's Connection compiles against.
        // Without mavenLocal winning, the build resolves upstream's and fails on a
        // missing package rather than on a missing version.
        mavenLocal()
        maven("https://repo.opencollab.dev/main")
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    includeBuild("build-logic")
}

rootProject.name = "api-parent"

include(":base")