@Dao
interface RapportFamilleDao {
    @Query("SELECT * FROM rapport_famille WHERE sessionId = :sessionId")
    fun getRapportBySessionId(sessionId: Long): Flow<RapportFamille?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rapport: RapportFamille): Long

    @Update
    suspend fun update(rapport: RapportFamille)

    @Delete
    suspend fun delete(rapport: RapportFamille)

    @Query("SELECT * FROM rapport_famille")
    suspend fun getAllRapportsSync(): List<RapportFamille>
}