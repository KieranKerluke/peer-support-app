@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val themeMode by viewModel.themeMode.collectAsState()
    val isBiometricEnabled by viewModel.isBiometricEnabled.collectAsState()
    var showResetDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            ListItem(
                headlineContent = { Text("Theme") },
                supportingContent = { Text("Choose app theme") },
                trailingContent = {
                    IconToggleButton(
                        checked = themeMode == AppCompatDelegate.MODE_NIGHT_YES,
                        onCheckedChange = { checked ->
                            viewModel.setThemeMode(
                                if (checked) AppCompatDelegate.MODE_NIGHT_YES
                                else AppCompatDelegate.MODE_NIGHT_NO
                            )
                        }
                    ) {
                        Icon(
                            if (themeMode == AppCompatDelegate.MODE_NIGHT_YES) 
                                Icons.Default.DarkMode
                            else Icons.Default.LightMode,
                            contentDescription = "Theme"
                        )
                    }
                }
            )

            ListItem(
                headlineContent = { Text("Biometric Lock") },
                supportingContent = { Text("Secure app with fingerprint") },
                trailingContent = {
                    Switch(
                        checked = isBiometricEnabled,
                        onCheckedChange = { viewModel.setBiometricEnabled(it) }
                    )
                }
            )

            ListItem(
                headlineContent = { Text("Export Data") },
                supportingContent = { Text("Backup app data") },
                trailingContent = {
                    IconButton(onClick = {
                        viewModel.exportData()
                            .onEach { uri -> /* Handle success */ }
                            .catch { /* Handle error */ }
                            .launchIn(rememberCoroutineScope())
                    }) {
                        Icon(Icons.Default.Upload, "Export")
                    }
                }
            )

            ListItem(
                headlineContent = { Text("Import Data") },
                supportingContent = { Text("Restore from backup") },
                trailingContent = {
                    IconButton(onClick = {
                        /* Launch file picker */ 
                    }) {
                        Icon(Icons.Default.Download, "Import")
                    }
                }
            )

            ListItem(
                headlineContent = { Text("Reset App") },
                supportingContent = { Text("Clear all data") },
                trailingContent = {
                    IconButton(onClick = { showResetDialog = true }) {
                        Icon(Icons.Default.Delete, "Reset")
                    }
                }
            )
        }
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = { Text("Reset App") },
            text = { Text("Are you sure you want to clear all data? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.resetApp()
                        showResetDialog = false
                    }
                ) {
                    Text("Reset")
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}