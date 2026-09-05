package com.lmtoff.catalog.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.lmtoff.catalog.R
import com.lmtoff.catalog.data.formatPrice
import com.lmtoff.catalog.data.priceValue
import com.lmtoff.catalog.ui.cart.CartLine
import com.lmtoff.catalog.ui.components.CartIcon
import com.lmtoff.catalog.ui.components.QuantityButton
import com.lmtoff.catalog.ui.theme.LmtoffBlack
import com.lmtoff.catalog.ui.theme.LmtoffBlue
import com.lmtoff.catalog.ui.theme.LmtoffMuted
import com.lmtoff.catalog.ui.theme.LmtoffPanel
import com.lmtoff.catalog.ui.theme.LmtoffPanelLight
import com.lmtoff.catalog.ui.theme.LmtoffText

/** Sepet ekranı: satırlar, adet düzenleme ve WhatsApp sipariş butonu. */
@Composable
fun CartScreen(
    cartLines: List<CartLine>,
    total: Int,
    onBack: () -> Unit,
    onUpdateQuantity: (CartLine, Int) -> Unit,
    onCheckout: (List<CartLine>) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = LmtoffBlack,
        bottomBar = {
            if (cartLines.isNotEmpty()) {
                CartCheckoutBar(
                    total = total,
                    onCheckout = { onCheckout(cartLines) }
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .statusBarsPadding(),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { CartHeader(onBack = onBack) }

            if (cartLines.isEmpty()) {
                item { EmptyCartState(onBack = onBack) }
            } else {
                items(cartLines) { line ->
                    CartLineCard(
                        line = line,
                        onDecrease = { onUpdateQuantity(line, line.quantity - 1) },
                        onIncrease = { onUpdateQuantity(line, line.quantity + 1) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CartCheckoutBar(
    total: Int,
    onCheckout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .background(LmtoffBlack)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Toplam",
                style = MaterialTheme.typography.titleMedium,
                color = LmtoffMuted
            )
            Text(
                text = formatPrice(total),
                style = MaterialTheme.typography.headlineSmall,
                color = LmtoffText,
                fontWeight = FontWeight.Black
            )
        }
        Button(
            onClick = onCheckout,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = LmtoffBlue,
                contentColor = LmtoffBlack
            )
        ) {
            Text(text = "WhatsApp Siparişini Ver", fontWeight = FontWeight.Black)
        }
    }
}

@Composable
private fun CartHeader(onBack: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(LmtoffPanel)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "Geri",
                tint = LmtoffText
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = "Sepet",
                style = MaterialTheme.typography.headlineMedium,
                color = LmtoffText,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "Sepetini kontrol et, sonra WhatsApp siparişini ver.",
                style = MaterialTheme.typography.bodyMedium,
                color = LmtoffMuted
            )
        }
    }
}

@Composable
private fun EmptyCartState(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(360.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF151922), LmtoffPanel, LmtoffBlack)
                )
            )
            .padding(22.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black.copy(alpha = 0.55f)),
                contentAlignment = Alignment.Center
            ) {
                CartIcon(tint = LmtoffBlue)
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Sepet Hazır Değil",
                style = MaterialTheme.typography.titleLarge,
                color = LmtoffText,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Ürününü ve rengini seç, sepetini kontrol et, sonra WhatsApp siparişini ver.",
                style = MaterialTheme.typography.bodyMedium,
                color = LmtoffMuted
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LmtoffBlue,
                    contentColor = LmtoffBlack
                )
            ) {
                Text(text = "Ürünlere Dön", fontWeight = FontWeight.Black)
            }
        }
    }
}

@Composable
private fun CartLineCard(
    line: CartLine,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = LmtoffPanel),
        border = BorderStroke(1.dp, LmtoffPanelLight)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = line.colorOption?.imageRes ?: line.product.imageRes),
                    contentDescription = line.product.name,
                    modifier = Modifier
                        .size(92.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = line.product.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = LmtoffBlue,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = line.colorOption?.let { "${line.product.name} (${it.label})" }
                            ?: line.product.name,
                        style = MaterialTheme.typography.titleSmall,
                        color = LmtoffText,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${line.product.price} x ${line.quantity}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = LmtoffMuted
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.2f))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatPrice(line.product.priceValue * line.quantity),
                    style = MaterialTheme.typography.titleMedium,
                    color = LmtoffBlue,
                    fontWeight = FontWeight.Black
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    QuantityButton(text = "-", onClick = onDecrease)
                    Box(
                        modifier = Modifier
                            .width(44.dp)
                            .height(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(LmtoffPanelLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = line.quantity.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = LmtoffText,
                            fontWeight = FontWeight.Black
                        )
                    }
                    QuantityButton(text = "+", onClick = onIncrease)
                }
            }
        }
    }
}
