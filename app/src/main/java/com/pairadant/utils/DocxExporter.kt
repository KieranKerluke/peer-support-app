class DocxExporter @Inject constructor(
    private val context: Context
) {
    fun exportRapportMedical(rapport: RapportMedicale, patient: Patient): Uri {
        val wordDoc = XWPFDocument()
        
        wordDoc.createParagraph().apply {
            createRun().apply {
                setText("Rapport Médical")
                setBold(true)
                setFontSize(16)
            }
        }

        wordDoc.createParagraph().apply {
            createRun().setText("Patient: ${patient.name}")
        }

        wordDoc.createParagraph().apply {
            createRun().setText("Date: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(rapport.lastModified))}")
        }

        // Add report sections
        addSection(wordDoc, "État Mental", rapport.etatMental)
        addSection(wordDoc, "Observations", rapport.observations)
        addSection(wordDoc, "Plan d'Action", rapport.planAction)
        addSection(wordDoc, "Conclusion", rapport.conclusion)

        return saveDocument(wordDoc, "rapport_medical_${patient.name}_${rapport.lastModified}.docx")
    }

    fun exportRapportFamille(rapport: RapportFamille, patient: Patient): Uri {
        val wordDoc = XWPFDocument()
        
        wordDoc.createParagraph().apply {
            createRun().apply {
                setText("Rapport Famille")
                setBold(true)
                setFontSize(16)
            }
        }

        // Add report sections
        addSection(wordDoc, "Objectif de la séance", rapport.objectifSeance)
        addSection(wordDoc, "Thèmes abordés avec le patient", rapport.themesPatient)
        addSection(wordDoc, "Thèmes abordés avec la famille", rapport.themesFamille)
        addSection(wordDoc, "Observations générales", rapport.observationsGenerales)
        addSection(wordDoc, "Conclusion", rapport.conclusion)

        return saveDocument(wordDoc, "rapport_famille_${patient.name}_${rapport.lastModified}.docx")
    }

    private fun addSection(doc: XWPFDocument, title: String, content: String) {
        doc.createParagraph().apply {
            createRun().apply {
                setText(title)
                setBold(true)
            }
        }
        doc.createParagraph().apply {
            createRun().setText(content)
        }
        doc.createParagraph() // Empty paragraph for spacing
    }

    private fun saveDocument(doc: XWPFDocument, fileName: String): Uri {
        val file = File(context.getExternalFilesDir(null), fileName)
        doc.write(FileOutputStream(file))
        doc.close()
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    }
}