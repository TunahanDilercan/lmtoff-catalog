package com.lmtoff.catalog.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

/** Canvas ile çizilen sepet ikonu; ayrı bir vektör kaynağına ihtiyaç duymaz. */
@Composable
fun CartIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(28.dp)) {
        val stroke = 3.4f
        drawLine(
            color = tint,
            start = Offset(size.width * 0.18f, size.height * 0.25f),
            end = Offset(size.width * 0.28f, size.height * 0.25f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        drawLine(
            color = tint,
            start = Offset(size.width * 0.28f, size.height * 0.25f),
            end = Offset(size.width * 0.38f, size.height * 0.62f),
            strokeWidth = stroke,
            cap = StrokeCap.Round
        )
        drawRoundRect(
            color = tint,
            topLeft = Offset(size.width * 0.36f, size.height * 0.34f),
            size = Size(size.width * 0.44f, size.height * 0.24f),
            cornerRadius = CornerRadius(5f, 5f),
            style = Stroke(width = stroke)
        )
        drawCircle(
            color = tint,
            radius = 3.6f,
            center = Offset(size.width * 0.43f, size.height * 0.76f)
        )
        drawCircle(
            color = tint,
            radius = 3.6f,
            center = Offset(size.width * 0.73f, size.height * 0.76f)
        )
    }
}
