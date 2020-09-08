plugins {
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
}

android {
  compileSdkVersion(groovy.util.Eval.x(project, "x.androidConfig.compileSdkVersion").toString().toInt())

  sourceSets["main"].java.srcDir("src/main/kotlin")
  sourceSets["test"].java.srcDir("src/test/kotlin")

  testOptions {
    unitTests.isIncludeAndroidResources = true
  }

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

  add("implementation", groovy.util.Eval.x(project, "x.dep.timber"))
  add("implementation", groovy.util.Eval.x(project, "x.dep.okHttp.okHttp"))
  add("implementation", groovy.util.Eval.x(project, "x.dep.kotlin.coroutinesAndroid"))

  //tests
  add("testImplementation", groovy.util.Eval.x(project, "x.dep.junit"))
  add("testImplementation", groovy.util.Eval.x(project, "x.dep.androidx.testJUnit"))
  add("testImplementation", groovy.util.Eval.x(project, "x.dep.robolectric"))

  add("testImplementation", groovy.util.Eval.x(project, "x.dep.okHttp.logging"))
  add("testImplementation", groovy.util.Eval.x(project, "x.dep.retrofit.retrofit"))
  add("testImplementation", groovy.util.Eval.x(project, "x.dep.retrofit.moshiConverter"))
  add("testImplementation", groovy.util.Eval.x(project, "x.dep.moshi.kotlin"))
  add("kaptTest", groovy.util.Eval.x(project, "x.dep.moshi.kotlinCodegen"))
}
