package com.example.my3d.structure

import androidx.compose.ui.graphics.Color

class Polygon(
    var points:List<Int>,
    var color:Color,
    var center:Point = Point(),
    var distance:Float = 0f
) {
}