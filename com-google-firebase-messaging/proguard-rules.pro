# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/erdal/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn com.google.errorprone.annotations.**
-dontwarn org.codehaus.**
-dontwarn okio.**
# keep the class and specified members from being removed or renamed
-keep class com.transcendensoft.hedbanz.BuildConfig { *; }

# keep the specified class members from being removed or renamed
# only if the class is preserved
-keepclassmembers class com.transcendensoft.hedbanz.BuildConfig { *; }

# keep everything in this package from being removed or renamed
-keep class com.transcendensoft.hedbanz.data.network.** { *; }

# keep everything in this package from being removed or renamed
-keep class com.transcendensoft.hedbanz.data.models.** { *; }

# keep everything in this package from being removed or renamed
-keep class com.transcendensoft.hedbanz.data.source.** { *; }

# keep everything in this package from being removed or renamed
-keep class com.transcendensoft.hedbanz.data.repository.** { *; }

# keep everything in this package from being removed or renamed
-keep class com.transcendensoft.hedbanz.domain.** { *; }