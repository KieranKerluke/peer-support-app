@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY dueDate ASC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE patientId = :patientId")
    fun getTasksByPatient(patientId: Long): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE status = :completed")
    fun getTasksByStatus(completed: Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)
}