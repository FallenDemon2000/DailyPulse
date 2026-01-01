import io.gitlab.arturbosch.detekt.Detekt

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.sqlDelight).apply(false)
    alias(libs.plugins.detekt)
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    dependencies {
        val detektVersion = "1.23.8" //TODO move it to libs.versions.toml
        //For some reason the version catalog is recommend here, but it doesn't work.
        detekt("io.gitlab.arturbosch.detekt:detekt-formatting:$detektVersion")
        detekt("io.gitlab.arturbosch.detekt:detekt-cli:$detektVersion")
    }
}

subprojects {
    tasks.withType<Detekt> {
        config.setFrom(files("${project.rootDir}/config/detekt.yml"))
        autoCorrect = true
        buildUponDefaultConfig = true
    }
}
