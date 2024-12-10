pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CIS357FinalProjectQbertGame"
include(":app")

// Include the Unity library
//include(":unityLibrary")
//project(":unityLibrary").projectDir = File(rootDir, "UnityProject/unityLibrary")
//include(":unityLibrary")
//include(":unityLibrary:mobilenotifications.androidlib")
