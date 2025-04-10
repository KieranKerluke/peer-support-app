@Composable
fun TaskListScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    onNavigateToTaskForm: (Long?) -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()
    val patients by viewModel.patients.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks") },
                actions = {
                    var expanded by remember { mutableStateOf(false) }
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.FilterList, "Filter")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("All Tasks") },
                            onClick = {
                                viewModel.init()
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Completed") },
                            onClick = {
                                viewModel.filterByStatus(true)
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Pending") },
                            onClick = {
                                viewModel.filterByStatus(false)
                                expanded = false
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToTaskForm(null) }) {
                Icon(Icons.Default.Add, "Add Task")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(tasks) { task ->
                TaskCard(
                    task = task,
                    patientName = patients.find { it.id == task.patientId }?.name,
                    onToggleStatus = { viewModel.toggleTaskStatus(task) },
                    onEdit = { onNavigateToTaskForm(task.id) },
                    onDelete = { viewModel.deleteTask(task) }
                )
            }
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    patientName: String?,
    onToggleStatus: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.titleMedium,
                    textDecoration = if (task.status) TextDecoration.LineThrough else TextDecoration.None
                )
                Checkbox(
                    checked = task.status,
                    onCheckedChange = { onToggleStatus() }
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Due: ${SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date(task.dueDate))}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            if (patientName != null) {
                Text(
                    text = "Patient: $patientName",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Delete")
                }
            }
        }
    }
}