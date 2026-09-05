package com.lmtoff.catalog.ui.components

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.lmtoff.catalog.ui.theme.LmtoffBlack
import com.lmtoff.catalog.ui.theme.LmtoffBlue
import com.lmtoff.catalog.ui.theme.LmtoffPanel
import com.lmtoff.catalog.ui.theme.LmtoffPanelLight
import com.lmtoff.catalog.ui.theme.LmtoffSilver

@Composable
fun CategoryChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = text,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = LmtoffPanel,
            labelColor = LmtoffSilver,
            selectedContainerColor = LmtoffBlue,
            selectedLabelColor = LmtoffBlack
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = LmtoffPanelLight,
            selectedBorderColor = LmtoffBlue
        )
    )
}
