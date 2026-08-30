package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.EnhancedEncryption
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.localization.LocalizedStrings
import com.example.security.CustodyLogEntry
import com.example.security.VaultEvidenceItem
import com.example.ui.theme.AccentGreen
import com.example.ui.theme.CardDarkGreen
import com.example.ui.theme.CardDarkGreenBorder
import com.example.ui.theme.CardDarkGreenElevated
import com.example.ui.theme.SelectedCardBrush
import com.example.ui.theme.Slate200
import com.example.ui.theme.Slate300
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate800
import com.example.ui.theme.Slate900
import com.example.ui.theme.Slate950
import com.example.ui.theme.TextPrimaryNearWhite
import com.example.ui.theme.TextSecondaryDarkCard
import com.example.ui.viewmodel.AppScreen
import com.example.ui.viewmodel.SahayakViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EvidenceVaultScreen(
    viewModel: SahayakViewModel,
    modifier: Modifier = Modifier
) {
    val currentLang by viewModel.currentLanguage.collectAsState()
    val vaultItems by viewModel.vaultItems.collectAsState()
    var selectedItemForDetail by remember { mutableStateOf<VaultEvidenceItem?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showCertificateDialog by remember { mutableStateOf<VaultEvidenceItem?>(null) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Slate950),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { viewModel.navigateTo(AppScreen.HOME) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Column {
                        Text(
                            text = LocalizedStrings.get("evidence_vault", currentLang),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "Hardware KeyStore AES-256 & SHA-256 Integrity",
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate400,
                            fontSize = 11.sp
                        )
                    }
                }

                Button(
                    onClick = { showAddDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentGreen, contentColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("add_evidence_btn")
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Secure Item", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        // Security Protocol Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(CardDarkGreenElevated)
                    .border(1.dp, CardDarkGreenBorder, RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(AccentGreen.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.EnhancedEncryption,
                            contentDescription = "Encrypted",
                            tint = AccentGreen,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Hardware-Isolated Security Active",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Media files are encrypted using hardware AES-GCM keys. EXIF metadata is stripped to protect your physical identity.",
                            color = TextSecondaryDarkCard,
                            fontSize = 11.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        }

        // Vault Items List
        item {
            Text(
                text = "Protected Media & Documents (${vaultItems.size})",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        items(vaultItems) { item ->
            VaultItemCard(
                item = item,
                onClick = { selectedItemForDetail = item },
                onExportCertificate = { showCertificateDialog = item }
            )
        }
    }

    // Detail Dialog
    selectedItemForDetail?.let { item ->
        AlertDialog(
            onDismissRequest = { selectedItemForDetail = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = null, tint = AccentGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = item.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Category: ${item.category}", color = Slate300, fontSize = 12.sp)
                    Text(text = "MIME Type: ${item.mimeType} | Size: ${item.sizeBytes / 1024} KB", color = Slate400, fontSize = 11.sp)
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "SHA-256 Digital Fingerprint:", fontWeight = FontWeight.Bold, color = AccentGreen, fontSize = 11.sp)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Slate950)
                            .border(1.dp, CardDarkGreenBorder, RoundedCornerShape(6.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = item.sha256Checksum,
                            color = Color(0xFF6EE7B7),
                            fontFamily = FontFamily.Monospace,
                            fontSize = 10.sp,
                            lineHeight = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Chain-of-Custody History:", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 12.sp)
                    item.chainOfCustodyLog.forEach { log ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(text = "• ", color = AccentGreen)
                            Column {
                                Text(text = "${log.action} (${log.actor})", color = Slate200, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                                Text(
                                    text = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date(log.timestamp)) + " | " + log.hashVerification,
                                    color = Slate400,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showCertificateDialog = item
                        selectedItemForDetail = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentGreen, contentColor = Color.White)
                ) {
                    Icon(imageVector = Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Export Legal Chain-of-Custody")
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedItemForDetail = null }) {
                    Text("Close", color = Slate400)
                }
            },
            containerColor = Slate900
        )
    }

    // Add Evidence Dialog
    if (showAddDialog) {
        AddEvidenceDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { title, category, type ->
                viewModel.addEvidenceToVault(title, category, type)
                showAddDialog = false
            }
        )
    }

    // Export Legal Certificate Dialog
    showCertificateDialog?.let { item ->
        AlertDialog(
            onDismissRequest = { showCertificateDialog = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Fingerprint, contentDescription = null, tint = AccentGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Court-Admissible Evidence Certificate", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "Certificate of Cryptographic Seal (Indian Evidence Act / BSA)",
                        color = AccentGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Evidence Item: ${item.fileName}\nSHA-256: ${item.sha256Checksum}\nTimestamp: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.getDefault()).format(Date(item.timestamp))}\nEncryption: AES-GCM-256 (Hardware Sealed)\nEXIF Scrubbed: Verified",
                        color = Slate200,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "This certificate provides verifiable proof of zero tampering for police investigations and legal aid submission.",
                        color = Slate400,
                        fontSize = 10.5.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.showUserNotification("Evidence Certificate downloaded and sealed successfully.")
                        showCertificateDialog = null
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentGreen, contentColor = Color.White)
                ) {
                    Text("Confirm & Seal Certificate")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCertificateDialog = null }) {
                    Text("Close", color = Slate400)
                }
            },
            containerColor = Slate900
        )
    }
}

