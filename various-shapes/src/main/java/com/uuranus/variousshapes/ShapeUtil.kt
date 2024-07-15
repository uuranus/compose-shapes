package com.uuranus.variousshapes

import kotlin.math.PI

data class Point(
    val x: Float,
    val y: Float,
)

internal fun Double.toRadian(): Double {
    return (this * PI / 180.0)
}

internal fun Double.toDegree(): Double {
    return (this * 180.0 / PI)
}

internal fun Float.toRadian(): Float {
    return (this * PI / 180.0).toFloat()
}

internal fun Float.toDegree(): Float {
    return (this * 180.0 / PI).toFloat()
}

