pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral() // Tambahkan Maven Central jika diperlukan untuk plugin lain
        maven { setUrl("https://jitpack.io") } // Tambahkan JitPack jika ada dependensi dari sini
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") } // Tambahkan JitPack untuk dependensi
    }
}

rootProject.name = "bottom_navigation"
include(":app")
