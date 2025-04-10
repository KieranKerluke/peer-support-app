@HiltViewModel
class RapportFamilleViewModel @Inject constructor(
    private val rapportFamilleDao: RapportFamilleDao
) : ViewModel() {
    
    private val _rapportState = MutableStateFlow<RapportFamille?>(null)
    val rapportState = _rapportState.asStateFlow()

    private var autoSaveJob: Job? = null

    fun loadRapport(sessionId: Long) {
        viewModelScope.launch {
            rapportFamilleDao.getRapportBySessionId(sessionId)
                .collect { rapport ->
                    _rapportState.value = rapport ?: RapportFamille(sessionId = sessionId)
                }
        }
    }

    fun updateField(field: String, value: String, sessionId: Long) {
        val currentRapport = _rapportState.value ?: RapportFamille(sessionId = sessionId)
        val updatedRapport = when(field) {
            "objectif" -> currentRapport.copy(objectifSeance = value)
            "themesPatient" -> currentRapport.copy(themesPatient = value)
            "themesFamille" -> currentRapport.copy(themesFamille = value)
            "observations" -> currentRapport.copy(observationsGenerales = value)
            "conclusion" -> currentRapport.copy(conclusion = value)
            else -> currentRapport
        }
        _rapportState.value = updatedRapport

        autoSaveJob?.cancel()
        autoSaveJob = viewModelScope.launch {
            delay(1000) // Auto-save after 1 second of inactivity
            saveRapport()
        }
    }

    private suspend fun saveRapport() {
        _rapportState.value?.let { rapport ->
            rapportFamilleDao.insert(rapport.copy(lastModified = System.currentTimeMillis()))
        }
    }

    fun manualSave() {
        viewModelScope.launch {
            saveRapport()
        }
    }
}