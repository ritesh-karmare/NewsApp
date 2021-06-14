package rk.information.news.utils.dataStore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val USER_PREFERENCES_NAME = "user_preferences"
const val DEFAULT_TOPIC = "India News"

class DataStoreHelper(private val context: Context) {

    private object PreferencesKeys {
        val TOPICS_LIST = stringSetPreferencesKey("topics_list")
        val SELECTED_TOPIC = stringPreferencesKey("selected_topic")
    }

    companion object {
        private val Context.dataStore by preferencesDataStore(
                name = USER_PREFERENCES_NAME
        )
    }

    val getTopicsList: Flow<Set<String>> = context.dataStore.data
            .map { preferences ->
                val showCompleted = preferences[PreferencesKeys.TOPICS_LIST] ?: mutableSetOf(DEFAULT_TOPIC)
                showCompleted
            }

    val getSelectedTopic: Flow<String> = context.dataStore.data
            .map { preferences ->
                val showCompleted = preferences[PreferencesKeys.SELECTED_TOPIC] ?: DEFAULT_TOPIC
                showCompleted
            }


    suspend fun updateTopicList(topicList: Set<String>) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOPICS_LIST] = topicList
        }
    }

    suspend fun updateSelectedTopic(selectedTopic: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SELECTED_TOPIC] = selectedTopic
        }
    }


}