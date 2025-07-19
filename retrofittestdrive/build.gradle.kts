val retrofit2Version = "2.9.0"
val resilience4jVersion = "1.7.1"
val slf4jVersion = "1.7.32"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.1.0")
    implementation(libs.commons.math3)
    implementation(libs.bundles.kotest)
    
    implementation("com.squareup.retrofit2:retrofit:$retrofit2Version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit2Version")

//    jackson support requires a bit of extra work
//    implementation("com.squareup.retrofit2:converter-jackson:$retrofit2Version")

    implementation("io.github.resilience4j:resilience4j-retry:$resilience4jVersion")

    // Logging - bare basics INFO to stderr
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")

    testImplementation("org.mock-server:mockserver-netty:5.11.2")
}

tasks.withType<Test> {
    systemProperty("org.slf4j.simpleLogger.log.org.mockserver", "warn")
    systemProperty("org.slf4j.simpleLogger.showThreadName", "false")
    systemProperty("org.slf4j.simpleLogger.levelInBrackets", "true")
}