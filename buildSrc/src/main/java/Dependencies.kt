object Versions {
    const val  lifecycle_version = "2.2.0"
    const val  dagger_version = "2.29.1"
}

object AndroidX {
    val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
    val coreKtx = "androidx.core:core-ktx:1.3.1"
    val appcompat = "androidx.appcompat:appcompat:1.2.0"
    val constraintlayout = "androidx.constraintlayout:constraintlayout:1.1.3"

}

object AndroidVersion{
    const val compileSdkVersion = 30
    const val buildToolsVersion  = "30.0.2"
    const val applicationId  = "com.zcp.wan_android"
    const val minSdkVersion = 19
    const val targetSdkVersion =  30
    const val versionCode = 1
    const val versionName = "1.0"
}

object AppDependencies{
    const val dagger = "com.google.dagger:dagger:${Versions.dagger_version}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger_version}"
}
