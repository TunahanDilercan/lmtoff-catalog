package com.lmtoff.catalog.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lmtoff.catalog.R
import com.lmtoff.catalog.ui.theme.LmtoffBlack
import com.lmtoff.catalog.ui.theme.LmtoffBlue
import com.lmtoff.catalog.ui.theme.LmtoffSilver
import com.lmtoff.catalog.ui.theme.LmtoffText

/** Ana ekranın üstündeki marka görseli ve tanıtım metni. */
@Composable
fun CatalogHeader(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(270.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.lmtoff_training_set),
            contentDescription = "LMTOFF spor ürünleri",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Black.copy(alpha = 0.35f), LmtoffBlack)
                    )
                )
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 20.dp, vertical = 26.dp)
        ) {
            Text(
                text = "LMTOFF",
                style = MaterialTheme.typography.displaySmall,
                color = LmtoffText,
                fontWeight = FontWeight.Black
            )
            Text(
                text = "SPOR ÜRÜNLERİ",
                style = MaterialTheme.typography.labelLarge,
                color = LmtoffBlue,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Antrenman ekipmanları, fitness aksesuarları ve salon stilini tamamlayan ürünler.",
                style = MaterialTheme.typography.bodyLarge,
                color = LmtoffSilver
            )
        }
    }
}
