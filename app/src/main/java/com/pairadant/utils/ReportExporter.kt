class ReportExporter @Inject constructor(
    private val context: Context
) {
    fun exportRapport(rapport: Any, patient: Patient): Uri {
        val wordDoc = XWPFDocument()
        
        when (rapport) {
            is RapportMedicale -> createMedicalReport(wordDoc, rapport, patient)
            is RapportFamille -> createFamilyReport(wordDoc, rapport, patient)
        }
        
        return saveDocument(wordDoc, generateFileName(rapport, patient))
    }

    private fun createMedicalReport(doc: XWPFDocument, rapport: RapportMedicale, patient: Patient) {
        // Add medical report formatting and content
    }

    private fun createFamilyReport(doc: XWPFDocument, rapport: RapportFamille, patient: Patient) {
        // Add family report formatting and content
    }

    private fun generateFileName(rapport: Any, patient: Patient): String {
        val type = when (rapport) {
            is RapportMedicale -> "medical"
            is RapportFamille -> "famille"
            else -> "rapport"
        }
        return "rapport_${type}_${patient.name}_${System.currentTimeMillis()}.docx"
    }
}