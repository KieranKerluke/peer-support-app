@Database(
    entities = [
        Patient::class,
        Session::class,
        RapportMedicale::class,
        RapportFamille::class,
        Task::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun patientDao(): PatientDao
    abstract fun sessionDao(): SessionDao
    abstract fun rapportMedicaleDao(): RapportMedicaleDao
    abstract fun rapportFamilleDao(): RapportFamilleDao
    abstract fun taskDao(): TaskDao

    companion object {
        const val DATABASE_NAME = "pairadant_db"
    }
}