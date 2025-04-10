@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val database: AppDatabase,
    private val gson: Gson
) : ViewModel() {

    val themeMode = preferencesManager.themeMode.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    )

    val isBiometricEnabled = preferencesManager.isBiometricEnabled.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        false
    )

    fun setThemeMode(mode: Int) {
        viewModelScope.launch {
            preferencesManager.setThemeMode(mode)
        }
    }

    fun setBiometricEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferencesManager.setBiometricEnabled(enabled)
        }
    }

    fun exportData(): Flow<Uri> = flow {
        val data = AppData(
            patients = database.patientDao().getAllPatientsSync(),
            sessions = database.sessionDao().getAllSessionsSync(),
            rapportsMedicale = database.rapportMedicaleDao().getAllRapportsSync(),
            rapportsFamille = database.rapportFamilleDao().getAllRapportsSync(),
            tasks = database.taskDao().getAllTasksSync()
        )
        val json = gson.toJson(data)
        val file = File(context.getExternalFilesDir(null), "backup_${System.currentTimeMillis()}.json")
        file.writeText(json)
        emit(FileProvider.getUriForFile(context, "${context.packageName}.provider", file))
    }.flowOn(Dispatchers.IO)

    fun importData(uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val json = context.contentResolver.openInputStream(uri)?.bufferedReader().use { it?.readText() }
            json?.let {
                val data = gson.fromJson(it, AppData::class.java)
                database.clearAllTables()
                database.patientDao().insertAll(data.patients)
                database.sessionDao().insertAll(data.sessions)
                database.rapportMedicaleDao().insertAll(data.rapportsMedicale)
                database.rapportFamilleDao().insertAll(data.rapportsFamille)
                database.taskDao().insertAll(data.tasks)
            }
        }
    }

    fun resetApp() {
        viewModelScope.launch(Dispatchers.IO) {
            database.clearAllTables()
        }
    }
}