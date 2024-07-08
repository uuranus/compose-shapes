package com.uuranus.variousshapes

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

@Composable
fun ParallelogramShape(
    skewed: Float,
    modifier: Modifier,
) {

    Canvas(
        modifier = modifier
    ) {

        val path = Path()

        val width = size.width
        val height = size.height

        val skewedWidth = skewed * width
        path.apply {
            moveTo(skewedWidth, 0f)

            lineTo(width, 0f)
            lineTo(width - skewedWidth, height)
            lineTo(0f, height)
            lineTo(skewedWidth, 0f)

            close()
        }

        drawPath(
            path = path,
            color = Color.Gray
        )
    }
}

@Composable
fun RhombusShape(
    modifier: Modifier,
) {

    Canvas(
        modifier = modifier
    ) {

        val path = Path()

        val width = size.width
        val height = size.height

        val centerX = width / 2
        val centerY = height / 2

        path.apply {
            moveTo(centerX, 0f)

            lineTo(width, centerY)
            lineTo(centerX, height)
            lineTo(0f, centerY)
            lineTo(centerX, 0f)

            close()
        }

        drawPath(
            path = path,
            color = Color.Gray
        )
    }

}

@Composable
fun TrapezoidShape(
    leftSkewed: Float = 0.2f,
    rightSkewed: Float = 0.2f,
    modifier: Modifier,
) {

    Canvas(
        modifier = modifier
    ) {

        val path = Path()

        val width = size.width
        val height = size.height

        val leftSkewedWidth = width * leftSkewed
        val rightSkewedWidth = width * rightSkewed

        path.apply {
            moveTo(leftSkewedWidth, 0f)

            lineTo(width - rightSkewedWidth, 0f)
            lineTo(width, height)
            lineTo(0f, height)
            lineTo(leftSkewedWidth, 0f)

            close()
        }

        drawPath(
            path = path,
            color = Color.Gray
        )
    }

}


