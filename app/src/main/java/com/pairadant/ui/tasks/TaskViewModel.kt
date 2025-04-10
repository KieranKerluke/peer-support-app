@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskDao: TaskDao,
    private val patientDao: PatientDao
) : ViewModel() {

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks = _tasks.asStateFlow()

    private val _patients = MutableStateFlow<List<Patient>>(emptyList())
    val patients = _patients.asStateFlow()

    init {
        viewModelScope.launch {
            taskDao.getAllTasks().collect {
                _tasks.value = it
            }
        }
        viewModelScope.launch {
            patientDao.getAllPatients().collect {
                _patients.value = it
            }
        }
    }

    fun addTask(task: Task) = viewModelScope.launch {
        taskDao.insert(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        taskDao.update(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        taskDao.delete(task)
    }

    fun toggleTaskStatus(task: Task) = viewModelScope.launch {
        taskDao.update(task.copy(status = !task.status))
    }

    fun filterByStatus(completed: Boolean) = viewModelScope.launch {
        taskDao.getTasksByStatus(completed).collect {
            _tasks.value = it
        }
    }

    fun filterByPatient(patientId: Long) = viewModelScope.launch {
        taskDao.getTasksByPatient(patientId).collect {
            _tasks.value = it
        }
    }
}