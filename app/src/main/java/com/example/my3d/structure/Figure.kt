package com.example.my3d.structure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/*
open class Figure(
    var points: MutableList<Point>,
    var edges: MutableList<Edge>,
    var polygons: MutableList<Polygon>
) {
}
*/
open class Figure {
  //  var points by mutableStateOf<MutableList<Point>>(mutableListOf())
  //  var edges by mutableStateOf<MutableList<Edge>>(mutableListOf())
  //  var polygons by mutableStateOf<MutableList<Polygon>>(mutableListOf())
    var points: MutableList<Point> = mutableListOf()
    var edges: MutableList<Edge> = mutableListOf()
    var polygons: MutableList<Polygon> = mutableListOf()
}