@Entity(tableName = "patients")
data class Patient(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String,
    var dateOfBirth: Long,
    var contactInfo: String,
    var tags: String
)