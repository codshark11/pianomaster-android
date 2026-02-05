package com.pianomaster.app.ui.components.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.pianomaster.app.ui.theme.Primary
import com.pianomaster.app.ui.theme.Surface
import com.pianomaster.app.ui.theme.TextPrimary
import com.pianomaster.app.ui.theme.TextSecondary

@Composable
fun AuthDialog(
    open: Boolean,
    onOpenChange: (Boolean) -> Unit,
    onLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    if (!open) return
    AlertDialog(
        onDismissRequest = { onOpenChange(false) },
        title = { Text("Sign In", color = TextPrimary) },
        text = {
            Column {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = TextSecondary.copy(alpha = 0.5f),
                        focusedLabelColor = Primary,
                        unfocusedLabelColor = TextSecondary
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary,
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = TextSecondary.copy(alpha = 0.5f),
                        focusedLabelColor = Primary,
                        unfocusedLabelColor = TextSecondary
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onLogin()
                    onOpenChange(false)
                }
            ) {
                Text("Sign In", color = Primary)
            }
        },
        dismissButton = {
            TextButton(onClick = { onOpenChange(false) }) {
                Text("Cancel", color = TextSecondary)
            }
        }
    )
}
