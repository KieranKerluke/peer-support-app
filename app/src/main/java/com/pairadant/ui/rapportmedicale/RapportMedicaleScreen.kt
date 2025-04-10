@Composable
fun RapportMedicaleScreen(
    viewModel: RapportMedicaleViewModel = hiltViewModel(),
    sessionId: Long,
    onNavigateBack: () -> Unit
) {
    val rapport by viewModel.rapportState.collectAsState()
    
    LaunchedEffect(sessionId) {
        viewModel.loadRapport(sessionId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rapport Médicale") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.manualSave() }) {
                        Icon(Icons.Default.Save, "Save")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = rapport?.objectifSeances ?: "",
                onValueChange = { viewModel.updateField("objectif", it, sessionId) },
                label = { Text("Objectif des séances") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = rapport?.exempleCasPresent ?: "",
                onValueChange = { viewModel.updateField("exemple", it, sessionId) },
                label = { Text("Exemple dans le cas présent") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = rapport?.themesAbordes ?: "",
                onValueChange = { viewModel.updateField("themes", it, sessionId) },
                label = { Text("Thèmes abordés") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "État émotionnel",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            OutlinedTextField(
                value = rapport?.etatEmotionnelDebut ?: "",
                onValueChange = { viewModel.updateField("emotionDebut", it, sessionId) },
                label = { Text("Début de séance") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = rapport?.etatEmotionnelFin ?: "",
                onValueChange = { viewModel.updateField("emotionFin", it, sessionId) },
                label = { Text("Fin de séance") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = rapport?.capaciteExpressionPensees ?: "",
                onValueChange = { viewModel.updateField("capacite", it, sessionId) },
                label = { Text("Capacité à exprimer ses pensées") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = rapport?.reactionsInterventions ?: "",
                onValueChange = { viewModel.updateField("reactions", it, sessionId) },
                label = { Text("Réactions aux interventions") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = rapport?.progresObserves ?: "",
                onValueChange = { viewModel.updateField("progres", it, sessionId) },
                label = { Text("Progrès observés") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Text(
                text = "Obstacles",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            OutlinedTextField(
                value = rapport?.barriereEmotionnelles ?: "",
                onValueChange = { viewModel.updateField("barrieres", it, sessionId) },
                label = { Text("Barrières émotionnelles") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = rapport?.problemeCommunicationFamiliale ?: "",
                onValueChange = { viewModel.updateField("communication", it, sessionId) },
                label = { Text("Problèmes de communication familiale") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = rapport?.facteursExternes ?: "",
                onValueChange = { viewModel.updateField("facteurs", it, sessionId) },
                label = { Text("Facteurs externes") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = rapport?.recommendationsPatient ?: "",
                onValueChange = { viewModel.updateField("recPatient", it, sessionId) },
                label = { Text("Recommandations pour le patient") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = rapport?.recommendationsFamille ?: "",
                onValueChange = { viewModel.updateField("recFamille", it, sessionId) },
                label = { Text("Recommandations pour la famille") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = rapport?.conclusionGenerale ?: "",
                onValueChange = { viewModel.updateField("conclusion", it, sessionId) },
                label = { Text("Conclusion générale") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}