plugins{
    `kotlin-dsl`
    groovy
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())
    /* Depend on the kotlin plugin, since we want to access it in our plugin */
//    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
//    implementation ("com.android.tools.build:gradle:4.0.0")
}

repositories {
    mavenCentral()
    google()
    jcenter()
    maven {
        setUrl("https://dl.bintray.com/kotlin/kotlin-eap" )
    }
}
