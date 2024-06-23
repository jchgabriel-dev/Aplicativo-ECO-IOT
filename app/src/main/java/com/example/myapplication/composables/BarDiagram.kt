package com.example.myapplication.composables

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.round
import kotlin.math.roundToInt
import androidx.compose.foundation.layout.*


@Preview(
    showSystemUi = true, showBackground = true
)
@Composable
fun PreviewLinearChart() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        LinearChart(
            data = listOf(
                Pair(6, 50.0), Pair(7, 60.0), Pair(8, 30.0)
            ), modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .align(CenterHorizontally)
        )

    }

}


@Composable
fun LinearChart(
    modifier: Modifier = Modifier,
    data: List<Pair<Int, Double>> = emptyList(),
) {

    val spacing = 100f
    val graphColor = Color.DarkGray
    val transparentGraphColor = remember { graphColor.copy(alpha = 0.5f) }
    val upperValue = 100 // Valor máximo en el eje Y
    val lowerValue = 0 // Valor mínimo en el eje Y
    val density = LocalDensity.current

    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val spacePerHour = (size.width - spacing) / data.size
        (data.indices).forEach { i ->
            val hour = data[i].first
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(), spacing + i * spacePerHour, size.height, textPaint
                )
            }
        }

        // Dibuja los valores en el eje Y de manera estática
        val yValues = listOf(0, 25, 50, 75, 100)
        yValues.forEachIndexed { index, yValue ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    yValue.toString(),
                    30f,
                    size.height - spacing - index * size.height / 4f,
                    textPaint
                )
            }
        }

        val strokePath = Path().apply {
            val height = size.height
            data.indices.forEach { i ->
                val info = data[i]
                val ratio = (info.second - lowerValue) / (upperValue - lowerValue)

                val x1 = spacing + i * spacePerHour
                val y1 = height - spacing - (ratio * height).toFloat()

                if (i == 0) {
                    moveTo(x1, y1)
                }
                lineTo(x1, y1)
            }
        }

        drawPath(
            path = strokePath, color = graphColor, style = Stroke(
                width = 3.dp.toPx(), cap = StrokeCap.Round
            )
        )

        val fillPath = android.graphics.Path(strokePath.asAndroidPath()).asComposePath().apply {
            lineTo(size.width - spacePerHour, size.height - spacing)
            lineTo(spacing, size.height - spacing)
            close()
        }

        drawPath(
            path = fillPath, brush = Brush.verticalGradient(
                colors = listOf(
                    transparentGraphColor, Color.Transparent
                ), endY = size.height - spacing
            )
        )

    }
}