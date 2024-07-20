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
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.math.sqrt

class StarPolygonShape(
    private val numOfPoints: Int,
    private val innerRadiusRatio: Float,
    private val cornerStyle: CornerStyle,
    private val outerCornerSize: CornerSize,
    private val innerCornerSize: CornerSize,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {

        if (numOfPoints == 0) {
            return Outline.Generic(Path())
        }

        val path = when (cornerStyle) {
            CornerStyle.ROUNDED -> {
                drawRoundedStarPolygonShape(
                    size = size,
                    numOfPoints = numOfPoints,
                    innerRadiusRatio = innerRadiusRatio,
                    outerCornerSize = outerCornerSize.toPx(size, density),
                    innerCornerSize = innerCornerSize.toPx(size, density)
                )
            }

            CornerStyle.INNER_ROUNDED -> {
                drawInnerRoundedStarPolygonShape(
                    size = size,
                    numOfPoints = numOfPoints,
                    innerRadiusRatio = innerRadiusRatio,
                    outerCornerSize = outerCornerSize.toPx(size, density),
                    innerCornerSize = innerCornerSize.toPx(size, density)
                )
            }

            CornerStyle.CUT -> {
                drawCutStarPolygonShape(
                    size = size,
                    numOfPoints = numOfPoints,
                    innerRadiusRatio = innerRadiusRatio,
                    outerCornerSize = outerCornerSize.toPx(size, density),
                    innerCornerSize = innerCornerSize.toPx(size, density)
                )
            }
        }

        return Outline.Generic(path)
    }
}

private fun drawRoundedStarPolygonShape(
    size: Size,
    numOfPoints: Int,
    innerRadiusRatio: Float,
    outerCornerSize: Float,
    innerCornerSize: Float,
): Path {
    val path = Path()

    val centerX = size.width / 2
    val centerY = size.height / 2
    val outerRadius = min(centerX, centerY)
    val innerRadius = outerRadius * innerRadiusRatio

    val theta = PI / numOfPoints

    var currentCenterAngle = 0.0

    val vertices = mutableListOf<Point>()
    for (i in 0 until numOfPoints * 2) {
        val r = if (i % 2 == 0) outerRadius else innerRadius
        val x = centerX + (r * sin(currentCenterAngle)).toFloat()
        val y = centerY - (r * cos(currentCenterAngle)).toFloat()
        vertices.add(Point(x, y))
        currentCenterAngle += theta
    }

    val (cX, cY) = vertices[0]
    val (pX, pY) = vertices[(vertices.size - 1) % vertices.size]
    val (nX, nY) = vertices[1]
    val (nnX, nnY) = vertices[2]

    val anglePrev = PI / 2 - atan((pY - cY) / (cX - pX))

    val angleNext = PI / 2 - atan((nY - cY) / (nX - cX))

    val outerVertexHalfAngle = (anglePrev + angleNext) / 2
    val outerVertexHalfAngleDegree = outerVertexHalfAngle.toDegree()

    val anglePrevInner = PI - atan((nY - cY) / (nX - cX))

    val angleNextInner = atan((nnY - nY) / (nnX - nX))

    val innerVertexHalfAngle = (anglePrevInner + angleNextInner) / 2
    val innerVertexHalfAngleDegree = innerVertexHalfAngle.toDegree()

    val outerCornerRadius = minOf(outerCornerSize, outerRadius)

    val innerCornerRadius = minOf(innerCornerSize, innerRadius)

    currentCenterAngle = 0.0

    for (i in vertices.indices) {
        val (x, y) = vertices[i]

        val curPointHalfAngleDegree =
            if (i % 2 == 0) outerVertexHalfAngleDegree else innerVertexHalfAngleDegree

        val curCornerRadius =
            if (i % 2 == 0) outerCornerRadius else innerCornerRadius
        val curRadius = if (i % 2 == 0) outerRadius else innerRadius

        val circleSinDist = curCornerRadius / sin(curPointHalfAngleDegree.toRadian())

        val circleCenter = if (curCornerRadius == 0f) {
            Point(
                x, y
            )
        } else if (i % 2 == 0) {
            Point(
                centerX + (curRadius - circleSinDist).toFloat() * sin(currentCenterAngle).toFloat(),
                centerY - (curRadius - circleSinDist).toFloat() * cos(currentCenterAngle).toFloat()
            )
        } else {
            Point(
                centerX + (circleSinDist + curRadius).toFloat() * sin(currentCenterAngle).toFloat(),
                centerY - (circleSinDist + curRadius).toFloat() * cos(currentCenterAngle).toFloat(),
            )
        }

        var centerCurAngle = atan2(
            (y - circleCenter.y.toDouble()),
            (x - circleCenter.x.toDouble())
        ).toDegree()

        centerCurAngle = if (centerCurAngle < 0f) {
            360f + centerCurAngle
        } else {
            centerCurAngle
        }

        var startAngleDegree = if (i % 2 == 0) {
            centerCurAngle - 90f + curPointHalfAngleDegree
        } else {
            centerCurAngle + 90f - curPointHalfAngleDegree
        }

        if (startAngleDegree < 0f) startAngleDegree += 360f

        val sweepAngle = if (i % 2 == 0) {
            180f - curPointHalfAngleDegree * 2
        } else {
            curPointHalfAngleDegree * 2 - 180f
        }

        path.arcTo(
            rect = Rect(
                offset = Offset(
                    (circleCenter.x.toDouble() - curCornerRadius).toFloat(),
                    (circleCenter.y.toDouble() - curCornerRadius).toFloat()
                ),
                size = Size(curCornerRadius * 2, curCornerRadius * 2)
            ),
            startAngleDegrees = startAngleDegree.toFloat(),
            sweepAngleDegrees = sweepAngle.toFloat(),
            forceMoveTo = false
        )

        currentCenterAngle += theta
    }

    path.close()

    return path
}

