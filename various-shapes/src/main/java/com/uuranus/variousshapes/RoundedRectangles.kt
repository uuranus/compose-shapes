package com.uuranus.variousshapes

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

@Composable
fun RoundedParallelogramShape(
    modifier: Modifier = Modifier,
    skewed: Float = 0.2f,
    topStart: Dp = 16.dp,
    topEnd: Dp = 32.dp,
    bottomEnd: Dp = 16.dp,
    bottomStart: Dp = 32.dp,
) {

    Canvas(
        modifier = modifier
    ) {

        val path = Path()

        val width = size.width
        val height = size.height

        val skewedWidth = skewed * width

        val y1 = 0f
        val x2 = width - skewedWidth
        val topRightAngle = -atan(
            (height - y1) / (x2 - width)
        )

        val topRightAngleDegree = (topRightAngle * 180 / Math.PI)

        val halfTan = tan(
            (topRightAngle / 2)
        )

        path.apply {
            moveTo(skewedWidth + topStart.toPx() * halfTan, 0f)

            lineTo(width - (topEnd.toPx() / halfTan), 0f)

            arcTo(
                rect = Rect(
                    offset = Offset(
                        width - topEnd.toPx() / halfTan - topEnd.toPx(),
                        0f
                    ),
                    size = Size(topEnd.toPx() * 2, topEnd.toPx() * 2)
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 180f - topRightAngleDegree.toFloat(),
                forceMoveTo = false
            )

            lineTo(
                width - skewedWidth + bottomEnd.toPx() * halfTan * cos(
                    topRightAngle
                ),
                height - bottomEnd.toPx() * halfTan * sin(topRightAngle)
            )

            arcTo(
                rect = Rect(
                    offset = Offset(
                        width - skewedWidth - bottomEnd.toPx() * halfTan - bottomEnd.toPx(),
                        height - bottomEnd.toPx() * 2
                    ),
                    size = Size(bottomEnd.toPx() * 2, bottomEnd.toPx() * 2)
                ),
                startAngleDegrees = (90f - topRightAngleDegree).toFloat(),
                sweepAngleDegrees = topRightAngleDegree.toFloat(),
                forceMoveTo = false
            )

            lineTo(bottomStart.toPx() / halfTan, height)

            arcTo(
                rect = Rect(
                    offset = Offset(
                        bottomStart.toPx() / halfTan - bottomStart.toPx(),
                        height - bottomStart.toPx() * 2
                    ),
                    size = Size(bottomStart.toPx() * 2, bottomStart.toPx() * 2)
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 180f - topRightAngleDegree.toFloat(),
                forceMoveTo = false
            )

            lineTo(
                skewedWidth - topStart.toPx() * halfTan * cos(topRightAngle),
                topStart.toPx() * halfTan * sin(topRightAngle)
            )

            arcTo(
                rect = Rect(
                    offset = Offset(
                        skewedWidth + topStart.toPx() * halfTan - topStart.toPx(),
                        0f
                    ),
                    size = Size(topStart.toPx() * 2, topStart.toPx() * 2)
                ),
                startAngleDegrees = (270f - topRightAngleDegree).toFloat(),
                sweepAngleDegrees = topRightAngleDegree.toFloat(),
                forceMoveTo = false
            )

            close()
        }

        drawPath(
            path = path,
            color = Color.Gray
        )
    }
}
