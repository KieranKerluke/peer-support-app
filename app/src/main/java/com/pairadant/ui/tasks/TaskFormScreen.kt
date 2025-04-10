@Composable
fun TaskFormScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    taskId: Long? = null,
    onNavigateBack: () -> Unit
) {
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf(System.currentTimeMillis()) }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }
    var selectedPatientId by remember { mutableStateOf<Long?>(null) }
    
    val patients by viewModel.patients.collectAsState()
    
    LaunchedEffect(taskId) {
        taskId?.let { id ->
            viewModel.getTaskById(id)?.let { task ->
                description = task.description
                dueDate = task.dueDate
                priority = task.priority
                selectedPatientId = task.patientId
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskId == null) "New Task" else "Edit Task") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
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
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Date Picker
            var showDatePicker by remember { mutableStateOf(false) }
            OutlinedButton(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Due Date: ${SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(dueDate))}")
            }
            
            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    confirmButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDatePicker = false }) {
                            Text("Cancel")
                        }
                    }
                ) {
                    DatePicker(
                        state = rememberDatePickerState(initialSelectedDateMillis = dueDate),
                        onDateSelected = { dueDate = it }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Priority Selection
            Column {
                Text("Priority", style = MaterialTheme.typography.labelMedium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Priority.values().forEach { priorityOption ->
                        FilterChip(
                            selected = priority == priorityOption,
                            onClick = { priority = priorityOption },
                            label = { Text(priorityOption.name) }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Patient Selection
            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = { }
            ) {
                OutlinedTextField(
                    value = patients.find { it.id == selectedPatientId }?.name ?: "No Patient",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Link to Patient (Optional)") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = false) },
                    modifier = Modifier.fillMaxWidth()
                )
                
                ExposedDropdownMenu(
                    expanded = false,
                    onDismissRequest = { }
                ) {
                    patients.forEach { patient ->
                        DropdownMenuItem(
                            text = { Text(patient.name) },
                            onClick = { selectedPatientId = patient.id }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = {
                    val task = Task(
                        id = taskId ?: 0,
                        description = description,
                        dueDate = dueDate,
                        priority = priority,
                        patientId = selectedPatientId
                    )
                    if (taskId == null) {
                        viewModel.addTask(task)
                    } else {
                        viewModel.updateTask(task)
                    }
                    onNavigateBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (taskId == null) "Create Task" else "Update Task")
            }
        }
    }
}