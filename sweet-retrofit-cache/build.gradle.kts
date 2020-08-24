plugins {
  id("com.android.library")
  kotlin("android")
}

android {
  compileSdkVersion(groovy.util.Eval.x(project, "x.androidConfig.compileSdkVersion").toString().toInt())

  lintOptions {
    textReport = true
    textOutput("stdout")
    ignore("InvalidPackage")
  }

  defaultConfig {
    minSdkVersion(groovy.util.Eval.x(project, "x.androidConfig.minSdkVersion").toString())
    targetSdkVersion(groovy.util.Eval.x(project, "x.androidConfig.targetSdkVersion").toString())
    testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
  }
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation(project(":apollo-http-cache"))

  add("implementation",groovy.util.Eval.x(project, "x.dep.timber"))
  add("implementation", groovy.util.Eval.x(project, "x.dep.retrofit"))
  add("implementation", groovy.util.Eval.x(project, "x.dep.kotlin.coroutinesAndroid"))
}
