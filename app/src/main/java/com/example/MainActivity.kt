package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Domain
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AiAssistantCompanionDialog
import com.example.ui.components.CamouflageScreen
import com.example.ui.components.SahayakTopBar
import com.example.ui.components.SupportCompanionAvatar
import com.example.ui.screens.AnonymousChatScreen
import com.example.ui.screens.CounsellorPortalScreen
import com.example.ui.screens.DirectoryScreen
import com.example.ui.screens.EmergencySosScreen
import com.example.ui.screens.EvidenceVaultScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ImpactDashboardScreen
import com.example.ui.screens.IntakeConfirmationScreen
import com.example.ui.screens.IntakeQuestionsScreen
import com.example.ui.screens.IntakeResultScreen
import com.example.ui.screens.IntakeRoleCategoryScreen
import com.example.ui.screens.ReportTrackingScreen
import com.example.ui.screens.ResourcesScreen
import com.example.ui.screens.SignsExplainerScreen
import com.example.ui.screens.TeenCheckScreen
import com.example.ui.screens.TrustedContactsScreen
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BluePrimaryLight
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.SahayakTheme
import com.example.ui.theme.SecondaryCyan
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate950
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.SahayakViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: SahayakViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsState()
            SahayakTheme(darkTheme = isDarkMode) {
                SahayakMainApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun SahayakMainApp(viewModel: SahayakViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val isCamouflageActive by viewModel.isCamouflageActive.collectAsState()
    val notificationMessage by viewModel.userNotification.collectAsState()
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val isAiAssistantOpen by viewModel.isAiAssistantOpen.collectAsState()
    val aiAssistantAdvice by viewModel.aiAssistantAdvice.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(notificationMessage) {
        if (notificationMessage != null) {
            snackbarHostState.showSnackbar(notificationMessage!!)
            viewModel.clearNotification()
        }
    }

    if (isCamouflageActive) {
        CamouflageScreen(
            onExitCamouflage = { viewModel.exitCamouflage() },
            modifier = Modifier.fillMaxSize()
        )
    } else if (currentScreen == AppScreen.ANONYMOUS_CHAT) {
        AnonymousChatScreen(
            viewModel = viewModel,
            modifier = Modifier.fillMaxSize()
        )
    } else {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                SahayakTopBar(
                    title = "SAYANAK",
                    subtitle = "Anonymous Triage & Healing Support",
                    onQuickExit = { viewModel.triggerQuickExitCamouflage() },
                    onEmergencySos = { viewModel.navigateTo(AppScreen.EMERGENCY_SOS) },
                    isDarkMode = isDarkMode,
                    onToggleTheme = { viewModel.toggleTheme() }
                )
            },
            bottomBar = {
                SahayakBottomNavigationBar(
                    currentScreen = currentScreen,
                    onNavigate = { screen ->
                        if (screen == AppScreen.ANONYMOUS_CHAT) {
                            viewModel.startAnonymousChat()
                        } else {
                            viewModel.navigateTo(screen)
                        }
                    }
                )
            },
            floatingActionButton = {
                if (currentScreen != AppScreen.EMERGENCY_SOS) {
                    FloatingActionButton(
                        onClick = { viewModel.toggleAiAssistant() },
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .testTag("ai_companion_fab")
                            .border(1.5.dp, Color.White.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                    ) {
                        Box(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            androidx.compose.foundation.layout.Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(6.dp)
                            ) {
                                SupportCompanionAvatar(size = 28.dp, isPulsing = false)
                                Text(
                                    text = "AI Companion",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                androidx.compose.animation.AnimatedContent(
                    targetState = currentScreen,
                    transitionSpec = {
                        fadeIn(animationSpec = androidx.compose.animation.core.tween(220)) togetherWith
                                fadeOut(animationSpec = androidx.compose.animation.core.tween(180))
                    },
                    label = "ScreenTransition"
                ) { targetScreen ->
                    when (targetScreen) {
                        AppScreen.HOME -> HomeScreen(viewModel = viewModel)
                        AppScreen.INTAKE_ROLE_CATEGORY -> IntakeRoleCategoryScreen(viewModel = viewModel)
                        AppScreen.INTAKE_QUESTIONS -> IntakeQuestionsScreen(viewModel = viewModel)
                        AppScreen.INTAKE_RESULT -> IntakeResultScreen(viewModel = viewModel)
                        AppScreen.INTAKE_CONFIRMATION -> IntakeConfirmationScreen(viewModel = viewModel)
                        AppScreen.TRACK_REPORT -> ReportTrackingScreen(viewModel = viewModel)
                        AppScreen.EMERGENCY_SOS -> EmergencySosScreen(viewModel = viewModel)
                        AppScreen.SIGNS_EXPLAINER -> SignsExplainerScreen(viewModel = viewModel)
                        AppScreen.TEEN_CHECK -> TeenCheckScreen(viewModel = viewModel)
                        AppScreen.COUNSELLOR_PORTAL -> CounsellorPortalScreen(viewModel = viewModel)
                        AppScreen.DIRECTORY -> DirectoryScreen(viewModel = viewModel)
                        AppScreen.RESOURCES -> ResourcesScreen(viewModel = viewModel)
                        AppScreen.AWARENESS_QUIZ -> SignsExplainerScreen(viewModel = viewModel)
                        AppScreen.ANONYMOUS_CHAT -> AnonymousChatScreen(viewModel = viewModel)
                        AppScreen.EVIDENCE_VAULT -> EvidenceVaultScreen(viewModel = viewModel)
                        AppScreen.TRUSTED_CONTACTS -> TrustedContactsScreen(viewModel = viewModel)
                        AppScreen.IMPACT_DASHBOARD -> ImpactDashboardScreen(viewModel = viewModel)
                    }
                }
            }
        }

        // Floating AI Assistant Companion Dialog
        if (isAiAssistantOpen) {
            AiAssistantCompanionDialog(
                advice = aiAssistantAdvice ?: "Hello! I am your Sahayak AI Companion. I am here to provide confidential triage guidance, mental health support, recovery steps, and safety assistance in complete privacy. How can I support you today?",
                onAsk = { query -> viewModel.askAiAssistant(query) },
                onDismiss = { viewModel.toggleAiAssistant() },
                onStartIntake = { viewModel.navigateTo(AppScreen.INTAKE_ROLE_CATEGORY) },
                onStartChat = { viewModel.startAnonymousChat() },
                onEmergencySos = { viewModel.navigateTo(AppScreen.EMERGENCY_SOS) }
            )
        }
    }
}

@Composable
fun SahayakBottomNavigationBar(
    currentScreen: AppScreen,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.98f),
        tonalElevation = 6.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            modifier = Modifier
                .height(68.dp)
                .navigationBarsPadding()
        ) {
            val isHome = currentScreen == AppScreen.HOME
            NavigationBarItem(
                selected = isHome,
                onClick = { onNavigate(AppScreen.HOME) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home",
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = { Text("Home", fontSize = 11.sp, fontWeight = if (isHome) FontWeight.Bold else FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                ),
                modifier = Modifier.testTag("nav_home")
            )

            val isIntake = currentScreen in listOf(
                AppScreen.INTAKE_ROLE_CATEGORY,
                AppScreen.INTAKE_QUESTIONS,
                AppScreen.INTAKE_RESULT,
                AppScreen.INTAKE_CONFIRMATION
            )
            NavigationBarItem(
                selected = isIntake,
                onClick = { onNavigate(AppScreen.INTAKE_ROLE_CATEGORY) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.AddCircleOutline,
                        contentDescription = "New Report",
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = { Text("Intake", fontSize = 11.sp, fontWeight = if (isIntake) FontWeight.Bold else FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                ),
                modifier = Modifier.testTag("nav_intake")
            )

            val isChat = currentScreen == AppScreen.ANONYMOUS_CHAT
            NavigationBarItem(
                selected = isChat,
                onClick = { onNavigate(AppScreen.ANONYMOUS_CHAT) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.ChatBubbleOutline,
                        contentDescription = "Anonymous Support Chat",
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = { Text("Chat", fontSize = 11.sp, fontWeight = if (isChat) FontWeight.Bold else FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                ),
                modifier = Modifier.testTag("nav_chat")
            )

            val isTrack = currentScreen == AppScreen.TRACK_REPORT
            NavigationBarItem(
                selected = isTrack,
                onClick = { onNavigate(AppScreen.TRACK_REPORT) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Track Token",
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = { Text("Track", fontSize = 11.sp, fontWeight = if (isTrack) FontWeight.Bold else FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                ),
                modifier = Modifier.testTag("nav_track")
            )

            val isDirectory = currentScreen in listOf(AppScreen.DIRECTORY, AppScreen.RESOURCES)
            NavigationBarItem(
                selected = isDirectory,
                onClick = { onNavigate(AppScreen.DIRECTORY) },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Domain,
                        contentDescription = "Directory",
                        modifier = Modifier.size(22.dp)
                    )
                },
                label = { Text("Centres", fontSize = 11.sp, fontWeight = if (isDirectory) FontWeight.Bold else FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                ),
                modifier = Modifier.testTag("nav_directory")
            )
        }
    }
}
