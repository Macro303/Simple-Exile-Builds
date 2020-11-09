import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
	id("application")
	kotlin("jvm") version "1.4.10"
	id("com.github.ben-manes.versions") version "0.35.0"
	id("org.openjfx.javafxplugin") version "0.0.9"
	id("org.beryx.runtime") version "1.11.4"
	id("com.github.johnrengelman.shadow") version "6.1.0"
}

repositories {
	mavenCentral()
	mavenLocal()
	jcenter()
	maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
	implementation(Dependencies.kotlin)
	implementation(Dependencies.tornado)

	//Jackson
	implementation(Dependencies.Jackson.core)
	implementation(Dependencies.Jackson.yaml)
	implementation(Dependencies.Jackson.jdk8)

	//Log4j
	implementation(Dependencies.Log4j.api)
	runtimeOnly(Dependencies.Log4j.core)
}

javafx {
	version = "15.0.1"
	modules = listOf("javafx.controls")
}

application{
	mainClassName = "github.macro.LauncherKt"
	applicationName = "Simple-Exile"
}

tasks.jar{
	manifest {
		attributes(
			"Main-Class" to application.mainClassName,
			"Multi-Release" to true
		)
	}
}

tasks.compileKotlin{
	sourceCompatibility = "11"
	targetCompatibility = "11"

	kotlinOptions.jvmTarget = "11"
	kotlinOptions.apiVersion = "1.4"
	kotlinOptions.languageVersion = "1.4"
}

runtime{
	addOptions("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages")
}

tasks.clean{
	file("release").deleteRecursively()
}

tasks.register<Copy>("copyApplication"){
	dependsOn("runtime")

	from("$buildDir/image")
	include("**/*")
	into("$buildDir/release")
}
tasks.register<Copy>("copyResources"){
	from("resources")
	exclude("docs")
	include("**/*")
	into("$buildDir/release/bin/resources")
}

tasks.register<Zip>("zipApplication"){
	dependsOn("copyApplication", "copyResources")

	from("$buildDir/release")
	include("**/*")
	archiveFileName.set("Simple-Exile.zip")
	destinationDirectory.set(file("release"))
}

tasks.register<Zip>("zipResources"){
	dependsOn("copyResources")

	from("$buildDir/release/bin/resources")
	include("**/*")
	archiveFileName.set("Simple-Exile_resources.zip")
	destinationDirectory.set(file("release"))
}

task("release"){
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