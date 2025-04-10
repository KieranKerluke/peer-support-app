@HiltViewModel
class ExportViewModel @Inject constructor(
    private val docxExporter: DocxExporter,
    private val rapportMedicaleDao: RapportMedicaleDao,
    private val rapportFamilleDao: RapportFamilleDao,
    private val patientDao: PatientDao
) : ViewModel() {

    fun exportRapportMedical(rapportId: Long): Flow<Uri> = flow {
        val rapport = rapportMedicaleDao.getRapportById(rapportId) ?: throw IllegalStateException("Rapport not found")
        val patient = patientDao.getPatientById(rapport.patientId) ?: throw IllegalStateException("Patient not found")
        emit(docxExporter.exportRapportMedical(rapport, patient))
    }.flowOn(Dispatchers.IO)

    fun exportRapportFamille(rapportId: Long): Flow<Uri> = flow {
        val rapport = rapportFamilleDao.getRapportById(rapportId) ?: throw IllegalStateException("Rapport not found")
        val patient = patientDao.getPatientById(rapport.patientId) ?: throw IllegalStateException("Patient not found")
        emit(docxExporter.exportRapportFamille(rapport, patient))
    }.flowOn(Dispatchers.IO)
}