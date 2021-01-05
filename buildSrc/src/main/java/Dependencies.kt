object Versions {
    const val  lifecycle_version = "2.2.0"
    const val  dagger_version = "2.29.1"
    const val  anko_version = "0.10.8"
    const val  fragment_version = "1.2.5"
    const val  recyclerview_version = "1.1.0"
    const val  recyclerview_selection_version = "1.0.0"
    const val  viewpager2_version = "1.0.0"
    const val  coordinatorlayout_version = "1.1.0"
    const val  constraintlayout_version = "1.1.3"
    const val  appcompat_version = "1.2.0"
    const val  core_version = "1.3.1"
    const val  googleMaterial_version = "1.2.1"
    const val  smartRefresh_version = "2.0.2"
    const val  retrofit2_version = "2.9.0"
    const val  datastore_version = "1.0.0-alpha04"
    const val  proto_version= "3.14.0"
}

object AndroidX {
    const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle_version}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.core_version}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat_version}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout_version}"
    const val fragment =  "androidx.fragment:fragment-ktx:${Versions.fragment_version}"
    const val recyclerview =  "androidx.recyclerview:recyclerview:${Versions.recyclerview_version}"
    const val recyclerviewSelection =  "androidx.recyclerview:recyclerview-selection:${Versions.recyclerview_selection_version}"
    const val viewpager2 =  "androidx.viewpager2:viewpager2:${Versions.viewpager2_version}"
    const val coordinatorlayout =  "androidx.coordinatorlayout:coordinatorlayout:${Versions.coordinatorlayout_version}"
    const val googleMaterial =  "com.google.android.material:material:${Versions.googleMaterial_version}"
    // Preferences DataStore
    const val datastorePreferences =  "androidx.datastore:datastore-preferences:${Versions.datastore_version}"
    // Proto DataStore
    const val datastoreProto =  "androidx.datastore:datastore-core:${Versions.datastore_version}"
    const val protobuf =  "com.google.protobuf:protobuf-java:${Versions.proto_version}"
    const val protoc =  "com.google.protobuf:protoc:${Versions.proto_version}"

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
    const val anko =  "org.jetbrains.anko:anko:${Versions.anko_version}"
    const val smartRefreshKernel =  "com.scwang.smart:refresh-layout-kernel:${Versions.smartRefresh_version}"
    const val retrofit2 =  "com.squareup.retrofit2:retrofit:${Versions.retrofit2_version}"
    const val retrofit2ConverterGson =  "com.squareup.retrofit2:converter-gson:${Versions.retrofit2_version}"
}
