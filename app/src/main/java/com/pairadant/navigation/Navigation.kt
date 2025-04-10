sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object PatientList : Screen("patients")
    object PatientForm : Screen("patient/{id}") {
        fun createRoute(id: Long?) = "patient/${id ?: "new"}"
    }
    object SessionList : Screen("sessions/{patientId}") {
        fun createRoute(patientId: Long) = "sessions/$patientId"
    }
    object SessionForm : Screen("session/{sessionId}") {
        fun createRoute(sessionId: Long?) = "session/${sessionId ?: "new"}"
    }
    object Calendar : Screen("calendar")
    object Tasks : Screen("tasks")
    object Settings : Screen("settings")
}