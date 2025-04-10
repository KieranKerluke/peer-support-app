@Entity(
    tableName = "rapport_medicale",
    foreignKeys = [
        ForeignKey(
            entity = Session::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RapportMedicale(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sessionId: Long,
    var objectifSeances: String = "",
    var exempleCasPresent: String = "",
    var themesAbordes: String = "",
    var etatEmotionnelDebut: String = "",
    var etatEmotionnelFin: String = "",
    var capaciteExpressionPensees: String = "",
    var reactionsInterventions: String = "",
    var progresObserves: String = "",
    var barriereEmotionnelles: String = "",
    var problemeCommunicationFamiliale: String = "",
    var facteursExternes: String = "",
    var recommendationsPatient: String = "",
    var recommendationsFamille: String = "",
    var conclusionGenerale: String = "",
    var lastModified: Long = System.currentTimeMillis()
)