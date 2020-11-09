object Dependencies {
	const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
	const val tornado = "no.tornado:tornadofx:2.0.0-SNAPSHOT"
	object Jackson {
		private const val version = "2.11.3"
		private const val group = "com.fasterxml.jackson"
		const val core = "$group.core:jackson-databind:$version"
		const val yaml = "$group.dataformat:jackson-dataformat-yaml:$version"
		const val jdk8 = "$group.datatype:jackson-datatype-jdk8:$version"
	}
	object Log4j {
		private const val version = "2.13.3"
		private const val group = "org.apache.logging.log4j"
		const val api = "$group:log4j-api:$version"
		const val core = "$group:log4j-core:$version"
	}
}