package com.jakey.aetnacompose.data.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This is many preferences manager. This is where all the data persistence happens and where
 * I declare how data is stored and retrieved. "Data Store" is always ran from suspend function.
 */

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext val context: Context
) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("")
    }

    suspend fun save(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { history ->
            history[dataStoreKey] = value
            while (history.asMap().keys.size >= 6) {
                history.remove(history.asMap().keys.first())

            }
            history[dataStoreKey] = value

        }
    }


    suspend fun deleteAllKeys() {
        context.dataStore.edit {
            it.clear()
        }
    }

    suspend fun deleteLastKey() {
        context.dataStore.edit {
            it.remove(it.asMap().keys.last())
        }
    }

    suspend fun read(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun readAllValues(): Collection<Any>? {
        val keys = context.dataStore.data
            .map {
                it.asMap().values
            }
        return keys.firstOrNull()
    }
}