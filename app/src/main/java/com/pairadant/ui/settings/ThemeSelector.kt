@Composable
fun ThemeSelector(
    currentTheme: Int,
    onThemeSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Theme",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ThemeOption(
                text = "Light",
                icon = Icons.Default.LightMode,
                selected = currentTheme == AppCompatDelegate.MODE_NIGHT_NO,
                onClick = { onThemeSelected(AppCompatDelegate.MODE_NIGHT_NO) }
            )
            
            ThemeOption(
                text = "Dark",
                icon = Icons.Default.DarkMode,
                selected = currentTheme == AppCompatDelegate.MODE_NIGHT_YES,
                onClick = { onThemeSelected(AppCompatDelegate.MODE_NIGHT_YES) }
            )
            
            ThemeOption(
                text = "System",
                icon = Icons.Default.Settings,
                selected = currentTheme == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
                onClick = { onThemeSelected(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) }
            )
        }
    }
}

@Composable
private fun ThemeOption(
    text: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .background(
                if (selected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surface
            )
            .padding(12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = if (selected) 
                MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = if (selected) 
                MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.onSurface
        )
    }
}