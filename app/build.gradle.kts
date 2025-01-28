plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.ikaslea.komertzialakapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ikaslea.komertzialakapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.play.services.maps)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    implementation("com.thoughtworks.xstream:xstream:1.4.21")
    implementation("com.j256.ormlite:ormlite-android:6.1")
    implementation("androidx.sqlite:sqlite:2.2.0")
    //implementation("com.prolificinteractive:material-calendarview:3.0.0")

}