package com.uuranus.variousshapes

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class ParallelogramShape(private val skewed: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path()
        val skewedWidth = skewed * size.width
        path.apply {
            moveTo(skewedWidth, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width - skewedWidth, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}

class TrapezoidShape(
    private val leftSkewed: Float,
    private val rightSkewed: Float
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path()
        val leftSkewedWidth = size.width * leftSkewed
        val rightSkewedWidth = size.width * rightSkewed
        path.apply {
            moveTo(leftSkewedWidth, 0f)
            lineTo(size.width - rightSkewedWidth, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}

