// Standard Libs - Android, Material, Browser
object StandardLibs {
    const val APP_COMPAT = "androidx.appcompat:appcompat:" + Version.V_APPCOMPAT
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:" + Version.V_CONSTRAINT_LAYOUT
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:" + Version.V_ANDROID_X
    const val SWIPEREFRESH = "androidx.swiperefreshlayout:swiperefreshlayout:" + Version.V_ANDROID_X
    const val CARDVIEW = "androidx.cardview:cardview:" + Version.V_ANDROID_X
    const val MATERIAL = "com.google.android.material:material:" + Version.V_MATERIAL
    const val DATASTORE_PREFERENCES = "androidx.datastore:datastore-preferences:" + Version.V_DATASTORE_PREFERENCES
    const val BROWSER = "androidx.browser:browser:1.3.0"
}


// Lifecycle - LiveData, ViewModels, etc
object LifeCycle {
    const val LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.V_LIFECYCLE_VIEWMODEL}"
    const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.V_LIFECYCLE_VIEWMODEL}"
    const val LIFECYCLE_VIEWMODEL = "android.arch.lifecycle:viewmodel:${Version.V_LIFECYCLE_VIEWMODEL}"
    const val LIFECYCLE_EXTENSIONS = "android.arch.lifecycle:extensions:${Version.V_LIFECYCLE_VIEWMODEL}"
}


// Networking - Retrofit, Gson
object Networking {
    const val OKHTTP_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:" + Version.V_OKHTTP
    const val RETROFIT = "com.squareup.retrofit2:retrofit:" + Version.V_RETROFIT
    const val GSON = "com.google.code.gson:gson:" + Version.V_GSON
    const val GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:" + Version.V_RETROFIT
}

// ImageLoading - Glide
object ImageLoading {
    const val GLIDE = "com.github.bumptech.glide:glide:" + Version.V_GLIDE
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:" + Version.V_GLIDE_COMPILER
}

// Testing dependencies - JUnit, Espresso
object Testing {
    const val JUNIT = "junit:junit:" + Version.V_JUNIT
    const val TEST_RUNNER = "androidx.test:runner:" + Version.V_TEST_RUNNER
    const val TEST_ESPRESSO = "androidx.test.espresso:espresso-core:" + Version.V_TEST_ESPRESSO
}