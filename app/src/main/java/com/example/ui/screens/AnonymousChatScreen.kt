package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AnonymousChatMessage
import com.example.data.model.MessageSenderRole
import com.example.ui.components.makePhoneCall
import com.example.ui.theme.BlueAccent
import com.example.ui.theme.BlueCobaltGradient
import com.example.ui.theme.BluePrimary
import com.example.ui.theme.BluePrimaryLight
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.CardBorderHighlight
import com.example.ui.theme.InputBackground
import com.example.ui.theme.SecondaryCyan
import com.example.ui.theme.SeverityUrgentSos
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate500
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate850
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.SahayakViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AnonymousChatScreen(
    viewModel: SahayakViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val currentSessionId by viewModel.activeChatSessionId.collectAsState()
    val chatMessages by viewModel.activeChatMessages.collectAsState()
    val chatInputText by viewModel.chatInputText.collectAsState()
    val isSending by viewModel.isChatSending.collectAsState()
    val activeCategory by viewModel.activeChatCategory.collectAsState()

    var showWipeDialog by remember { mutableStateOf(false) }

    // Scroll to latest message automatically on message list change
    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val activeTriage by viewModel.activeTriageResult.collectAsState()

    val quickPrompts = when (currentLanguage) {
        com.example.localization.AppLanguage.TAMIL -> listOf(
            "என்னை என் கணவர் அடிக்கிறார்",
            "என் குழந்தை ஆபத்தில் இருக்கிறது",
            "வங்கி கணக்கு பணம் திருடப்பட்டது",
            "எனக்கு தீவிர மன அழுத்தம் & பயம் உள்ளது",
            "வழியில் ஒருவர் என்னை பின்தொடர்கிறார்"
        )
        com.example.localization.AppLanguage.HINDI -> listOf(
            "मेरे पति मेरे साथ घरेलू हिंसा कर रहे हैं",
            "मेरे बच्चे के साथ दुर्व्यवहार हो रहा है",
            "मेरे बैंक खाते से साइबर धोखाधड़ी हुई है",
            "मुझे बहुत अधिक घबराहट और अवसाद हो रहा है",
            "कार्यस्थल पर उत्पीड़न की शिकायत करनी है"
        )
        else -> listOf(
            "My husband is physically abusing me",
            "A child is being abused / POCSO emergency",
            "Financial cyber fraud bank account compromised",
            "I'm feeling intense panic & anxiety right now",
            "Someone is following and stalking me"
        )
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(Slate950),
        topBar = {
            Surface(
                color = Slate900,
                border = androidx.compose.foundation.BorderStroke(1.dp, Slate800)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { viewModel.navigateTo(AppScreen.HOME) },
                                modifier = Modifier
                                    .size(36.dp)
                                    .testTag("chat_back_button")
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back to Home",
                                    tint = Slate200
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Confidential Support Desk",
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(BlueAccent)
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.clickable {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = ClipData.newPlainText("Chat Session ID", currentSessionId)
                                        clipboard.setPrimaryClip(clip)
                                        Toast.makeText(context, "Session ID copied securely", Toast.LENGTH_SHORT).show()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = "Secure Token",
                                        tint = SecondaryCyan,
                                        modifier = Modifier.size(11.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Session: $currentSessionId",
                                        color = Slate400,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy Session ID",
                                        tint = Slate500,
                                        modifier = Modifier.size(11.dp)
                                    )
                                }
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Quick Exit Camouflage
                            IconButton(
                                onClick = { viewModel.triggerQuickExitCamouflage() },
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Slate800)
                                    .testTag("chat_camouflage_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Calculate,
                                    contentDescription = "Camouflage Mode",
                                    tint = Slate200,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            // Wipe / Disappearing History
                            IconButton(
                                onClick = { showWipeDialog = true },
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(Slate800)
                                    .testTag("chat_wipe_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DeleteSweep,
                                    contentDescription = "Wipe Session",
                                    tint = Slate400,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Privacy Assurance Pill
                    Surface(
                        color = BluePrimary.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BluePrimary.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Shield,
                                contentDescription = "Shield",
                                tint = BluePrimaryLight,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "100% Anonymous • Zero phone or IP tracking • Cloud Synced via Firestore",
                                color = BluePrimaryLight,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                color = Color(0xFF061A14), // Deep dark forest background matching screenshot
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF0F2E24))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    // Quick Prompts
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 10.dp)
                    ) {
                        items(quickPrompts) { prompt ->
                            Surface(
                                color = Color(0xFF0F382C), // Dark forest green prompt chip
                                shape = RoundedCornerShape(20.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1B4E3E)),
                                modifier = Modifier.clickable {
                                    viewModel.setChatInputText(prompt)
                                }
                            ) {
                                Text(
                                    text = prompt,
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                                )
                            }
                        }
                    }

                    // Input & Send
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = chatInputText,
                            onValueChange = { viewModel.setChatInputText(it) },
                            placeholder = {
                                Text("Type your message...", color = Color(0xFF5A786B), fontSize = 14.sp)
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFDCE8E0), // Soft pale mint/sage background from screenshot
                                unfocusedContainerColor = Color(0xFFDCE8E0),
                                focusedBorderColor = Color(0xFF134E3F),
                                unfocusedBorderColor = Color.Transparent,
                                focusedTextColor = Color(0xFF111827),
                                unfocusedTextColor = Color(0xFF111827),
                                cursorColor = Color(0xFF0F382C)
                            ),
                            shape = RoundedCornerShape(24.dp),
                            maxLines = 4,
                            modifier = Modifier
                                .weight(1f)
                                .testTag("chat_input_field"),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                            keyboardActions = KeyboardActions(onSend = {
                                if (chatInputText.isNotBlank()) {
                                    viewModel.sendChatMessage()
                                }
                            })
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        IconButton(
                            onClick = { viewModel.sendChatMessage() },
                            enabled = chatInputText.isNotBlank() && !isSending,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(
                                    if (chatInputText.isNotBlank()) Color(0xFF0F382C) else Color(0xFF0C2B22)
                                )
                                .testTag("chat_send_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send Message",
                                tint = if (chatInputText.isNotBlank()) Color.White else Color(0xFF5A786B),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    // Emergency safety notice footer
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 4.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "In active crisis? Call ",
                            color = Color(0xFF94A3B8),
                            fontSize = 11.sp
                        )
                        Text(
                            text = "112 (Emergency)",
                            color = Color(0xFFEF4444),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                makePhoneCall(context, "112")
                            }
                        )
                        Text(
                            text = " or ",
                            color = Color(0xFF94A3B8),
                            fontSize = 11.sp
                        )
                        Text(
                            text = "14416 (Tele-MANAS)",
                            color = Color(0xFF38BDF8),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                makePhoneCall(context, "14416")
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Welcome Header Card
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Slate900),
                    shape = RoundedCornerShape(16.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Slate800),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(BluePrimary.copy(alpha = 0.2f))
                                .border(1.dp, BluePrimary.copy(alpha = 0.5f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = "Security",
                                tint = BluePrimaryLight,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Encrypted Safe Channel",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "You are chatting anonymously with empanelled counselors and AI crisis responders. Nothing links this session to your name, phone number, or device.",
                            color = Slate300,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 17.sp
                        )
                    }
                }
            }

            // Message Bubbles
            items(chatMessages, key = { it.id }) { message ->
                ChatMessageBubble(message = message)
            }

            if (isSending) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Surface(
                            color = Slate800,
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text(
                                text = "Encrypting & transmitting...",
                                color = Slate400,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Wipe Dialog Confirmation
    if (showWipeDialog) {
        AlertDialog(
            onDismissRequest = { showWipeDialog = false },
            containerColor = Slate900,
            title = {
                Text("Wipe Chat History?", color = Color.White, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "This permanently purges all local messages for this session from this device. Recommended if you are using a shared or monitored device.",
                    color = Slate300,
                    fontSize = 13.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.clearActiveChatSession()
                        showWipeDialog = false
                        Toast.makeText(context, "Session wiped cleanly", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SeverityUrgentSos)
                ) {
                    Text("Wipe & Clear", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showWipeDialog = false }) {
                    Text("Cancel", color = Slate400)
                }
            }
        )
    }
}

@Composable
fun ChatMessageBubble(
    message: AnonymousChatMessage,
    modifier: Modifier = Modifier
) {
    val isSeeker = message.senderRole == MessageSenderRole.SEEKER.name
    val isSystem = message.senderRole == MessageSenderRole.SYSTEM_SAFETY_BOT.name
    val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val formattedTime = timeFormat.format(Date(message.timestamp))

    if (isSystem || message.isSafetyAlert) {
        // System / Emergency Alert Card
        Surface(
            color = if (message.isSafetyAlert) SeverityUrgentSos.copy(alpha = 0.15f) else BluePrimary.copy(alpha = 0.12f),
            shape = RoundedCornerShape(14.dp),
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                if (message.isSafetyAlert) SeverityUrgentSos.copy(alpha = 0.5f) else BluePrimary.copy(alpha = 0.4f)
            ),
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = if (message.isSafetyAlert) Icons.Default.Warning else Icons.Default.Shield,
                    contentDescription = "Alert",
                    tint = if (message.isSafetyAlert) SeverityUrgentSos else BluePrimaryLight,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = message.senderAlias,
                        color = if (message.isSafetyAlert) SeverityUrgentSos else BluePrimaryLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = message.messageText,
                        color = Slate200,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )

                    // In-bubble 1-tap helpline call actions
                    val phoneMatches = listOf("1091", "1098", "1930", "14416", "112", "1800-599-0019", "14567", "1905", "1090").filter {
                        message.messageText.contains(it)
                    }

                    if (phoneMatches.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        val context = LocalContext.current
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            phoneMatches.take(2).forEach { num ->
                                Button(
                                    onClick = { makePhoneCall(context, num) },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (num == "112" || num == "1091" || num == "1098") Color(0xFFDC2626) else Slate800,
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Icon(imageVector = Icons.Default.Phone, contentDescription = "Call", modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Call $num", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = formattedTime,
                        color = Slate400,
                        fontSize = 10.sp
                    )
                }
            }
        }
    } else if (isSeeker) {
        // Seeker bubble (Right aligned, Royal Blue Gradient)
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.End
        ) {
            Surface(
                color = BluePrimary,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 2.dp),
                modifier = Modifier.widthIn(max = 290.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                    Text(
                        text = message.messageText,
                        color = Color.White,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.align(Alignment.End),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = formattedTime,
                            color = Slate200.copy(alpha = 0.8f),
                            fontSize = 10.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.DoneAll,
                            contentDescription = "Delivered",
                            tint = Color.White,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "You (Anonymous)",
                color = Slate400,
                fontSize = 10.sp,
                modifier = Modifier.padding(end = 4.dp)
            )
        }
    } else {
        // Companion / Counsellor chatbot bubble (Left aligned, Light Blue #E0F2FE background with high-contrast Dark Blue/Dark Slate text)
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Surface(
                color = Color(0xFFE0F2FE), // Light Blue #E0F2FE accessible container
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 2.dp, bottomEnd = 16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBAE6FD)),
                modifier = Modifier.widthIn(max = 300.dp)
            ) {
                Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.VerifiedUser,
                            contentDescription = "Verified Companion Support",
                            tint = Color(0xFF1E3A8A), // Dark Blue #1E3A8A
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = message.senderAlias,
                            color = Color(0xFF1E3A8A), // Dark Blue #1E3A8A
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = message.messageText,
                        color = Color(0xFF1F2937), // Dark Gray / Charcoal #1F2937 for optimal readability and accessibility
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = formattedTime,
                        color = Color(0xFF64748B), // Slate 500
                        fontSize = 10.sp,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Empanelled Clinical Triage",
                color = Slate400,
                fontSize = 10.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}
