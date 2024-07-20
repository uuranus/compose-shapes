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
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class TrapezoidShape(
    private val startSkewed: Float,
    private val endSkewed: Float,
    private val cornerStyle: CornerStyle,
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

        val path = when (cornerStyle) {
            CornerStyle.ROUNDED -> {
                drawRoundedTrapezoidShape(
                    size = size,
                    startSkewed = startSkewed,
                    endSkewed = endSkewed,
                    topStart = topStart.toPx(size, density),
                    topEnd = topEnd.toPx(size, density),
                    bottomStart = bottomStart.toPx(size, density),
                    bottomEnd = bottomEnd.toPx(size, density),
                )
            }

            CornerStyle.INNER_ROUNDED -> {
                drawInnerRoundedTrapezoidShape(
                    size = size,
                    startSkewed = startSkewed,
                    endSkewed = endSkewed,
                    topStart = topStart.toPx(size, density),
                    topEnd = topEnd.toPx(size, density),
                    bottomStart = bottomStart.toPx(size, density),
                    bottomEnd = bottomEnd.toPx(size, density),
                )
            }

            CornerStyle.CUT -> {
                drawCutTrapezoidShape(
                    size = size,
                    startSkewed = startSkewed,
                    endSkewed = endSkewed,
                    topStart = topStart.toPx(size, density),
                    topEnd = topEnd.toPx(size, density),
                    bottomStart = bottomStart.toPx(size, density),
                    bottomEnd = bottomEnd.toPx(size, density),
                )
            }
        }

        return Outline.Generic(path)
    }
}

private fun drawRoundedTrapezoidShape(
    size: Size,
    startSkewed: Float,
    endSkewed: Float,
    topStart: Float,
    topEnd: Float,
    bottomEnd: Float,
    bottomStart: Float,
): Path {
    val path = Path()

    val width = size.width
    val height = size.height

    val startSkewedWidth = width * startSkewed
    val endSkewedWidth = width * endSkewed

    val bottomStartHalfAngle = atan(height / startSkewedWidth) / 2
    val bottomEndHalfAngle = atan(height / endSkewedWidth) / 2
    val bottomStartHalfAngleDegree = bottomStartHalfAngle.toDegree()
    val bottomEndHalfAngleDegree = bottomEndHalfAngle.toDegree()

    val topStartCircleCenter =
        Point(
            startSkewedWidth + topStart / tan(PI / 2 - bottomStartHalfAngle).toFloat(),
            topStart
        )
    val topEndCircleCenter = Point(
        width - endSkewedWidth - topEnd / tan(PI / 2 - bottomEndHalfAngle).toFloat(),
        topEnd
    )
    val bottomStartCircleCenter =
        Point(bottomStart / tan(bottomStartHalfAngle), height - bottomStart)
    val bottomEndCircleCenter = Point(
        width
                - bottomEnd / tan(bottomEndHalfAngle), height - bottomEnd
    )

    path.apply {
        arcTo(
            rect = Rect(
                offset = Offset(
                    topStartCircleCenter.x - topStart,
                    topStartCircleCenter.y - topStart
                ),
                size = Size(topStart * 2, topStart * 2),
            ),
            startAngleDegrees = -90f - 2 * bottomStartHalfAngleDegree,
            sweepAngleDegrees = 2 * bottomStartHalfAngleDegree,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    topEndCircleCenter.x - topEnd,
                    topEndCircleCenter.y - topEnd
                ),
                size = Size(topEnd * 2, topEnd * 2),
            ),
            startAngleDegrees = -90f,
            sweepAngleDegrees = 2 * bottomEndHalfAngleDegree,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    bottomEndCircleCenter.x - bottomEnd,
                    bottomEndCircleCenter.y - bottomEnd
                ),
                size = Size(bottomEnd * 2, bottomEnd * 2),
            ),
            startAngleDegrees = 2 * bottomEndHalfAngleDegree - 90f,
            sweepAngleDegrees = 180f - 2 * bottomEndHalfAngleDegree,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    bottomStartCircleCenter.x - bottomStart,
                    bottomStartCircleCenter.y - bottomStart
                ),
                size = Size(bottomStart * 2, bottomStart * 2),
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 180f - 2 * bottomStartHalfAngleDegree,
            forceMoveTo = false
        )

        close()
    }

    return path
}

