@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val sessionDao: SessionDao
) : ViewModel() {

    private val _sessionsForDate = MutableStateFlow<List<Session>>(emptyList())
    val sessionsForDate = _sessionsForDate.asStateFlow()

    fun loadSessionsForDate(timestamp: Long) {
        viewModelScope.launch {
            val startOfDay = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault()
            ).with(LocalTime.MIN).toInstant(ZoneOffset.UTC).toEpochMilli()
            
            val endOfDay = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp),
                ZoneId.systemDefault()
            ).with(LocalTime.MAX).toInstant(ZoneOffset.UTC).toEpochMilli()

            sessionDao.getSessionsByDateRange(startOfDay, endOfDay).collect { sessions ->
                _sessionsForDate.value = sessions
            }
        }
    }
}