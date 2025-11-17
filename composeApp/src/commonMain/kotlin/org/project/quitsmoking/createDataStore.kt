package org.project.quitsmoking

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect fun createDataStore(): DataStore<Preferences>

internal const val dataStoreFileName = "quit.smoking.preferences_pb"