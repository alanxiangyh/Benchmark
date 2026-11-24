plugins {
    java
    id("me.champeau.jmh") version "0.7.4"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openjdk.jmh:jmh-core:1.37")
    annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
}

sourceSets {
    named("jmh") {
        java.setSrcDirs(listOf("src/main/java"))
        resources.setSrcDirs(listOf("src/main/resources"))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.addAll(
        listOf("--enable-preview", "--release", "25")
    )
}

tasks.withType<Test>().configureEach {
    jvmArgs("--enable-preview")
}

tasks.withType<JavaExec>().configureEach {
    jvmArgs("--enable-preview")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

jmh {
    jmhVersion.set("1.37")
    jvmArgsAppend.add("--enable-preview")
    duplicateClassesStrategy.set(DuplicatesStrategy.EXCLUDE)
}

tasks.named<Jar>("jmhJar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.withType<me.champeau.jmh.JMHTask>().configureEach {
    outputs.upToDateWhen { false }
}