private fun drawInnerRoundedStarPolygonShape(
    size: Size,
    numOfPoints: Int,
    innerRadiusRatio: Float,
    outerCornerSize: Float,
    innerCornerSize: Float,
): Path {
    val path = Path()

    val centerX = size.width / 2
    val centerY = size.height / 2
    val outerRadius = min(centerX, centerY)
    val innerRadius = outerRadius * innerRadiusRatio

    val theta = PI / numOfPoints

    var currentCenterAngle = 0.0

    val vertices = mutableListOf<Point>()
    for (i in 0 until numOfPoints * 2) {
        val r = if (i % 2 == 0) outerRadius else innerRadius
        val x = centerX + (r * sin(currentCenterAngle)).toFloat()
        val y = centerY - (r * cos(currentCenterAngle)).toFloat()
        vertices.add(Point(x, y))
        currentCenterAngle += theta
    }

    val (cX, cY) = vertices[0]
    val (pX, pY) = vertices[(vertices.size - 1) % vertices.size]
    val (nX, nY) = vertices[1]
    val (nnX, nnY) = vertices[2]

    val anglePrev = PI / 2 - atan((pY - cY) / (cX - pX))

    val angleNext = PI / 2 - atan((nY - cY) / (nX - cX))

    val outerVertexHalfAngle = (anglePrev + angleNext) / 2
    val outerVertexHalfAngleDegree = outerVertexHalfAngle.toDegree()

    val anglePrevInner = PI - atan((nY - cY) / (nX - cX))

    val angleNextInner = atan((nnY - nY) / (nnX - nX))

    val innerVertexHalfAngle = (anglePrevInner + angleNextInner) / 2
    val innerVertexHalfAngleDegree = innerVertexHalfAngle.toDegree()

    val outerCornerRadius = minOf(outerCornerSize, outerRadius)

    val innerCornerRadius = minOf(innerCornerSize, innerRadius)

    currentCenterAngle = 0.0

    for (i in vertices.indices) {
        val (x, y) = vertices[i]

        val curPointHalfAngleDegree =
            if (i % 2 == 0) outerVertexHalfAngleDegree else innerVertexHalfAngleDegree

        val curCornerRadius =
            if (i % 2 == 0) outerCornerRadius else innerCornerRadius
        val curRadius = if (i % 2 == 0) outerRadius else innerRadius

        val circleSinDist = curCornerRadius / sin(curPointHalfAngleDegree.toRadian())

        val circleCenter = if (i % 2 == 0) {
            Point(
                x, y
            )
        } else {
            Point(
                centerX + (circleSinDist + curRadius).toFloat() * sin(currentCenterAngle).toFloat(),
                centerY - (circleSinDist + curRadius).toFloat() * cos(currentCenterAngle).toFloat(),
            )
        }

        var centerCurAngleDegree = atan2(
            (centerY - y.toDouble()),
            (centerX - x.toDouble())
        ).toDegree()

        centerCurAngleDegree = if (centerCurAngleDegree < 0f) {
            360f + centerCurAngleDegree
        } else {
            centerCurAngleDegree
        }

        var startAngleDegree = if (i % 2 == 0) {
            centerCurAngleDegree + curPointHalfAngleDegree
        } else {
            centerCurAngleDegree + 90f - curPointHalfAngleDegree
        }

        if (startAngleDegree < 0f) startAngleDegree += 360f

        val sweepAngle = if (i % 2 == 0) {
            -2 * curPointHalfAngleDegree
        } else {
            2 * curPointHalfAngleDegree - 180f
        }

        path.arcTo(
            rect = Rect(
                offset = Offset(
                    (circleCenter.x.toDouble() - curCornerRadius).toFloat(),
                    (circleCenter.y.toDouble() - curCornerRadius).toFloat()
                ),
                size = Size(curCornerRadius * 2, curCornerRadius * 2)
            ),
            startAngleDegrees = startAngleDegree.toFloat(),
            sweepAngleDegrees = sweepAngle.toFloat(),
            forceMoveTo = false
        )

        currentCenterAngle += theta
    }

    path.close()

    return path
}

