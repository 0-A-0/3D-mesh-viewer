package com.example.my3d.ui.theme.figures

import androidx.compose.ui.graphics.Color
import com.example.my3d.ui.theme.structure.Edge
import com.example.my3d.ui.theme.structure.Figure
import com.example.my3d.ui.theme.structure.Point
import com.example.my3d.ui.theme.structure.Polygon
import kotlin.math.cos
import kotlin.math.sin

class Cylinder(
    val prop:Float = 5f,
    val scaleX:Float = 1f,
    val scaleY:Float = 1f,
    val scaleZ:Float = 1f,
): Figure() {
    init {
            val eps = 3.14f/prop
            var fi: Float; var cout = 0;val k:Int = 9;var w = 0;var x: Float;var y: Float; var z = -4f
            for (i in 0 until k) {
                fi = 0f
                w = 0
                while (fi <= 2 * Math.PI + 0.00001f) {
                    x = scaleX * cos(fi)
                    y = scaleY * sin(fi)
                    fi += eps
                    w += 1
                    cout += 1
                    points.add(Point(x, y,scaleZ * z))
                }
                z += 1
        }
        var lvl = 0;
        for (j in 0 until k) {
            for (i in 0 until w -1) {
            edges.add(Edge(points[i + lvl], points[i + 1 + lvl]))
        }
            lvl += w;
        }
        // горизонтальные рёбра
        for (i in 0 until w){
            edges.add(Edge(points[i], points[cout - (w-i)]))
        }
       // lvl = 0;
        var color: Color
        for (i in 0 until w-1) {
            for (j in 0 until k-1) {
            if(j%2 == 0) color = Color.Yellow
            else color = Color.Green
            polygons.add(Polygon(listOf(j*w+i,(j+1)*w+i,(j+1)*w+i+1,j*w+i+1),color))

        }
        }
    }
}
