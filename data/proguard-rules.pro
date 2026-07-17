# Data module proguard rules (library self-minify — not used for project deps).
# App packaging uses consumer-rules.pro instead.
# Keep DTOs for Gson reflection
-keep class com.rim.droid.data.dto.** { *; }
