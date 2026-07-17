# Rules applied when the domain module is consumed by the app (R8 at app packaging).

# Domain entities are @Serializable and passed through type-safe navigation.
-keep class com.rim.droid.domain.entity.** { *; }
