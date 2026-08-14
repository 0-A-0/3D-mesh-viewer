package com.example.my3d.ui.theme.canvas

import com.example.my3d.WIN
import com.example.my3d.ui.theme.structure.Figure
import com.example.my3d.ui.theme.structure.Point
import com.example.my3d.ui.theme.structure.Polygon
import java.lang.Math.pow
import kotlin.math.sqrt

class Math3D(
    private val win:WIN
) {
    private val zc:Float = win.focus.z;
    private val z0:Float = win.camera.z;
    private val x0:Float = win.camera.x;
    private val y0:Float = win.camera.y;
    fun xs(point: Point):Float {
        return (point.x - x0) / (point.z - z0) * (zc - z0) + x0
    }
    fun ys(point:Point):Float {
        return (point.y - y0) / (point.z - z0) * (zc - z0) + y0
    }
//    fun transformPoint(array:List<List<Float>>,point:Point): Point{
//        val arr:FloatArray = floatArrayOf(0f,0f,0f,0f)
//        //classC.mult(array.flatten().toFloatArray(), listOf(point.x,point.y,point.z,1f).toFloatArray(),arr);
//        //val arr = mult(array, listOf(point.x,point.y,point.z,1f)); // на котлин
//        point.x = arr[0];
//        point.y = arr[1];
//        point.z = arr[2];
//        return point;
//    }
//    private fun mult(T:List<List<Float>>, m : List<Float>):List<Float> {
//        val c : MutableList<Float> = mutableListOf(0f, 0f, 0f, 0f);
//        for (i in 0 until 4) {
//            var s : Float = 0f;
//            for (j in 0 until 4) {
//            s += T[j][i] * m[j];
//        }
//            c[i] = s;
//        }
//        return c;
//    }
//    fun rotateOx(delta:Float):List<List<Float>> {
//        return listOf(
//            listOf(1f, 0f, 0f, 0f),
//            listOf(0f, cos(delta), sin(delta), 0f),
//            listOf(0f, -sin(delta), cos(delta), 0f),
//            listOf(0f, 0f, 0f, 1f)
//        )
//    }
//    fun rotateOy(delta:Float):List<List<Float>> {
//        return listOf(
//            listOf(cos(delta), 0f, -sin(delta), 0f),
//            listOf(0f, 1f, 0f, 0f),
//            listOf(sin(delta), 0f, cos(delta), 0f),
//            listOf(0f, 0f, 0f, 1f)
//        )
//    }
//    fun move(dx:Float,dy:Float,dz:Float):List<List<Float>> {
//        return listOf(
//            listOf(1f, 0f, 0f, 0f),
//            listOf(0f, 1f, 0f, 1f),
//            listOf(0f, 0f, 1f, 0f),
//            listOf(dx, dy, dz, 1f)
//        )
//    }
    fun calcCenter(figure: Figure) {
        for(polygon in figure.polygons ){
            val points = polygon.points;
            var x = 0f;var y = 0f; var z = 0f
            for (j in 0 until points.size) {
                x += figure.points[points[j]].x;
                y += figure.points[points[j]].y;
                z += figure.points[points[j]].z;
            }
            polygon.center.x = x / points.size;
            polygon.center.y = y / points.size;
            polygon.center.z = z / points.size;
        }
    }
    fun calcDistance(figure:Figure, endPoint:Point/*, name:String*/) {
        for(polygon in figure.polygons ){
            polygon.distance = sqrt(pow((endPoint.x - polygon.center.x).toDouble(), 2.0) +
                    pow((endPoint.y - polygon.center.y).toDouble(), 2.0) +
                    pow((endPoint.z - polygon.center.z).toDouble(), 2.0)).toFloat();
        }
    }
    fun sortByArtistAlgoritm(polygons:MutableList<Polygon>) {
        polygons.sortByDescending { it.distance };
    }
}