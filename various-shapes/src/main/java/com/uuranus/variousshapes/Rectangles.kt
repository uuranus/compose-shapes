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
) {

    Canvas(
        modifier = Modifier.fillMaxSize()
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

) {

    Canvas(
        modifier = Modifier.fillMaxSize()
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
    skewed: Float,
) {

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {

        val path = Path()

        val width = size.width
        val height = size.height

        val skewedWidth = width * skewed

        path.apply {
            moveTo(skewedWidth, 0f)

            lineTo(width - skewedWidth, 0f)
            lineTo(width, height)
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