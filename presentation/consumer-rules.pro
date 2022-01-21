
-dontwarn java.lang.invoke.StringConcatFactory

##---------------Begin: proguard configuration for Hilt ----------
# Keep class names of Hilt injected ViewModels since their name are used as a multibinding map key.
-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel

##---------------Begin: proguard configuration for Coroutines ----------
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}

-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

##---------------Begin: proguard configuration for kakao ----------
-keep class com.kakao.sdk.**.model.* { <fields>; }
-keep class * extends com.google.gson.TypeAdapter

##---------------Begin: proguard configuration for naver ----------
-keep public class com.nhn.android.naverlogin.** {
       public protected *;
}

##---------------Begin: proguard configuration for removed log ----------
-assumenosideeffects class android.util.Log {
  public static *** v(...);
  public static *** d(...);
  public static *** i(...);
  public static *** w(...);
  public static *** e(...);
}

##---------------Begin: proguard configuration for lottie ----------
-dontwarn com.airbnb.lottie.**
-keep class com.airbnb.lottie.** {*;}