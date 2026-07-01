package com.rim.droid.data.theme

import android.content.Context
import android.content.SharedPreferences
import com.google.common.truth.Truth.assertThat
import com.rim.droid.domain.entity.ThemeType
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class ThemeRepositoryImplTest {

    private lateinit var prefs: SharedPreferences
    private lateinit var repo: ThemeRepositoryImpl

    @Before
    fun setUp() {
        prefs = InMemorySharedPreferences()
        val context = mock<Context> {
            on { getSharedPreferences(any(), any()) } doReturn prefs
        }
        repo = ThemeRepositoryImpl(context)
    }

    @Test
    fun `no stored value defaults to system`() {
        assertThat(repo.getTheme()).isEqualTo(ThemeType.SYSTEM)
    }

    @Test
    fun `round-trip persists light`() {
        repo.setTheme(ThemeType.LIGHT)
        assertThat(repo.getTheme()).isEqualTo(ThemeType.LIGHT)
    }

    @Test
    fun `round-trip persists dark`() {
        repo.setTheme(ThemeType.DARK)
        assertThat(repo.getTheme()).isEqualTo(ThemeType.DARK)
    }
}

private class InMemorySharedPreferences : SharedPreferences {
    private val store = mutableMapOf<String, Any?>()
    private val listeners = mutableSetOf<SharedPreferences.OnSharedPreferenceChangeListener>()

    override fun getAll(): Map<String, *> = store.toMap()
    override fun getString(key: String?, defValue: String?): String? = store[key] as? String ?: defValue
    override fun getStringSet(key: String?, defValue: MutableSet<String>?): MutableSet<String>? =
        (store[key] as? MutableSet<String>) ?: defValue
    override fun getInt(key: String?, defValue: Int): Int = (store[key] as? Int) ?: defValue
    override fun getLong(key: String?, defValue: Long): Long = (store[key] as? Long) ?: defValue
    override fun getFloat(key: String?, defValue: Float): Float = (store[key] as? Float) ?: defValue
    override fun getBoolean(key: String?, defValue: Boolean): Boolean = (store[key] as? Boolean) ?: defValue
    override fun contains(key: String?): Boolean = store.containsKey(key)
    override fun edit(): SharedPreferences.Editor = InMemoryEditor(this)
    override fun registerOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?,
    ) {
        listener?.let { listeners.add(it) }
    }
    override fun unregisterOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?,
    ) {
        listener?.let { listeners.remove(it) }
    }

    fun put(key: String, value: Any?) {
        store[key] = value
        listeners.forEach { it.onSharedPreferenceChanged(this, key) }
    }

    fun remove(key: String) {
        store.remove(key)
        listeners.forEach { it.onSharedPreferenceChanged(this, key) }
    }
}

private class InMemoryEditor(private val prefs: InMemorySharedPreferences) : SharedPreferences.Editor {
    private val pending = mutableMapOf<String, Any?>()
    private val pendingRemoves = mutableSetOf<String>()

    override fun putString(key: String?, value: String?): SharedPreferences.Editor {
        key?.let { pending[it] = value }
        return this
    }
    override fun putStringSet(key: String?, values: MutableSet<String>?): SharedPreferences.Editor {
        key?.let { pending[it] = values }
        return this
    }
    override fun putInt(key: String?, value: Int): SharedPreferences.Editor {
        key?.let { pending[it] = value }
        return this
    }
    override fun putLong(key: String?, value: Long): SharedPreferences.Editor {
        key?.let { pending[it] = value }
        return this
    }
    override fun putFloat(key: String?, value: Float): SharedPreferences.Editor {
        key?.let { pending[it] = value }
        return this
    }
    override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor {
        key?.let { pending[it] = value }
        return this
    }
    override fun remove(key: String?): SharedPreferences.Editor {
        key?.let {
            pendingRemoves.add(it)
            pending.remove(it)
        }
        return this
    }
    override fun clear(): SharedPreferences.Editor {
        pending.clear()
        return this
    }
    override fun commit(): Boolean {
        apply()
        return true
    }
    override fun apply() {
        pendingRemoves.forEach { prefs.remove(it) }
        pending.forEach { (k, v) -> prefs.put(k, v) }
        pending.clear()
        pendingRemoves.clear()
    }
}
