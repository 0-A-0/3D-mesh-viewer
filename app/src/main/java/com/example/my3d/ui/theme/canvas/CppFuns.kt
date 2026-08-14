package com.example.my3d.ui.theme.canvas

import com.example.my3d.ui.theme.structure.Point

class CppFuns {
    companion object {
      init {
          System.loadLibrary("my3d")
      }
    external fun mult(t: FloatArray, m : FloatArray, result:FloatArray)
    external fun transformPoints(valueX:Float,valueY:Float,points: Array<Point>)
    }
}
