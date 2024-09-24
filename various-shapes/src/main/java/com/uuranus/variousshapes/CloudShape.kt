package com.uuranus.variousshapes

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class CloudShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = Path()

        val centerX = size.width / 2
        val centerY = size.height / 2

        val width = size.width
        val height = width * 2 / 3
        val largestRadius = height / 3

        val radiusDist = with(density) {
            10.dp.toPx()
        }

        val edgeCircleRadius = largestRadius - radiusDist * 2
        val middleCircleRadius = largestRadius - radiusDist

        val centerCircleY = largestRadius / 2
        val middleCircleY = middleCircleRadius / 2

        val ellipses = listOf(
            Rect(
                0f,
                centerY - edgeCircleRadius,
                edgeCircleRadius * 2,
                centerY + edgeCircleRadius
            ),

            Rect(
                centerX - middleCircleRadius * 2,
                centerY - middleCircleY - middleCircleRadius,
                centerX,
                centerY - middleCircleY + middleCircleRadius,
            ),
            Rect(
                centerX - largestRadius,
                centerY - centerCircleY - largestRadius,
                centerX + largestRadius,
                centerY - centerCircleY + largestRadius
            ),
            Rect(
                centerX,
                centerY - middleCircleY - middleCircleRadius,
                centerX + middleCircleRadius * 2,
                centerY - middleCircleY + middleCircleRadius,
            ),

            Rect(
                width - edgeCircleRadius * 2,
                centerY - edgeCircleRadius,
                width,
                centerY + edgeCircleRadius
            ),

            Rect(
                centerX,
                centerY + middleCircleY - middleCircleRadius,
                centerX + middleCircleRadius * 2,
                centerY + middleCircleY + middleCircleRadius,
            ),
            Rect(
                centerX - largestRadius,
                centerY + centerCircleY - largestRadius,
                centerX + largestRadius,
                centerY + centerCircleY + largestRadius
            ),
            Rect(
                centerX - middleCircleRadius * 2,
                centerY + middleCircleY - middleCircleRadius,
                centerX,
                centerY + middleCircleY + middleCircleRadius,
            ),

            )

        for (ellipse in ellipses) {

            path.addOval(
                Rect(
                    centerX - middleCircleRadius * 2,
                    centerY + middleCircleY - middleCircleRadius,
                    centerX,
                    centerY + middleCircleY + middleCircleRadius,
                )
            )

        }

        return Outline.Generic(path)

    }
}
