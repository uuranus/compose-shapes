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
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

@Composable
fun RoundedParallelogramShape(
    modifier: Modifier = Modifier,
    skewed: Float = 0.2f,
    cornerRadius: Dp,
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
            moveTo(skewedWidth + cornerRadius.toPx() * halfTan, 0f)

            lineTo(width - (cornerRadius.toPx() / halfTan), 0f)

            arcTo(
                rect = Rect(
                    offset = Offset(
                        width - cornerRadius.toPx() / halfTan - cornerRadius.toPx(),
                        0f
                    ),
                    size = Size(cornerRadius.toPx() * 2, cornerRadius.toPx() * 2)
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 180f - topRightAngleDegree.toFloat(),
                forceMoveTo = false
            )

//            quadraticBezierTo(
//                x1 = x1,
//                y1 = 0f,
//                x2 =
//            )

            lineTo(
                width - skewedWidth + cornerRadius.toPx() * halfTan * cos(
                    topRightAngle
                ),
                height - cornerRadius.toPx() * halfTan * sin(topRightAngle)
            )

            arcTo(
                rect = Rect(
                    offset = Offset(
                        width - skewedWidth - cornerRadius.toPx() * halfTan - cornerRadius.toPx(),
                        height - cornerRadius.toPx() * 2
                    ),
                    size = Size(cornerRadius.toPx() * 2, cornerRadius.toPx() * 2)
                ),
                startAngleDegrees = (90f - topRightAngleDegree).toFloat(),
                sweepAngleDegrees = topRightAngleDegree.toFloat(),
                forceMoveTo = false
            )

            lineTo(cornerRadius.toPx() / halfTan, height)

            //arc
            arcTo(
                rect = Rect(
                    offset = Offset(
                        cornerRadius.toPx() * halfTan - cornerRadius.toPx(),
                        height - cornerRadius.toPx() * 2
                    ),
                    size = Size(cornerRadius.toPx() * 2, cornerRadius.toPx() * 2)
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 180f - topRightAngleDegree.toFloat(),
                forceMoveTo = false
            )

            lineTo(
                skewedWidth - cornerRadius.toPx() * halfTan * cos(topRightAngle),
                cornerRadius.toPx() * halfTan * sin(topRightAngle)
            )

            //arc

            arcTo(
                rect = Rect(
                    offset = Offset(
                        skewedWidth + cornerRadius.toPx() * halfTan - cornerRadius.toPx(),
                        0f
                    ),
                    size = Size(cornerRadius.toPx() * 2, cornerRadius.toPx() * 2)
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
