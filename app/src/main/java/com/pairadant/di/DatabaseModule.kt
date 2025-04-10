@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun providePatientDao(database: AppDatabase) = database.patientDao()

    @Provides
    fun provideSessionDao(database: AppDatabase) = database.sessionDao()

    @Provides
    fun provideRapportMedicaleDao(database: AppDatabase) = database.rapportMedicaleDao()

    @Provides
    fun provideRapportFamilleDao(database: AppDatabase) = database.rapportFamilleDao()

    @Provides
    fun provideTaskDao(database: AppDatabase) = database.taskDao()
}