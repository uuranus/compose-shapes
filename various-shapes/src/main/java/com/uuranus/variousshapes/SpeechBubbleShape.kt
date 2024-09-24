package com.uuranus.variousshapes

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

enum class PointerPosition {
    Top,
    Start,
    End,
    Bottom
}

class SpeechBubbleShape(
    private val topStart: CornerSize,
    private val topEnd: CornerSize,
    private val bottomEnd: CornerSize,
    private val bottomStart: CornerSize,
    private val pointerWidthRatio: Float = 0.2f,
    private val pointerHeightRatio: Float = 0.2f,
    private val pointerPosition: PointerPosition = PointerPosition.Bottom,
    /*@FloatRange(from = -1.0, to = 1.0)*/
    private val pointerSkewed: Float = 0f,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {

        val path = when (pointerPosition) {
            PointerPosition.Top -> drawTopPointerSpeechBubble(
                size,
                layoutDirection,
                density,
                topStart,
                topEnd,
                bottomStart,
                bottomEnd,
                pointerWidthRatio,
                pointerHeightRatio,
                pointerSkewed
            )

            PointerPosition.Start -> drawStartPointerSpeechBubble(
                size,
                layoutDirection,
                density,
                topStart,
                topEnd,
                bottomStart,
                bottomEnd,
                pointerWidthRatio,
                pointerHeightRatio,
                pointerSkewed
            )

            PointerPosition.End -> drawEndPointerSpeechBubble(
                size,
                layoutDirection,
                density,
                topStart,
                topEnd,
                bottomStart,
                bottomEnd,
                pointerWidthRatio,
                pointerHeightRatio,
                pointerSkewed
            )

            PointerPosition.Bottom -> drawBottomPointerSpeechBubble(
                size,
                layoutDirection,
                density,
                topStart,
                topEnd,
                bottomStart,
                bottomEnd,
                pointerWidthRatio,
                pointerHeightRatio,
                pointerSkewed
            )
        }

        return Outline.Generic(path)
    }

}