private fun drawInnerRoundedTrapezoidShape(
    size: Size,
    startSkewed: Float,
    endSkewed: Float,
    topStart: Float,
    topEnd: Float,
    bottomEnd: Float,
    bottomStart: Float,
): Path {
    val path = Path()

    val width = size.width
    val height = size.height

    val startSkewedWidth = width * startSkewed
    val endSkewedWidth = width * endSkewed

    val bottomStartAngle = atan(height / startSkewedWidth)
    val bottomEndAngle = atan(height / endSkewedWidth)
    val bottomStartAngleDegree = bottomStartAngle.toDegree()
    val bottomEndAngleDegree = bottomEndAngle.toDegree()

    path.apply {
        arcTo(
            rect = Rect(
                offset = Offset(
                    startSkewedWidth - topStart,
                    -topStart
                ),
                size = Size(topStart * 2, topStart * 2),
            ),
            startAngleDegrees = 180f - bottomStartAngleDegree,
            sweepAngleDegrees = bottomStartAngleDegree - 180f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    width - endSkewedWidth - topEnd,
                    -topEnd
                ),
                size = Size(topEnd * 2, topEnd * 2),
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = bottomEndAngleDegree - 180f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    width - bottomEnd,
                    height - bottomEnd
                ),
                size = Size(bottomEnd * 2, bottomEnd * 2),
            ),
            startAngleDegrees = 180f + bottomEndAngleDegree,
            sweepAngleDegrees = -bottomEndAngleDegree,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    -bottomStart,
                    height - bottomStart
                ),
                size = Size(bottomStart * 2, bottomStart * 2),
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = -bottomStartAngleDegree,
            forceMoveTo = false
        )

        close()
    }

    return path
}

private fun drawCutTrapezoidShape(
    size: Size,
    startSkewed: Float,
    endSkewed: Float,
    topStart: Float,
    topEnd: Float,
    bottomEnd: Float,
    bottomStart: Float,
): Path {
    val path = Path()

    val width = size.width
    val height = size.height

    val startSkewedWidth = width * startSkewed
    val endSkewedWidth = width * endSkewed

    val bottomStartAngle = atan(height / startSkewedWidth)
    val bottomEndAngle = atan(height / endSkewedWidth)

    path.apply {

        moveTo(startSkewedWidth + topStart, 0f)

        lineTo(width - endSkewedWidth - topEnd, 0f)
        lineTo(width - endSkewedWidth + topEnd * cos(bottomEndAngle), topEnd * sin(bottomEndAngle))

        lineTo(width - bottomEnd * cos(bottomEndAngle), height - bottomEnd * sin(bottomEndAngle))
        lineTo(width - bottomEnd, height)

        lineTo(bottomStart, height)
        lineTo(bottomStart * cos(bottomStartAngle), height - bottomStart * sin(bottomStartAngle))

        lineTo(
            startSkewedWidth - topStart * cos(bottomStartAngle),
            topStart * sin(bottomStartAngle)
        )

        close()
    }

    return path
}

fun TrapezoidShape(
    skewed: Float,
    cornerStyle: CornerStyle = CornerStyle.ROUNDED,
    cornerSize: CornerSize = CornerSize(0.dp),
) =
    TrapezoidShape(skewed, skewed, cornerStyle, cornerSize, cornerSize, cornerSize, cornerSize)

fun TrapezoidShape(
    startSkewed: Float,
    endSkewed: Float,
    cornerStyle: CornerStyle = CornerStyle.ROUNDED,
    cornerSize: CornerSize = CornerSize(0.dp),
) =
    TrapezoidShape(startSkewed, endSkewed, cornerStyle, cornerSize, cornerSize, cornerSize, cornerSize)

fun TrapezoidShape(
    startSkewed: Float,
    endSkewed: Float,
    cornerStyle: CornerStyle = CornerStyle.ROUNDED,
    size: Dp = 0.dp,
) =
    TrapezoidShape(
        startSkewed,
        endSkewed,
        cornerStyle,
        CornerSize(size),
        CornerSize(size),
        CornerSize(size),
        CornerSize(size)
    )

fun TrapezoidShape(
    startSkewed: Float,
    endSkewed: Float,
    cornerStyle: CornerStyle = CornerStyle.ROUNDED,
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp,
) = TrapezoidShape(
    startSkewed = startSkewed,
    endSkewed = endSkewed,
    cornerStyle = cornerStyle,
    topStart = CornerSize(topStart),
    topEnd = CornerSize(topEnd),
    bottomEnd = CornerSize(bottomEnd),
    bottomStart = CornerSize(bottomStart)
)


