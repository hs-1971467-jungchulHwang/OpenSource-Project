/*
  Copyright 2019 Kakao Corp.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven {
            url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/")
        }
    }
}

val libraries = rootProject.subprojects.filter { project ->
    project.name !in Dokka.samples
}

configure(libraries) {
    apply(plugin = "org.jetbrains.dokka")

    tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
        moduleName.set(Publish.projectName)
        dokkaSourceSets {
            configureEach {
                includes.from("../packages.md")
                skipEmptyPackages.set(true)
                listOf(
                    "https://developer.android.com/reference/kotlin/",
                    "http://reactivex.io/RxJava/javadoc/",
                    "https://square.github.io/retrofit/2.x/retrofit/",
                    "http://www.reactive-streams.org/reactive-streams-1.0.3-javadoc/"
//                "https://square.github.io/okhttp/4.x/okhttp/okhttp3/"
                ).forEach {
                    externalDocumentationLink {
                        url.set(java.net.URL(it))
                        packageListUrl.set(java.net.URL("${it}package-list"))
                    }
                }
                listOf("androidx", "dagger", "io.reactivex", "com.google").forEach {
                    perPackageOption {
                        matchingRegex.set("${it}($|\\.).*")
                        suppress.set(true)
                    }
                }
            }
        }
    }
}

plugins {
    id("org.jetbrains.dokka") version "1.6.10"
    id("com.github.ben-manes.versions") version "0.27.0"
    `maven-publish`
}

val clean by tasks.registering(Delete::class) {
    delete(rootProject.buildDir)
}

val publishSdk by tasks.registering(Task::class) {
    libraries.filter { it.name !in Publish.excludedLibraries }.forEach { project ->
        dependsOn(project.tasks["publish"])
    }
    dependsOn(tasks["publishProjectPublicationToMavenRepository"])
}

val fullSourcePath = "${rootProject.buildDir}/full_source"

// 샘플앱에서 제외되는 모듈들 (rx 포함)
val excludedModules = listOf(
    "all",
    "common",
    "network",
    "template",
    "navi",
    "auth",
    "talk",
    "story",
    "share",
    "user",
    "friend",
)

val copyProject by tasks.registering(Copy::class) {
    from(rootProject.rootDir)
    into(fullSourcePath)
    exclude(
        relativePath(rootProject.buildDir),
        "**/.gradle",
        ".idea",
        ".project.info",
        "**/jacoco.exec",
        "**/*.iml",
        "buildSrc/build",
        "README.md",
        "rxpartnersdkv2Ko.md",
        "*.keystore",
        "Dockerfile",
        "Jenkinsfile",
        "deploy.sh"
    )
    exclude(
        rootProject.subprojects.map { relativePath(it.buildDir) }
    )

    // 샘플 프로젝트에서 sdk 모듈 제외
    rootProject.subprojects.filter {
        excludedModules.forEach { _ ->
            // 샘플앱 관련 모듈은 포함
            if (it.path.contains("sample")) {
                return@filter false
            }
        }
        true
    }.forEach {
        exclude(relativePath(it.projectDir))
    }
}

fun File.addComment(moduleName: String): Unit = writeText(
    readText().replace("api(project(\":$moduleName\"))", "// api(project(\":$moduleName\"))")
)

fun File.replaceDependencyToRemote(moduleName: String): Unit = writeText(
    readText().replace(
        "project(\":$moduleName\")",
        "\"com.kakao.sdk:v2-$moduleName:${SdkVersions.version}\""
    )
)

fun addCommentToFriendModule(directory: File) {
    directory.listFiles()?.forEach { file ->
        val postFix = if (directory.name.contains("rx")) "-rx" else ""
        if (file.name == "build.gradle.kts") {
            file.addComment("friend$postFix")
            file.addComment("partner-friend$postFix")
        }
    }
}

fun replaceSampleDependency(directory: File) {
    directory.listFiles()?.forEach { file ->
        val postFix =
            if (directory.name.contains("rx") || directory.name.contains("java")) "-rx" else ""
        if (file.name == "build.gradle" || file.name == "build.gradle.kts") {
            file.replaceDependencyToRemote("all-rx")
            file.replaceDependencyToRemote("partner-all$postFix")
        }
    }
}

// 샘플앱이 SDK 모듈 없이도 빌드되도록 파일 수정
val editFiles by tasks.registering(Task::class) {
    doLast {
        File(fullSourcePath).listFiles()?.forEach {
            when {
                it.name == "settings.gradle.kts" -> {
                    // SDK 모듈이 제거되도록 주석 처리
                    excludedModules.forEach { module ->
                        it.writeText(it.readText().replace("\"$module", "// \"$module"))
                        it.writeText(
                            it.readText().replace("\"partner-$module", "// \"partner-$module")
                        )
                    }
                }
                it.path.contains("sample") -> {
                    // 통합 모듈을 외부 저장소에서 불러오도록 수정
                    replaceSampleDependency(it)
                }
                it.path.contains("open") -> {
                    it.listFiles()?.forEach { directory ->
                        if (directory.name == "sample-common") {
                            // 통합 모듈을 외부 저장소에서 불러오도록 수정
                            replaceSampleDependency(directory)
                        }
                    }
                }

            }
        }
    }
}

val zipProject by tasks.registering(Zip::class) {
    dependsOn(copyProject)
    dependsOn(editFiles)
    from(fullSourcePath)
    destinationDirectory.set(rootProject.buildDir)
    archiveBaseName.set(Publish.projectName)
    archiveVersion.set(SdkVersions.version)
}

val dokkaHtmlCollector by tasks.getting(org.jetbrains.dokka.gradle.DokkaCollectorTask::class) {
    outputDirectory.set(rootProject.buildDir.resolve("dokka"))
}

val zipDokka by tasks.registering(Zip::class) {
    dependsOn(dokkaHtmlCollector)
    from("${rootProject.buildDir}/dokka")
    destinationDirectory.set(rootProject.buildDir)
    archiveBaseName.set(Publish.dokkaArtifactId)
    archiveVersion.set(SdkVersions.version)
    delete("${rootProject.buildDir}/dokka")
}

// kts 에서 gradle.properties 에서 property 를 읽어보는 방식
val nexusSnapshotRepositoryUrl = project.properties["NEXUS_SNAPSHOT_REPOSITORY_URL"] as? String
val nexusReleaseRepositoryUrl = project.properties["NEXUS_RELEASE_REPOSITORY_URL"] as? String
val nexusUsername = project.properties["NEXUS_USERNAME"] as? String
val nexusPassword = project.properties["NEXUS_PASSWORD"] as? String

publishing {
    repositories {
        maven {
            url = if (SdkVersions.version.endsWith("-SNAPSHOT")) {
                nexusSnapshotRepositoryUrl?.let { uri(it) } ?: mavenLocal().url
            } else {
                nexusReleaseRepositoryUrl?.let { uri(it) } ?: mavenLocal().url
            }
            credentials {
                username = nexusUsername ?: ""
                password = nexusPassword ?: ""
            }
        }
    }
    publications {
        register("dokka", MavenPublication::class) {
            groupId = Publish.groupId
            artifactId = Publish.dokkaArtifactId
            version = SdkVersions.version
            artifact(zipDokka.get())
        }
        register("project", MavenPublication::class) {
            groupId = Publish.groupId
            artifactId = Publish.projectName
            version = SdkVersions.version
            artifact(zipProject.get())
        }
    }
}