public class Deps {

    // VERSIONS
    private static final String V_APPCOMPAT = "1.0.2";
    private static final String V_ANDROID_X = "1.0.0";
    private static final String V_CONSTRAINT_LAYOUT = "1.1.3";

    private static final String V_OKHTTP = "3.11.0";
    private static final String V_RETROFIT = "2.4.0";
    private static final String V_GSON = "2.8.5";

    private static final String V_GLIDE = "4.7.1";
    private static final String V_GLIDE_COMPILER = "4.6.1";

    private static final String V_DATABINDING_COMPILER = "3.4.0";
    private static final String V_LIFECYCLE_VIEWMODEL = "1.1.0";
    private static final String V_LIFECYCLE_EXTENSIONS = "1.1.0";
    private static final String V_CORE_KTX = "1.0.1";
    private static final String V_KOTLIN = "1.3.31";
    private static final String V_JUNIT = "4.12";
    private static final String V_TEST_RUNNER = "1.1.1";
    private static final String V_TEST_ESPRESSO = "3.1.1";


    // LIBRARIES
    public static final String APP_COMPAT = "androidx.appcompat:appcompat:"+V_APPCOMPAT;
    public static final String CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:"+V_CONSTRAINT_LAYOUT;
    public static final String RECYCLER_VIEW = "androidx.recyclerview:recyclerview:"+V_ANDROID_X;
    public static final String SWIPEREFRESH = "androidx.swiperefreshlayout:swiperefreshlayout:"+V_ANDROID_X;
    public static final String CARDVIEW = "androidx.cardview:cardview:"+V_ANDROID_X;

    // Retrofit and OkHttp networking libraries
    public static final String OKHTTP_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:"+V_OKHTTP;
    public static final String RETROFIT = "com.squareup.retrofit2:retrofit:"+V_RETROFIT;
    public static final String GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:"+V_RETROFIT;
    public static final String GSON = "com.google.code.gson:gson:"+V_GSON;

    // Glide - imageLoading
    public static final String GLIDE = "com.github.bumptech.glide:glide:"+V_GLIDE;
    // ANNOTATION PROCESSPOR
    public static final String GLIDE_COMPILER = "com.github.bumptech.glide:compiler:"+V_GLIDE_COMPILER;

    // Data binding component
    // KAPT
    public static final String DATABINDING_COMPILER = "androidx.databinding:databinding-compiler:" + V_DATABINDING_COMPILER;
    public static final String LIFECYCLE_VIEWMODEL = "android.arch.lifecycle:viewmodel:" + V_LIFECYCLE_VIEWMODEL;
    public static final String LIFECYCLE_EXTENSIONS = "android.arch.lifecycle:extensions:" + V_LIFECYCLE_EXTENSIONS;

    public static final String KOTLIN_CORE = "androidx.core:core-ktx:"+V_CORE_KTX;
    public static final String KOTLIN_JDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:"+V_KOTLIN;


    // testImplementation
    public static final String JUNIT = "junit:junit:"+V_JUNIT;
    // androidTestImplementation
    public static final String TEST_RUNNER = "androidx.test:runner:"+V_TEST_RUNNER;
    public static final String TEST_ESPRESSO = "androidx.test.espresso:espresso-core:"+V_TEST_ESPRESSO;

}
