package com.lmtoff.catalog.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lmtoff.catalog.data.formatPrice
import com.lmtoff.catalog.ui.theme.LmtoffBlack
import com.lmtoff.catalog.ui.theme.LmtoffBlue
import com.lmtoff.catalog.ui.theme.LmtoffSilver
import com.lmtoff.catalog.ui.theme.LmtoffText

/** Ana ekranın sağ üstünde duran, ürün adedi ve tutarı gösteren sepet butonu. */
@Composable
fun CartFloatingButton(
    cartCount: Int,
    cartTotal: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(58.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Black.copy(alpha = 0.72f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        CartIcon(tint = if (cartCount > 0) LmtoffBlue else LmtoffSilver)
        if (cartCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(5.dp)
                    .size(20.dp)
                    .clip(RoundedCornerShape(50))
                    .background(LmtoffBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = cartCount.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = LmtoffBlack,
                    fontWeight = FontWeight.Black
                )
            }
            Text(
                text = formatPrice(cartTotal),
                style = MaterialTheme.typography.labelSmall,
                color = LmtoffText,
                modifier = Modifier.align(Alignment.BottomCenter),
                fontWeight = FontWeight.Bold
            )
        }
    }
}
