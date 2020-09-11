plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")

  //deployment plugins
  id("com.jfrog.bintray") version "1.8.5"
  maven
}

android {
  compileSdkVersion(groovy.util.Eval.x(project, "x.androidConfig.compileSdkVersion").toString().toInt())

  sourceSets["main"].java.srcDir("src/main/kotlin")
  sourceSets["androidTest"].java.srcDir("src/androidTest/kotlin")

  lintOptions {
    textReport = true
    textOutput("stdout")
    ignore("InvalidPackage")
  }

  defaultConfig {
    versionName = "2.3.1-SNAPSHOT"

    minSdkVersion(groovy.util.Eval.x(project, "x.androidConfig.minSdkVersion").toString())
    targetSdkVersion(groovy.util.Eval.x(project, "x.androidConfig.targetSdkVersion").toString())

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    testOptions {
      unitTests.isIncludeAndroidResources = true
    }
  }
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation(project(":apollo-http-cache"))

  add("implementation", groovy.util.Eval.x(project, "x.dep.timber"))
  add("implementation", groovy.util.Eval.x(project, "x.dep.okHttp.okHttp"))
  add("implementation", groovy.util.Eval.x(project, "x.dep.kotlin.coroutinesAndroid"))

  //tests
  add("androidTestImplementation", groovy.util.Eval.x(project, "x.dep.junit"))
  add("androidTestImplementation", groovy.util.Eval.x(project, "x.dep.androidx.testJUnit"))
  add("androidTestImplementation", groovy.util.Eval.x(project, "x.dep.androidx.testRunner"))

  add("androidTestImplementation", groovy.util.Eval.x(project, "x.dep.koin.android"))
  add("androidTestImplementation", groovy.util.Eval.x(project, "x.dep.koin.test"))
  add("androidTestImplementation", groovy.util.Eval.x(project, "x.dep.okHttp.logging"))
  add("androidTestImplementation", groovy.util.Eval.x(project, "x.dep.retrofit.retrofit"))
  add("androidTestImplementation", groovy.util.Eval.x(project, "x.dep.retrofit.moshiConverter"))
  add("androidTestImplementation", groovy.util.Eval.x(project, "x.dep.moshi.kotlin"))
  add("kaptAndroidTest", groovy.util.Eval.x(project, "x.dep.moshi.kotlinCodegen"))
}

val name = "Sweet OkHttp Cache"
val groupId = "com.hagergroup"
val artifactId = "sweet-okhttp-cache"

tasks.named<Upload>("uploadArchives") {
  repositories.withGroovyBuilder {
   "mavenDeployer" {
      "snapshotRepository"("url" to findProperty("azureArtifactsUrl")) {
        "authentication"("userName" to findProperty("azureArtifactsUsername"), "password" to findProperty("azureArtifactsGradleAccessToken"))
      }

      "pom" {
        "project" {
          setProperty("name", name)
          setProperty("groupId", groupId)
          setProperty("artifactId", artifactId)
          setProperty("version", android.defaultConfig.versionName)
          setProperty("packaging", "aar")
        }
      }
    }
  }
}

bintray {
  user = findProperty("bintrayUsername").toString()
  key = findProperty("bintrayKey").toString()

  publish = true

  setPublications(name)

  pkg.apply {
    repo = "Maven"
    name = artifactId
    userOrg = "hagergroup"
    githubRepo = "hagergroup/sweet-okhttp-cache-android"
    vcsUrl = "https://github.com/hagergroup/sweet-okhttp-cache-android"
    description = "HTTP Cache for OkHttp"
    setLabels("kotlin", "OkHttp", "cache", "cache http")
    setLicenses("MIT")
    desc = description
    websiteUrl = "https://github.com/hagergroup/sweet-okhttp-cache-android"
    issueTrackerUrl = "https://github.com/hagergroup/sweet-okhttp-cache-android/issues"
    githubReleaseNotesFile = "README.md"

    version.apply {
      name = android.defaultConfig.versionName
      desc = "https://github.com/hagergroup/sweet-okhttp-cache-android"
      vcsTag = android.defaultConfig.versionName
    }
  }
}
