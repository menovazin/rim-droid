# Rules applied when the data module is consumed by the app (R8 at app packaging).

# Gson reflects on DTO fields/constructors by name.
-keep class com.rim.droid.data.dto.** { *; }

# Retrofit service interface methods (library also ships its own rules).
-keep,allowobfuscation,allowshrinking interface com.rim.droid.data.api.**
