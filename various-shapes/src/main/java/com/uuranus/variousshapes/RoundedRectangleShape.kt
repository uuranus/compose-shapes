package com.uuranus.variousshapes

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class RoundedParallelogramShape(
    private val skewed: Float = 0.2f,
    private val topStart: CornerSize,
    private val topEnd: CornerSize,
    private val bottomEnd: CornerSize,
    private val bottomStart: CornerSize,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = Path()
        val width = size.width
        val height = size.height
        val skewedWidth = skewed * width
        val y1 = 0f
        val x2 = width - skewedWidth
        val topRightAngle = -atan((height - y1) / (x2 - width))
        val topRightAngleDegree = (topRightAngle * 180 / Math.PI)
        val halfTan = tan((topRightAngle / 2))

        path.apply {
            moveTo(skewedWidth + topStart.toPx(size, density) * halfTan, 0f)
            lineTo(width - (topEnd.toPx(size, density) / halfTan), 0f)
            arcTo(
                rect = Rect(
                    offset = Offset(
                        width - topEnd.toPx(size, density) / halfTan - topEnd.toPx(
                            size,
                            density
                        ), 0f
                    ),
                    size = Size(topEnd.toPx(size, density) * 2, topEnd.toPx(size, density) * 2)
                ),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 180f - topRightAngleDegree.toFloat(),
                forceMoveTo = false
            )


            lineTo(
                width - skewedWidth + bottomEnd.toPx(size, density) * halfTan * cos(topRightAngle),
                height - bottomEnd.toPx(size, density) * halfTan * sin(topRightAngle)
            )

            arcTo(
                rect = Rect(
                    offset = Offset(
                        width - skewedWidth - bottomEnd.toPx(
                            size,
                            density
                        ) * halfTan - bottomEnd.toPx(size, density),
                        height - bottomEnd.toPx(size, density) * 2
                    ),
                    size = Size(
                        bottomEnd.toPx(size, density) * 2,
                        bottomEnd.toPx(size, density) * 2
                    )
                ),
                startAngleDegrees = (90f - topRightAngleDegree).toFloat(),
                sweepAngleDegrees = topRightAngleDegree.toFloat(),
                forceMoveTo = false
            )
            lineTo(bottomStart.toPx(size, density) / halfTan, height)
            arcTo(
                rect = Rect(
                    offset = Offset(
                        bottomStart.toPx(size, density) / halfTan - bottomStart.toPx(
                            size,
                            density
                        ), height - bottomStart.toPx(size, density) * 2
                    ),
                    size = Size(
                        bottomStart.toPx(size, density) * 2,
                        bottomStart.toPx(size, density) * 2
                    )
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 180f - topRightAngleDegree.toFloat(),
                forceMoveTo = false
            )
            lineTo(
                skewedWidth - topStart.toPx(size, density) * halfTan * cos(topRightAngle),
                topStart.toPx(size, density) * halfTan * sin(topRightAngle)
            )
            arcTo(
                rect = Rect(
                    offset = Offset(
                        skewedWidth + topStart.toPx(
                            size,
                            density
                        ) * halfTan - topStart.toPx(size, density), 0f
                    ),
                    size = Size(topStart.toPx(size, density) * 2, topStart.toPx(size, density) * 2)
                ),
                startAngleDegrees = (270f - topRightAngleDegree).toFloat(),
                sweepAngleDegrees = topRightAngleDegree.toFloat(),
                forceMoveTo = false
            )
            close()
        }

        return Outline.Generic(path)
    }
}



fun RoundedParallelogramShape(skewed: Float, cornerSize: CornerSize) =
    RoundedParallelogramShape(skewed, cornerSize, cornerSize, cornerSize, cornerSize)

fun RoundedParallelogramShape(skewed: Float, size: Dp) =
    RoundedParallelogramShape(skewed, CornerSize(size))

fun RoundedParallelogramShape(
    skewed: Float,
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp,
) = RoundedParallelogramShape(
    skewed = skewed,
    topStart = CornerSize(topStart),
    topEnd = CornerSize(topEnd),
    bottomEnd = CornerSize(bottomEnd),
    bottomStart = CornerSize(bottomStart)
)