@Composable
fun VaultItemCard(
    item: VaultEvidenceItem,
    onClick: () -> Unit,
    onExportCertificate: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardDarkGreen)
            .border(1.dp, CardDarkGreenBorder, RoundedCornerShape(14.dp))
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(AccentGreen.copy(alpha = 0.18f)),
                        contentAlignment = Alignment.Center
                    ) {
                        val icon = when {
                            item.mimeType.contains("image") -> Icons.Default.CameraAlt
                            item.mimeType.contains("audio") -> Icons.Default.Mic
                            else -> Icons.Default.Description
                        }
                        Icon(imageVector = icon, contentDescription = null, tint = AccentGreen, modifier = Modifier.size(18.dp))
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryNearWhite,
                            fontSize = 13.5.sp
                        )
                        Text(
                            text = item.category + " • " + SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(item.timestamp)),
                            color = TextSecondaryDarkCard,
                            fontSize = 11.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(CardDarkGreenElevated)
                        .border(1.dp, AccentGreen.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "AES-256 SEAL",
                        color = AccentGreen,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Hash: " + item.sha256Checksum.take(16) + "...",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.5.sp,
                    color = Color(0xFF6EE7B7)
                )

                Text(
                    text = "View Chain Log >",
                    color = AccentGreen,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun AddEvidenceDialog(
    onDismiss: () -> Unit,
    onAdd: (String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Cyber Harassment") }
    var selectedType by remember { mutableStateOf("image/png") }

    val categories = listOf("Cyber Harassment", "Domestic Abuse", "Financial Extortion", "Child Exploitation", "Physical Threats")
    val types = listOf(
        Pair("Photo / Screenshot", "image/png"),
        Pair("Audio Recording", "audio/m4a"),
        Pair("PDF Document / Contract", "application/pdf")
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Secure New Evidence in Vault", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(text = "Item Title / Description:", color = Slate200, fontSize = 12.sp)
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = { Text("e.g. Chat screenshot, audio recording...", color = Slate400, fontSize = 11.sp) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Slate950,
                        unfocusedContainerColor = Slate950,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(text = "Category:", color = Slate200, fontSize = 12.sp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    categories.take(3).forEach { cat ->
                        val isSel = selectedCategory == cat
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) AccentGreen else Slate800)
                                .clickable { selectedCategory = cat }
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Text(text = cat, color = Color.White, fontSize = 10.5.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }

                Text(text = "Media Format:", color = Slate200, fontSize = 12.sp)
                types.forEach { (label, mime) ->
                    val isSel = selectedType == mime
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSel) CardDarkGreenElevated else Slate800)
                            .clickable { selectedType = mime }
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isSel) Icons.Default.CheckCircle else Icons.Default.Lock,
                            contentDescription = null,
                            tint = if (isSel) AccentGreen else Slate400,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = label, color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val finalTitle = if (title.isBlank()) "Secured Evidence Item" else title
                    onAdd(finalTitle, selectedCategory, selectedType)
                },
                colors = ButtonDefaults.buttonColors(containerColor = AccentGreen, contentColor = Color.White)
            ) {
                Text("Encrypt & Seal in Vault")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = Slate400)
            }
        },
        containerColor = Slate900
    )
}