private fun drawCutStarPolygonShape(
    size: Size,
    numOfPoints: Int,
    innerRadiusRatio: Float,
    outerCornerSize: Float,
    innerCornerSize: Float,
): Path {
    val path = Path()

    val centerX = size.width / 2
    val centerY = size.height / 2
    val outerRadius = min(centerX, centerY)
    val innerRadius = outerRadius * innerRadiusRatio

    val theta = PI / numOfPoints

    var currentCenterAngle = 0.0

    val vertices = mutableListOf<Point>()
    for (i in 0 until numOfPoints * 2) {
        val r = if (i % 2 == 0) outerRadius else innerRadius
        val x = centerX + (r * sin(currentCenterAngle)).toFloat()
        val y = centerY - (r * cos(currentCenterAngle)).toFloat()
        vertices.add(Point(x, y))
        currentCenterAngle += theta
    }

    val dx = vertices[1].x - vertices[0].x
    val dy = vertices[1].y - vertices[0].y
    val polygonDist = sqrt(dx * dx + dy * dy)


    for (i in vertices.indices) {
        val (x, y) = vertices[i]
        val (prevX, prevY) = vertices[(i + vertices.size - 1) % vertices.size]
        val (nextX, nextY) = vertices[(i + 1) % vertices.size]

        val prevDx = x - prevX
        val prevDy = y - prevY
        val nextDx = nextX - x
        val nextDy = nextY - y

        val startPoint = if (i % 2 == 0) {
            Point(
                x - prevDx * outerCornerSize / polygonDist,
                y - prevDy * outerCornerSize / polygonDist
            )
        } else {
            Point(
                x - prevDx * innerCornerSize / polygonDist,
                y - prevDy * innerCornerSize / polygonDist
            )
        }

        val endPoint = if (i % 2 == 0) {
            Point(
                x + nextDx * outerCornerSize / polygonDist,
                y + nextDy * outerCornerSize / polygonDist
            )
        } else {
            Point(
                x + nextDx * innerCornerSize / polygonDist,
                y + nextDy * innerCornerSize / polygonDist
            )
        }

        if (i == 0) {
            path.moveTo(
                startPoint.x,
                startPoint.y
            )
        } else {
            path.lineTo(
                startPoint.x,
                startPoint.y
            )
        }

        path.lineTo(
            endPoint.x,
            endPoint.y
        )

        currentCenterAngle += theta
    }

    path.close()

    return path
}

fun StarPolygonShape(
    numOfPoints: Int,
    innerRadiusRatio: Float = 0.5f,
    cornerStyle: CornerStyle = CornerStyle.ROUNDED,
    cornerSize: CornerSize = CornerSize(0.dp),
) =
    StarPolygonShape(numOfPoints, innerRadiusRatio, cornerStyle, cornerSize, cornerSize)

fun StarPolygonShape(
    numOfPoints: Int,
    innerRadiusRatio: Float = 0.5f,
    cornerStyle: CornerStyle = CornerStyle.ROUNDED,
    size: Dp = 0.dp,
) =
    StarPolygonShape(numOfPoints, innerRadiusRatio, cornerStyle, CornerSize(size))

fun StarPolygonShape(
    numOfPoints: Int,
    innerRadiusRatio: Float = 0.5f,
    cornerStyle: CornerStyle = CornerStyle.ROUNDED,
    outerCornerSize: Dp = 0.dp,
    innerCornerSize: Dp = 0.dp,
) = StarPolygonShape(
    numOfPoints = numOfPoints,
    innerRadiusRatio = innerRadiusRatio,
    cornerStyle = cornerStyle,
    outerCornerSize = CornerSize(outerCornerSize),
    innerCornerSize = CornerSize(innerCornerSize)
)
