package com.uuranus.variousshapes

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
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

class RhombusShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path()
        val centerX = size.width / 2
        val centerY = size.height / 2
        path.apply {
            moveTo(centerX, 0f)
            lineTo(size.width, centerY)
            lineTo(centerX, size.height)
            lineTo(0f, centerY)
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

