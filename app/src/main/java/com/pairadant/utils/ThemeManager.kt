class ThemeManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private val themeKey = intPreferencesKey("theme_mode")

    val themeMode: Flow<Int> = dataStore.data.map { preferences ->
        preferences[themeKey] ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    suspend fun setThemeMode(mode: Int) {
        dataStore.edit { preferences ->
            preferences[themeKey] = mode
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}