@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    onNavigateToPatients: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToTasks: () -> Unit
) {
    val todaySessions by viewModel.todaySessions.collectAsState()
    val pendingTasks by viewModel.pendingTasks.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Dashboard") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Quick Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QuickActionButton(
                    icon = Icons.Default.Person,
                    text = "Add Patient",
                    onClick = onNavigateToPatients
                )
                QuickActionButton(
                    icon = Icons.Default.CalendarToday,
                    text = "Calendar",
                    onClick = onNavigateToCalendar
                )
                QuickActionButton(
                    icon = Icons.Default.Assignment,
                    text = "Tasks",
                    onClick = onNavigateToTasks
                )
            }

            // Today's Sessions
            SessionsList(sessions = todaySessions)
            
            // Pending Tasks
            TasksList(tasks = pendingTasks)
        }
    }
}