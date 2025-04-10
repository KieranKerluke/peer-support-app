@Entity(
    tableName = "rapport_famille",
    foreignKeys = [
        ForeignKey(
            entity = Session::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RapportFamille(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sessionId: Long,
    var objectifSeance: String = "",
    var themesPatient: String = "",
    var themesFamille: String = "",
    var observationsGenerales: String = "",
    var conclusion: String = "",
    var lastModified: Long = System.currentTimeMillis()
)