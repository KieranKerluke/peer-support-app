@Composable
fun RapportFamilleScreen(
    viewModel: RapportFamilleViewModel = hiltViewModel(),
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
                title = { Text("Rapport Famille") },
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
                value = rapport?.objectifSeance ?: "",
                onValueChange = { viewModel.updateField("objectif", it, sessionId) },
                label = { Text("Objectif de la séance") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = rapport?.themesPatient ?: "",
                onValueChange = { viewModel.updateField("themesPatient", it, sessionId) },
                label = { Text("Thèmes abordés avec le patient") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            // ... Similar OutlinedTextField for other fields ...
        }
    }
}