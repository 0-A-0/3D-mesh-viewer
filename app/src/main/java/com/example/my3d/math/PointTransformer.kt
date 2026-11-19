package com.example.my3d.math

import com.example.my3d.structure.Point

interface PointTransformer {
    fun transformPoints(valueX: Float, valueY: Float, points: Array<Point>)
}

class JniPointTransformer : PointTransformer {
    override fun transformPoints(valueX: Float, valueY: Float, points: Array<Point>) {
        CppFuns.transformPoints(valueX, valueY, points)
    }
}
