package com.uuranus.variousshapes

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.unit.Density
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.LayoutDirection

class PolygonShape(
    private val numOfPoints: Int,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = Path()
        val centerX = size.width / 2
        val centerY = size.height / 2
        val radius = min(centerX, centerY)

        val angle = 2.0 * PI / numOfPoints

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
        return Outline.Generic(path)
    }
}
