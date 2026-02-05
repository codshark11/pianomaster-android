package com.pianomaster.app.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.pianomaster.app.ui.theme.Primary
import com.pianomaster.app.ui.theme.PrimaryVariant
import com.pianomaster.app.ui.theme.Surface
import com.pianomaster.app.ui.theme.TextPrimary
import com.pianomaster.app.ui.theme.TextSecondary

private data class CreditPackage(val id: String, val amount: Int, val label: String)

@Composable
fun AddCreditsDialog(
    open: Boolean,
    onOpenChange: (Boolean) -> Unit,
    onAddCredits: (Int) -> Unit
) {
    if (!open) return
    val packages = listOf(
        CreditPackage("starter", 500, "Starter Pack (500)"),
        CreditPackage("popular", 1200, "Most Popular (1200)"),
        CreditPackage("value", 2500, "Best Value (2500)")
    )
    Dialog(onDismissRequest = { onOpenChange(false) }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(Surface)
                .padding(24.dp)
        ) {
            Text(
                "Add Credits",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            packages.forEach { pkg ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Primary.copy(alpha = 0.1f))
                        .border(1.dp, Primary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .clickable {
                            onAddCredits(pkg.amount)
                            onOpenChange(false)
                        }
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(pkg.label, color = TextPrimary, style = MaterialTheme.typography.titleSmall)
                    Text("${pkg.amount}", color = PrimaryVariant, style = MaterialTheme.typography.titleMedium)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { onOpenChange(false) }) {
                    Text("Cancel", color = TextSecondary)
                }
            }
        }
    }
}
