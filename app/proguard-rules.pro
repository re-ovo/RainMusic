
# keep attr
-keepattributes SourceFile,LineNumberTable

# Disable obfuscate/optimize
-dontobfuscate
-dontoptimize
-verbose

# Keep Self
-keep class me.rerere.rainmusic.**

# Keep Kotlin
-keep class kotlin.**
-keep class kotlinx.**