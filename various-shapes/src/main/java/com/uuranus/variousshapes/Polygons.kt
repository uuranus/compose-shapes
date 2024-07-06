package com.uuranus.variousshapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PolygonShape(
    numOfPoints: Int,
    modifier: Modifier,
) {
    Canvas(
        modifier = modifier
    ) {

        val path = Path()
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = minOf(centerX, centerY)
        val angle = 2.0 * PI / (numOfPoints)

        path.moveTo(
            centerX + (radius * sin(0.0)).toFloat(),
            centerY - (radius * cos(0.0)).toFloat()
        )

        for (i in 1 until numOfPoints) {
            val x = centerX + (radius * sin(i * angle)).toFloat()
            val y = centerY - (radius * cos(i * angle)).toFloat()
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