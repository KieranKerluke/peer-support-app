@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Lock orientation to portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContent {
            val isDarkTheme by viewModel.isDarkTheme.collectAsState()
            val isBiometricEnabled by viewModel.isBiometricEnabled.collectAsState()
            var isAuthenticated by remember { mutableStateOf(!isBiometricEnabled) }

            PairAidantTheme(darkTheme = isDarkTheme) {
                if (isBiometricEnabled && !isAuthenticated) {
                    BiometricPromptScreen(
                        onAuthSuccess = { isAuthenticated = true }
                    )
                } else {
                    val navController = rememberNavController()
                    
                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = navController.currentDestination?.route == "dashboard",
                                    onClick = { navController.navigate("dashboard") },
                                    icon = { Icon(Icons.Default.Home, "Dashboard") },
                                    label = { Text("Dashboard") }
                                )
                                // Add other navigation items
                            }
                        }
                    ) { padding ->
                        NavHost(
                            navController = navController,
                            startDestination = "dashboard",
                            modifier = Modifier.padding(padding)
                        ) {
                            composable("dashboard") { DashboardScreen(navController) }
                            composable("patients") { PatientListScreen(navController) }
                            composable("sessions") { SessionListScreen(navController) }
                            composable("calendar") { CalendarScreen(navController) }
                            composable("tasks") { TaskListScreen(navController) }
                            composable("settings") { SettingsScreen(navController) }
                            // Add other routes
                        }
                    }
                }
            }
        }
    }
}