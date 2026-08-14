package com.example.my3d.ui.theme.figures

import androidx.compose.ui.graphics.Color
import com.example.my3d.ui.theme.structure.Edge
import com.example.my3d.ui.theme.structure.Figure
import com.example.my3d.ui.theme.structure.Point
import com.example.my3d.ui.theme.structure.Polygon

class Cube(
    val scaleX:Float = 1f,
    val scaleY:Float = 1f,
    val scaleZ:Float = 1f,
) :Figure() {
    init {
        points = mutableListOf(
        Point(1f, 1f, 1f),
        Point(-1f, 1f, 1f),
        Point(-1f, -1f, 1f),
        Point(1f, -1f, 1f),
        Point(1f, 1f, -1f),
        Point(1f, -1f, -1f),
        Point(-1f, 1f, -1f),
        Point(-1f, -1f, -1f),
        )
        for(point in points){
            point.x *= scaleX
            point.y *= scaleY
            point.z *= scaleZ
        }
        edges = mutableListOf(
        Edge(points[0], points[4]),
        Edge(points[0], points[1]),
        Edge(points[3], points[2]),
        Edge(points[5], points[7]),
        Edge(points[5], points[4]),
        Edge(points[6], points[1]),
        Edge(points[6], points[4]),
        Edge(points[6], points[7]),

        Edge(points[2], points[7]),
        Edge(points[5], points[3]),
        Edge(points[1], points[2]),
        Edge(points[3], points[0]),
        )
        polygons = mutableListOf(
        Polygon(listOf(0, 1, 2, 3), Color(0xff849123)),
        Polygon(listOf(1,6 , 7, 2), Color(0xffff5632)),
        Polygon(listOf(1, 6, 4, 0), Color(0xffff9021)),
        Polygon(listOf(6, 4, 5, 7), Color(0xffff0090)),
        Polygon(listOf(2, 7, 5, 3), Color.Red),
        Polygon(listOf(0, 4, 5, 3), Color.Cyan),
        )
    }
}