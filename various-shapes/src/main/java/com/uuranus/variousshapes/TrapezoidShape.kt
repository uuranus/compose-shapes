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
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.tan

class TrapezoidShape(
    private val startSkewed: Float,
    private val endSkewed: Float,
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

        val startSkewedWidth = width * startSkewed
        val endSkewedWidth = width * endSkewed

        val bottomStartHalfAngle = atan(height / startSkewedWidth) / 2
        val bottomEndHalfAngle = atan(height / endSkewedWidth) / 2
        val bottomStartHalfAngleDegree = bottomStartHalfAngle.toDegree()
        val bottomEndHalfAngleDegree = bottomEndHalfAngle.toDegree()

        val topStartRadius = topStart.toPx(size, density)
        val topEndRadius = topEnd.toPx(size, density)
        val bottomStartRadius = bottomStart.toPx(size, density)
        val bottomEndRadius = bottomEnd.toPx(size, density)

        val topStartCircleCenter =
            Point(
                startSkewedWidth + topStartRadius / tan(PI / 2 - bottomStartHalfAngle).toFloat(),
                topStartRadius
            )
        val topEndCircleCenter = Point(
            width - endSkewedWidth - topEndRadius / tan(PI / 2 - bottomEndHalfAngle).toFloat(),
            topEndRadius
        )
        val bottomStartCircleCenter =
            Point(bottomStartRadius / tan(bottomStartHalfAngle), height - bottomStartRadius)
        val bottomEndCircleCenter = Point(
            width
                    - bottomEndRadius / tan(bottomEndHalfAngle), height - bottomEndRadius
        )

        path.apply {
            arcTo(
                rect = Rect(
                    offset = Offset(
                        topStartCircleCenter.x - topStartRadius,
                        topStartCircleCenter.y - topStartRadius
                    ),
                    size = Size(topStartRadius * 2, topStartRadius * 2),
                ),
                startAngleDegrees = -90f - 2 * bottomStartHalfAngleDegree,
                sweepAngleDegrees = 2 * bottomStartHalfAngleDegree,
                forceMoveTo = false
            )

            arcTo(
                rect = Rect(
                    offset = Offset(
                        topEndCircleCenter.x - topEndRadius,
                        topEndCircleCenter.y - topEndRadius
                    ),
                    size = Size(topEndRadius * 2, topEndRadius * 2),
                ),
                startAngleDegrees = -90f,
                sweepAngleDegrees = 2 * bottomEndHalfAngleDegree,
                forceMoveTo = false
            )

            arcTo(
                rect = Rect(
                    offset = Offset(
                        bottomEndCircleCenter.x - bottomEndRadius,
                        bottomEndCircleCenter.y - bottomEndRadius
                    ),
                    size = Size(bottomEndRadius * 2, bottomEndRadius * 2),
                ),
                startAngleDegrees = 2 * bottomEndHalfAngleDegree - 90f,
                sweepAngleDegrees = 180f - 2 * bottomEndHalfAngleDegree,
                forceMoveTo = false
            )

            arcTo(
                rect = Rect(
                    offset = Offset(
                        bottomStartCircleCenter.x - bottomStartRadius,
                        bottomStartCircleCenter.y - bottomStartRadius
                    ),
                    size = Size(bottomStartRadius * 2, bottomStartRadius * 2),
                ),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 180f - 2 * bottomStartHalfAngleDegree,
                forceMoveTo = false
            )

            close()
        }
        return Outline.Generic(path)
    }
}

fun TrapezoidShape(skewed: Float, cornerSize: CornerSize) =
    TrapezoidShape(skewed, skewed, cornerSize, cornerSize, cornerSize, cornerSize)

fun TrapezoidShape(startSkewed: Float, endSkewed: Float, size: Dp) =
    TrapezoidShape(
        startSkewed,
        endSkewed,
        CornerSize(size),
        CornerSize(size),
        CornerSize(size),
        CornerSize(size)
    )

fun TrapezoidShape(
    startSkewed: Float,
    endSkewed: Float,
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp,
) = TrapezoidShape(
    startSkewed = startSkewed,
    endSkewed = endSkewed,
    topStart = CornerSize(topStart),
    topEnd = CornerSize(topEnd),
    bottomEnd = CornerSize(bottomEnd),
    bottomStart = CornerSize(bottomStart)
)


