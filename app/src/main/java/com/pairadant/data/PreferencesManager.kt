class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {
    private val Context.dataStore by preferencesDataStore(name = "settings")
    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val THEME_MODE = intPreferencesKey("theme_mode")
        val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
    }

    val themeMode: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.THEME_MODE] ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    val isBiometricEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.BIOMETRIC_ENABLED] ?: false
    }

    suspend fun setThemeMode(mode: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_MODE] = mode
        }
    }

    suspend fun setBiometricEnabled(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.BIOMETRIC_ENABLED] = enabled
        }
    }
}