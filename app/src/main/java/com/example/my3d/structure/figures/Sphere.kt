package com.example.my3d.structure.figures

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.example.my3d.structure.Edge
import com.example.my3d.structure.Figure
import com.example.my3d.structure.Point
import com.example.my3d.structure.Polygon
import kotlin.math.cos
import kotlin.math.sin

class Sphere(
    var prop:Float = 9f,
    val scaleX:Float = 1f,
    val scaleY:Float = 1f,
    val scaleZ:Float = 1f,
): Figure() {
    init{
        val eps = 3.14f/prop
        var q = -Math.PI.toFloat() / 2f;
        var fi = 0f;
        var cout = 0;var w=0;var g=0; var x:Float; var y:Float; var z:Float
        while (q <= Math.PI / 2 + 0.0001) {
            w=0;
            while (fi <= 2 * Math.PI + 0.0001) {
                x = scaleX * sin(fi) * cos(q)
                y = scaleY * cos(fi)
                z = scaleZ * sin(fi) * sin(q)
                fi = fi + eps
                w++
                points.add(Point(x, y, z))
            }
            g++
            cout += 1
            fi = 0f
            q = q + eps;
        }
        for (i in 0 until points.size-w) {
            edges.add(Edge(this.points[i], this.points[i + w]))
        }
        var lvl = 0;
        // вертикальнфе рёбра
        for (j in 0 until g) {
            for (i in 0 until w-1 ) {
            edges.add(Edge(points[i + lvl], points[i + 1 + lvl]))
        }
            lvl += w;
        }
        var color = Color(0xff00ff00)
        for (i in 0 until w-1) {
            for (j in 0 until g-1) {
            if ((i % 4 == 0 || i % 4 == 1) && (j % 4 == 0 || j % 4 == 1)){color = Color(0xff00ff00)}
            if ((i % 4 == 0 || i % 4 == 1) && (j % 4 == 2 || j % 4 == 3)){color = Color(0xffff0000)}
            if ((i % 4 == 2 || i % 4 == 3) && (j % 4 == 0 || j % 4 == 1)){color = Color(0xffff0000)}
            if ((i % 4 == 2 || i % 4 == 3) && (j % 4 == 2 || j % 4 == 3)){color = Color(0xff00ff00)}
            polygons.add(Polygon(listOf(j*w+i,(j+1)*w+i,(j+1)*w+i+1,j*w+i+1),color))

        }
        }
    }
}