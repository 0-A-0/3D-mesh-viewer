package com.example.my3d.structure

import com.example.my3d.structure.figures.Cube
import com.example.my3d.structure.figures.Cylinder
import com.example.my3d.structure.figures.Sphere

object FigureFactory {
    fun create(indexFigure: Int, prop: Float, scaleX: Float, scaleY: Float, scaleZ: Float): Figure =
        when (indexFigure) {
            0 -> Cube(scaleX, scaleY, scaleZ)
            1 -> Cylinder(prop, scaleX, scaleY, scaleZ)
            2 -> Sphere(prop, scaleX, scaleY, scaleZ)
            else -> Cube(scaleX, scaleY, scaleZ)
        }
}
