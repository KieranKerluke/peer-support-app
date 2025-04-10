@Entity(
    tableName = "sessions",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["id"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Session(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val patientId: Long,
    var date: Long,
    var duration: Int, // in minutes
    var location: String
)