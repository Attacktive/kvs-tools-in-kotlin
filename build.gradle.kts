import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.6.21"
}

group = "attacktive"
version = "0.1.0"

repositories {
	mavenCentral()
}

dependencies {
	implementation("net.sourceforge.argparse4j:argparse4j:0.9.0")

	testImplementation(kotlin("test"))
}

tasks.test {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}
