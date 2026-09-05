package com.lmtoff.catalog.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.lmtoff.catalog.data.ProductColorOption
import com.lmtoff.catalog.ui.theme.LmtoffBlue
import com.lmtoff.catalog.ui.theme.LmtoffMuted
import com.lmtoff.catalog.ui.theme.LmtoffPanel
import com.lmtoff.catalog.ui.theme.LmtoffPanelLight
import com.lmtoff.catalog.ui.theme.LmtoffText

/** Ürün detayındaki küçük görsel önizlemesi. */
@Composable
fun ProductImageThumbnail(
    imageRes: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(76.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (selected) LmtoffBlue else LmtoffPanelLight)
            .padding(if (selected) 2.dp else 1.dp)
            .clip(RoundedCornerShape(7.dp))
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Ürün görseli",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

/** Ürünün renk seçeneklerini gösteren seçici. */
@Composable
fun ProductColorSelector(
    options: List<ProductColorOption>,
    selectedOption: ProductColorOption?,
    onSelect: (ProductColorOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Renk Seçimi",
            style = MaterialTheme.typography.titleMedium,
            color = LmtoffText,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            options.forEach { option ->
                val selected = selectedOption?.label == option.label
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (selected) LmtoffBlue else LmtoffPanelLight)
                        .padding(1.dp)
                        .clip(RoundedCornerShape(7.dp))
                        .background(LmtoffPanel)
                        .clickable { onSelect(option) }
                        .padding(horizontal = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Color(option.swatch))
                        )
                        Text(
                            text = option.label,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (selected) LmtoffText else LmtoffMuted,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}
