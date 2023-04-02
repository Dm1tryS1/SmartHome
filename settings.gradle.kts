dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "SmartHome"
include(":app")
include(":common:core")
include(":common:data")
include(":common:network")
include(":common:shared_preferences")
include(":common:permissions")
include(":common:storage")
include(":features:home:home_impl")
include(":features:home:home_api")
include(":features:information:information_api")
include(":features:information:information_impl")
include(":features:system:system_api")
include(":features:system:system_impl")
include(":features:charts:charts_api")
include(":features:charts:charts_impl")
include(":features:connection:connection_api")
include(":features:connection:connection_impl")
include(":features:settings:settings_api")
include(":features:settings:settings_impl")
