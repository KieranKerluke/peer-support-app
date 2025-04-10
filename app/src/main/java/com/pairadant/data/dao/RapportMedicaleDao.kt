@Dao
interface RapportMedicaleDao {
    @Query("SELECT * FROM rapport_medicale WHERE sessionId = :sessionId")
    fun getRapportBySessionId(sessionId: Long): Flow<RapportMedicale?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rapport: RapportMedicale): Long

    @Update
    suspend fun update(rapport: RapportMedicale)

    @Delete
    suspend fun delete(rapport: RapportMedicale)

    @Query("SELECT * FROM rapport_medicale")
    suspend fun getAllRapportsSync(): List<RapportMedicale>
}