package com.uuranus.variousshapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StarShape(innerRadiusRatio: Float, modifier: Modifier) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
    ) {

        val path = Path()
        val centerX = size.width / 2
        val centerY = size.height / 2
        val outerRadius = minOf(centerX, centerY)
        val innerRadius = outerRadius * innerRadiusRatio
        val numPoints = 5
        val angle = 2.0 * PI / (numPoints * 2)

        path.moveTo(
            centerX + (outerRadius * sin(0.0)).toFloat(),
            centerY - (outerRadius * cos(0.0)).toFloat()
        )

        for (i in 1 until numPoints * 2) {
            val r = if (i % 2 == 0) outerRadius else innerRadius
            val x = centerX + (r * sin(i * angle)).toFloat()
            val y = centerY - (r * cos(i * angle)).toFloat()
            path.lineTo(x, y)
        }

        path.close()


        drawPath(
            path = path,
            color = Color.Black,
            style = Fill
        )

    }

}
