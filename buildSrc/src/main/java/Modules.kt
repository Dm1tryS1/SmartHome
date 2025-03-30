object Modules {

    const val app = ":app"

    object Common {
        const val core = ":common:core"
        const val data = ":common:data"
        const val network = ":common:network"
        const val sharedPreferences = ":common:shared_preferences"
        const val storage = ":common:storage"
    }

    class FeatureTemplate(name: String) {
        val api = ":features:$name:${name}_api"
        val impl = ":features:$name:${name}_impl"
    }

    object Feature {
        val home = FeatureTemplate("home")
        val information = FeatureTemplate("information")
        val system = FeatureTemplate("system")
        val charts = FeatureTemplate("charts")
        val settings = FeatureTemplate("settings")
        val connection = FeatureTemplate("connection")
        val auth = FeatureTemplate("auth")
    }

}