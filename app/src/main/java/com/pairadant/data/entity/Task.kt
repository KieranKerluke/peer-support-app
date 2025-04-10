@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = Patient::class,
            parentColumns = ["id"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var description: String,
    var dueDate: Long,
    @TypeConverters(PriorityConverter::class)
    var priority: Priority,
    var status: Boolean = false,
    var patientId: Long? = null
)

enum class Priority {
    LOW, MEDIUM, HIGH
}

class PriorityConverter {
    @TypeConverter
    fun toPriority(value: String) = enumValueOf<Priority>(value)

    @TypeConverter
    fun fromPriority(priority: Priority) = priority.name
}