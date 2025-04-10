@HiltViewModel
class RapportMedicaleViewModel @Inject constructor(
    private val rapportMedicaleDao: RapportMedicaleDao
) : ViewModel() {
    
    private val _rapportState = MutableStateFlow<RapportMedicale?>(null)
    val rapportState = _rapportState.asStateFlow()

    private var autoSaveJob: Job? = null

    fun loadRapport(sessionId: Long) {
        viewModelScope.launch {
            rapportMedicaleDao.getRapportBySessionId(sessionId)
                .collect { rapport ->
                    _rapportState.value = rapport ?: RapportMedicale(sessionId = sessionId)
                }
        }
    }

    fun updateField(field: String, value: String, sessionId: Long) {
        val currentRapport = _rapportState.value ?: RapportMedicale(sessionId = sessionId)
        val updatedRapport = when(field) {
            "objectif" -> currentRapport.copy(objectifSeances = value)
            "exemple" -> currentRapport.copy(exempleCasPresent = value)
            "themes" -> currentRapport.copy(themesAbordes = value)
            "emotionDebut" -> currentRapport.copy(etatEmotionnelDebut = value)
            "emotionFin" -> currentRapport.copy(etatEmotionnelFin = value)
            "capacite" -> currentRapport.copy(capaciteExpressionPensees = value)
            "reactions" -> currentRapport.copy(reactionsInterventions = value)
            "progres" -> currentRapport.copy(progresObserves = value)
            "barrieres" -> currentRapport.copy(barriereEmotionnelles = value)
            "communication" -> currentRapport.copy(problemeCommunicationFamiliale = value)
            "facteurs" -> currentRapport.copy(facteursExternes = value)
            "recPatient" -> currentRapport.copy(recommendationsPatient = value)
            "recFamille" -> currentRapport.copy(recommendationsFamille = value)
            "conclusion" -> currentRapport.copy(conclusionGenerale = value)
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
            rapportMedicaleDao.insert(rapport.copy(lastModified = System.currentTimeMillis()))
        }
    }

    fun manualSave() {
        viewModelScope.launch {
            saveRapport()
        }
    }
}