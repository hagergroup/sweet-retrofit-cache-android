plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")

  //deployment plugins
  maven
  `maven-publish`
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
    versionName = "2.4.1"

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

val libGroupId = "com.hagergroup"
val libArtifactId = "sweet-okhttp-cache"

val libSourcesJar by tasks.registering(Jar::class) {
  archiveClassifier.set("sources")
  from(android.sourceSets.getByName("main").java.srcDirs)
}

publishing {
  publications {
    create<MavenPublication>("sweetcache") {
      from(components.findByName("release"))
      artifact(libSourcesJar.get())
    }
  }
}

artifacts {
  add("archives", libSourcesJar)
}

tasks.named<Upload>("uploadArchives") {
  repositories.withGroovyBuilder {
   "mavenDeployer" {
      "snapshotRepository"("url" to findProperty("azureArtifactsUrl")) {
        "authentication"("userName" to findProperty("azureArtifactsUsername"), "password" to findProperty("azureArtifactsGradleAccessToken"))
      }

     "repository"("url" to "https://api.bintray.com/maven/hagergroup/Maven/sweet-okhttp-cache/;publish=1") {
       "authentication"("userName" to findProperty("bintrayUsername"), "password" to findProperty("bintrayKey"))
     }

      "pom" {
        "project" {
          setProperty("name", libArtifactId)
          setProperty("groupId", libGroupId)
          setProperty("artifactId", libArtifactId)
          setProperty("version", android.defaultConfig.versionName)
          setProperty("packaging", "aar")
        }
      }
    }
  }
}