private fun drawTopPointerSpeechBubble(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density,
    topStart: CornerSize,
    topEnd: CornerSize,
    bottomEnd: CornerSize,
    bottomStart: CornerSize,
    pointerWidthRatio: Float,
    pointerHeightRatio: Float,
    skewed: Float,
): Path {
    val path = Path()

    val pointerHeight = size.height * pointerHeightRatio
    val drawStartPointerHeight = pointerHeight * 1.1f
    val pointerWidth = size.width * pointerWidthRatio

    val withoutPointerSize = size.copy(height = size.height - pointerHeight)

    val topStartPx = if (layoutDirection == LayoutDirection.Ltr) topStart.toPx(
        withoutPointerSize,
        density
    ) else topEnd.toPx(size, density)

    val topEndPx = if (layoutDirection == LayoutDirection.Ltr) topEnd.toPx(
        withoutPointerSize,
        density
    ) else topStart.toPx(size, density)

    val bottomStartPx = if (layoutDirection == LayoutDirection.Ltr) bottomStart.toPx(
        withoutPointerSize,
        density
    ) else bottomEnd.toPx(size, density)

    val bottomEndPx = if (layoutDirection == LayoutDirection.Ltr) bottomEnd.toPx(
        withoutPointerSize,
        density
    ) else bottomStart.toPx(size, density)

    path.apply {
        arcTo(
            rect = Rect(
                offset = Offset(0f, pointerHeight),
                size = Size(
                    topStartPx * 2, topStartPx * 2
                )
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(size.width - topEndPx * 2, pointerHeight),
                size = Size(
                    topEndPx * 2, topEndPx * 2
                )
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    size.width - bottomEndPx * 2,
                    size.height - bottomEndPx * 2
                ),
                size = Size(
                    bottomEndPx * 2, bottomEndPx * 2
                )
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(0f, size.height - bottomStartPx * 2),
                size = Size(
                    bottomStartPx * 2, bottomStartPx * 2
                )
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
    }

    val startPosition = Offset(size.width / 2 - pointerWidth / 2, drawStartPointerHeight)

    val endPosition = Offset(size.width / 2 + pointerWidth / 2, drawStartPointerHeight)

    val pointPosition = if (layoutDirection == LayoutDirection.Ltr) {
        Offset(size.width / 2 + pointerWidth * skewed, 0f)
    } else {
        Offset(size.width / 2 - pointerWidth * skewed, 0f)
    }

    path.apply {
        op(
            path1 = this,
            path2 = Path().apply {
                moveTo(
                    startPosition.x,
                    startPosition.y
                )
                lineTo(
                    pointPosition.x,
                    pointPosition.y
                )
                lineTo(
                    endPosition.x,
                    endPosition.y
                )
            },
            operation = PathOperation.Union
        )
    }

    return path
}

private fun drawStartPointerSpeechBubble(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density,
    topStart: CornerSize,
    topEnd: CornerSize,
    bottomEnd: CornerSize,
    bottomStart: CornerSize,
    pointerWidthRatio: Float,
    pointerHeightRatio: Float,
    skewed: Float,
): Path {
    val path = Path()

    val pointerHeight = size.width * pointerWidthRatio
    val drawStartPointerHeight = pointerHeight * 1.1f
    val pointerWidth = size.height * pointerHeightRatio

    val withoutPointerSize = size.copy(width = size.width - pointerHeight)

    val topStartPx = if (layoutDirection == LayoutDirection.Ltr) topStart.toPx(
        withoutPointerSize,
        density
    ) else topEnd.toPx(size, density)

    val topEndPx = if (layoutDirection == LayoutDirection.Ltr) topEnd.toPx(
        withoutPointerSize,
        density
    ) else topStart.toPx(size, density)

    val bottomStartPx = if (layoutDirection == LayoutDirection.Ltr) bottomStart.toPx(
        withoutPointerSize,
        density
    ) else bottomEnd.toPx(size, density)

    val bottomEndPx = if (layoutDirection == LayoutDirection.Ltr) bottomEnd.toPx(
        withoutPointerSize,
        density
    ) else bottomStart.toPx(size, density)

    path.apply {
        arcTo(
            rect = Rect(
                offset = Offset(pointerHeight, 0f),
                size = Size(
                    topStartPx * 2, topStartPx * 2
                )
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(size.width - topEndPx * 2, 0f),
                size = Size(
                    topEndPx * 2, topEndPx * 2
                )
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    size.width - bottomEndPx * 2,
                    size.height - bottomEndPx * 2
                ),
                size = Size(
                    bottomEndPx * 2, bottomEndPx * 2
                )
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    pointerHeight,
                    size.height - bottomStartPx * 2
                ),
                size = Size(
                    bottomStartPx * 2, bottomStartPx * 2
                )
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
    }

    val startPosition = Offset(drawStartPointerHeight, size.height / 2 - pointerWidth / 2)

    val endPosition = Offset(drawStartPointerHeight, size.height / 2 + pointerWidth / 2)

    val pointPosition = if (layoutDirection == LayoutDirection.Ltr) {
        Offset(0f, size.height / 2 + pointerWidth * skewed)
    } else {
        Offset(0f, size.height / 2 - pointerWidth * skewed)
    }

    path.apply {
        op(
            path1 = this,
            path2 = Path().apply {
                moveTo(
                    startPosition.x,
                    startPosition.y
                )
                lineTo(
                    pointPosition.x,
                    pointPosition.y
                )
                lineTo(
                    endPosition.x,
                    endPosition.y
                )
            },
            operation = PathOperation.Union
        )
    }

    return path
}

private fun drawEndPointerSpeechBubble(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density,
    topStart: CornerSize,
    topEnd: CornerSize,
    bottomEnd: CornerSize,
    bottomStart: CornerSize,
    pointerWidthRatio: Float,
    pointerHeightRatio: Float,
    skewed: Float,
): Path {
    val path = Path()

    val pointerHeight = size.width * pointerHeightRatio
    val drawPointerHeight = pointerHeight * 1.1f
    val pointerWidth = size.height * pointerWidthRatio

    val withoutPointerSize = size.copy(width = size.width - pointerHeight)

    val topStartPx = if (layoutDirection == LayoutDirection.Ltr) topStart.toPx(
        withoutPointerSize,
        density
    ) else topEnd.toPx(size, density)

    val topEndPx = if (layoutDirection == LayoutDirection.Ltr) topEnd.toPx(
        withoutPointerSize,
        density
    ) else topStart.toPx(size, density)

    val bottomStartPx = if (layoutDirection == LayoutDirection.Ltr) bottomStart.toPx(
        withoutPointerSize,
        density
    ) else bottomEnd.toPx(size, density)

    val bottomEndPx = if (layoutDirection == LayoutDirection.Ltr) bottomEnd.toPx(
        withoutPointerSize,
        density
    ) else bottomStart.toPx(size, density)

    path.apply {
        arcTo(
            rect = Rect(
                offset = Offset(0f, 0f),
                size = Size(
                    topStartPx * 2, topStartPx * 2
                )
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(size.width - pointerHeight - topEndPx * 2, 0f),
                size = Size(
                    topEndPx * 2, topEndPx * 2
                )
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    size.width - pointerHeight - bottomEndPx * 2,
                    size.height - bottomEndPx * 2
                ),
                size = Size(
                    bottomEndPx * 2, bottomEndPx * 2
                )
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(0f, size.height - bottomStartPx * 2),
                size = Size(
                    bottomStartPx * 2, bottomStartPx * 2
                )
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
    }

    val startPosition = Offset(
        size.width - drawPointerHeight,
        size.height / 2 - pointerWidth / 2
    )

    val endPosition = Offset(
        size.width - drawPointerHeight,
        size.height / 2 + pointerWidth / 2
    )

    val pointPosition = if (layoutDirection == LayoutDirection.Ltr) {
        Offset(size.width, size.height / 2 + pointerWidth * skewed)
    } else {
        Offset(size.width, size.height / 2 - pointerWidth * skewed)
    }

    path.apply {
        op(
            path1 = this,
            path2 = Path().apply {
                moveTo(
                    startPosition.x,
                    startPosition.y
                )
                lineTo(
                    pointPosition.x,
                    pointPosition.y
                )
                lineTo(
                    endPosition.x,
                    endPosition.y
                )
            },
            operation = PathOperation.Union
        )
    }

    return path
}

private fun drawBottomPointerSpeechBubble(
    size: Size,
    layoutDirection: LayoutDirection,
    density: Density,
    topStart: CornerSize,
    topEnd: CornerSize,
    bottomEnd: CornerSize,
    bottomStart: CornerSize,
    pointerWidthRatio: Float,
    pointerHeightRatio: Float,
    skewed: Float,
): Path {
    val path = Path()

    val pointerHeight = size.height * pointerHeightRatio
    val drawPointerHeight = pointerHeight * 1.1f
    val pointerWidth = size.width * pointerWidthRatio

    val withoutPointerSize = size.copy(height = size.height - pointerHeight)

    val topStartPx = if (layoutDirection == LayoutDirection.Ltr) topStart.toPx(
        withoutPointerSize,
        density
    ) else topEnd.toPx(size, density)

    val topEndPx = if (layoutDirection == LayoutDirection.Ltr) topEnd.toPx(
        withoutPointerSize,
        density
    ) else topStart.toPx(size, density)

    val bottomStartPx = if (layoutDirection == LayoutDirection.Ltr) bottomStart.toPx(
        withoutPointerSize,
        density
    ) else bottomEnd.toPx(size, density)

    val bottomEndPx = if (layoutDirection == LayoutDirection.Ltr) bottomEnd.toPx(
        withoutPointerSize,
        density
    ) else bottomStart.toPx(size, density)

    path.apply {
        arcTo(
            rect = Rect(
                offset = Offset(0f, 0f),
                size = Size(
                    topStartPx * 2, topStartPx * 2
                )
            ),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(size.width - topEndPx * 2, 0f),
                size = Size(
                    topEndPx * 2, topEndPx * 2
                )
            ),
            startAngleDegrees = 270f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(
                    size.width - bottomEndPx * 2,
                    size.height - pointerHeight - bottomEndPx * 2
                ),
                size = Size(
                    bottomEndPx * 2, bottomEndPx * 2
                )
            ),
            startAngleDegrees = 0f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )

        arcTo(
            rect = Rect(
                offset = Offset(0f, size.height - pointerHeight - bottomStartPx * 2),
                size = Size(
                    bottomStartPx * 2, bottomStartPx * 2
                )
            ),
            startAngleDegrees = 90f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
    }

    val startPosition = Offset(
        size.width / 2 - pointerWidth / 2,
        size.height - drawPointerHeight
    )

    val endPosition = Offset(
        size.width / 2 + pointerWidth / 2,
        size.height - drawPointerHeight
    )

    val pointPosition = if (layoutDirection == LayoutDirection.Ltr) {
        Offset(size.width / 2 + pointerWidth * skewed, size.height)
    } else {
        Offset(size.width / 2 - pointerWidth * skewed, size.height)
    }

    path.apply {
        op(
            path1 = this,
            path2 = Path().apply {
                moveTo(
                    startPosition.x,
                    startPosition.y
                )
                lineTo(
                    pointPosition.x,
                    pointPosition.y
                )
                lineTo(
                    endPosition.x,
                    endPosition.y
                )
            },
            operation = PathOperation.Union
        )
    }

    return path
}

fun SpeechBubbleShape(
    corner: CornerSize,
    pointerWidthRatio: Float = 0.2f,
    pointerHeightRatio: Float = 0.2f,
    pointerPosition: PointerPosition = PointerPosition.Bottom,
    /*@FloatRange(from = -1.0, to = 1.0)*/
    pointerSkewed: Float = 0f,
) =
    SpeechBubbleShape(
        corner, corner, corner, corner,
        pointerWidthRatio = pointerWidthRatio,
        pointerHeightRatio = pointerHeightRatio,
        pointerPosition, pointerSkewed
    )


fun SpeechBubbleShape(
    size: Dp,
    pointerWidthRatio: Float = 0.2f,
    pointerHeightRatio: Float = 0.2f,
    pointerPosition: PointerPosition = PointerPosition.Bottom,
    /*@FloatRange(from = -1.0, to = 1.0)*/
    pointerSkewed: Float = 0f,
) = SpeechBubbleShape(
    CornerSize(size),
    pointerWidthRatio = pointerWidthRatio,
    pointerHeightRatio = pointerHeightRatio,
    pointerPosition, pointerSkewed
)

fun SpeechBubbleShape(
    size: Float,
    pointerWidthRatio: Float = 0.2f,
    pointerHeightRatio: Float = 0.2f,
    pointerPosition: PointerPosition = PointerPosition.Bottom,
    /*@FloatRange(from = -1.0, to = 1.0)*/
    pointerSkewed: Float = 0f,
) = SpeechBubbleShape(
    CornerSize(size),
    pointerWidthRatio = pointerWidthRatio,
    pointerHeightRatio = pointerHeightRatio,
    pointerPosition, pointerSkewed
)

fun SpeechBubbleShape(
    percent: Int,
    pointerWidthRatio: Float = 0.2f,
    pointerHeightRatio: Float = 0.2f,
    pointerPosition: PointerPosition = PointerPosition.Bottom,
    /*@FloatRange(from = -1.0, to = 1.0)*/
    pointerSkewed: Float = 0f,
) = SpeechBubbleShape(
    CornerSize(percent),
    pointerWidthRatio = pointerWidthRatio,
    pointerHeightRatio = pointerHeightRatio,
    pointerPosition, pointerSkewed
)

fun SpeechBubbleShape(
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp,
    pointerWidthRatio: Float = 0.2f,
    pointerHeightRatio: Float = 0.2f,
    pointerPosition: PointerPosition = PointerPosition.Bottom,
    /*@FloatRange(from = -1.0, to = 1.0)*/
    pointerSkewed: Float = 0f,
) = SpeechBubbleShape(
    topStart = CornerSize(topStart),
    topEnd = CornerSize(topEnd),
    bottomEnd = CornerSize(bottomEnd),
    bottomStart = CornerSize(bottomStart),
    pointerWidthRatio = pointerWidthRatio,
    pointerHeightRatio = pointerHeightRatio,
    pointerPosition = pointerPosition,
    pointerSkewed = pointerSkewed
)

fun SpeechBubbleShape(
    topStart: Float = 0.0f,
    topEnd: Float = 0.0f,
    bottomEnd: Float = 0.0f,
    bottomStart: Float = 0.0f,
    pointerWidthRatio: Float = 0.2f,
    pointerHeightRatio: Float = 0.2f,
    pointerPosition: PointerPosition = PointerPosition.Bottom,
    /*@FloatRange(from = -1.0, to = 1.0)*/
    pointerSkewed: Float = 0f,
) = SpeechBubbleShape(
    topStart = CornerSize(topStart),
    topEnd = CornerSize(topEnd),
    bottomEnd = CornerSize(bottomEnd),
    bottomStart = CornerSize(bottomStart),
    pointerWidthRatio = pointerWidthRatio,
    pointerHeightRatio = pointerHeightRatio,
    pointerPosition = pointerPosition,
    pointerSkewed = pointerSkewed
)

fun SpeechBubbleShape(
    /*@IntRange(from = 0, to = 100)*/
    topStartPercent: Int = 0,
    /*@IntRange(from = 0, to = 100)*/
    topEndPercent: Int = 0,
    /*@IntRange(from = 0, to = 100)*/
    bottomEndPercent: Int = 0,
    /*@IntRange(from = 0, to = 100)*/
    bottomStartPercent: Int = 0,
    pointerWidthRatio: Float = 0.2f,
    pointerHeightRatio: Float = 0.2f,
    pointerPosition: PointerPosition = PointerPosition.Bottom,
    /*@FloatRange(from = -1.0, to = 1.0)*/
    pointerSkewed: Float = 0f,
) = SpeechBubbleShape(
    topStart = CornerSize(topStartPercent),
    topEnd = CornerSize(topEndPercent),
    bottomEnd = CornerSize(bottomEndPercent),
    bottomStart = CornerSize(bottomStartPercent),
    pointerWidthRatio = pointerWidthRatio,
    pointerHeightRatio = pointerHeightRatio,
    pointerPosition = pointerPosition,
    pointerSkewed = pointerSkewed
)
