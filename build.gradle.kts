import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
	id("application")
	kotlin("jvm") version "1.4.30"
	id("com.github.ben-manes.versions") version "0.36.0"
	id("org.openjfx.javafxplugin") version "0.0.9"
	id("org.beryx.runtime") version "1.12.1"
	id("com.github.johnrengelman.shadow") version "6.1.0"
}

repositories {
	mavenCentral()
	mavenLocal()
	jcenter()
	maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation(group = "no.tornado", name = "tornadofx", version = "2.0.0-SNAPSHOT")

	//Jackson
	val jacksonVersion = "2.12.1"
	implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = jacksonVersion)
	implementation(group = "com.fasterxml.jackson.dataformat", name = "jackson-dataformat-yaml", version = jacksonVersion)
	implementation(group = "com.fasterxml.jackson.datatype", name = "jackson-datatype-jdk8", version = jacksonVersion)

	//Log4j
	val logVersion = "2.14.0"
	implementation(group = "org.apache.logging.log4j", name = "log4j-api", version = logVersion)
	runtimeOnly(group = "org.apache.logging.log4j", name = "log4j-core", version = logVersion)
}

javafx {
	version = "15.0.1"
	modules = listOf("javafx.controls")
}

application {
	mainClass.set("github.macro.LauncherKt")
	mainClassName = mainClass.get()
	applicationName = "Path-of-Taurewa"
}

tasks.jar {
	manifest {
		attributes(
			"Main-Class" to application.mainClass,
			"Multi-Release" to true
		)
	}
}

tasks.compileKotlin {
	sourceCompatibility = "11"
	targetCompatibility = "11"

	kotlinOptions.jvmTarget = "11"
	kotlinOptions.apiVersion = "1.4"
	kotlinOptions.languageVersion = "1.4"
}

runtime {
	addOptions("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
}

tasks.clean {
	file("release").deleteRecursively()
}

tasks.register<Copy>("copyApplication") {
	dependsOn("runtime")

	from("$buildDir/image")
	include("**/*")
	into("$buildDir/release")
}
tasks.register<Copy>("copyResources") {
	from("resources")
	exclude("docs")
	include("**/*")
	into("$buildDir/release/bin/resources")
}

tasks.register<Zip>("zipApplication") {
	dependsOn("copyApplication", "copyResources")

	from("$buildDir/release")
	include("**/*")
	archiveFileName.set("Path-of-Taurewa.zip")
	destinationDirectory.set(file("release"))
}

tasks.register<Zip>("zipResources") {
	dependsOn("copyResources")

	from("$buildDir/release/bin/resources")
	include("**/*")
	archiveFileName.set("Path-of-Taurewa_resources.zip")
	destinationDirectory.set(file("release"))
}

task("release") {
	dependsOn("zipResources", "zipApplication")
	tasks["zipApplication"].shouldRunAfter("zipResources")
}

fun isNonStable(version: String): Boolean {
	val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
	val regex = "^[0-9,.v-]+(-r)?$".toRegex()
	val isStable = stableKeyword || regex.matches(version)
	return isStable.not()
}

tasks.named("dependencyUpdates", DependencyUpdatesTask::class.java).configure {
	// Example 1: reject all non stable versions
	rejectVersionIf {
		isNonStable(candidate.version)
	}

	// Example 2: disallow release candidates as upgradable versions from stable versions
	rejectVersionIf {
		isNonStable(candidate.version) && !isNonStable(currentVersion)
	}

	// Example 3: using the full syntax
	resolutionStrategy {
		componentSelection {
			all {
				if (isNonStable(candidate.version) && !isNonStable(currentVersion)) {
					reject("Release candidate")
				}
			}
		}
	}
}