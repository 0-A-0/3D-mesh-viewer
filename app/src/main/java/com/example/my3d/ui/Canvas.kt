package com.example.my3d.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.my3d.math.Math3D
import com.example.my3d.math.WIN
import com.example.my3d.structure.Figure

data class CanvasUiState(
    val figure: Figure,
    val arePoints: Boolean,
    val areEdges: Boolean,
    val arePolygons: Boolean,
    val scale: Float,
    val test: Float,
)

@Composable
fun MyCanvas(
    state: CanvasUiState,
    onTransformPoints: (Float, Float) -> Unit,
    onZoom: (Float) -> Unit,
    modifier: Modifier = Modifier,
    width: Float = 400f,
    height: Float = 400f,
    win: WIN = WIN,
    color: Color = Color.Black,
) {
    val figure = state.figure
    val arePoints = state.arePoints
    val areEdges = state.areEdges
    val arePolygons = state.arePolygons
    val scale = state.scale
    val test = state.test

    val radius = 3f
    val math = remember { Math3D(win) }
    var canvasSize by remember { mutableStateOf(IntSize.Zero) }

    val currentFigure by rememberUpdatedState(newValue = figure)
    val pointerMod = remember {
        Modifier.pointerInput(Unit){
            awaitPointerEventScope {
                while (true) {
                    val startPosition = awaitFirstDown().position
                    while (true) {
                        val event: PointerEvent = awaitPointerEvent()
                        val changes: List<PointerInputChange> = event.changes
                        val pointers = event.changes.filter { it.pressed }
                        if (pointers.size == 1) {
                            if (changes[0].pressed) {
                                val position = changes[0].position - startPosition
                                onTransformPoints(position.x, position.y)
                            }
                        } else break
                    }
                }
            }
        }
    }

    fun xs(x: Float): Float {
        return canvasSize.width * (x - win.left) / win.width;
    }
    fun ys(y: Float): Float {
        return canvasSize.width - canvasSize.width * (y - win.bottom) / win.height;
    }

    Column(modifier = modifier) {
        Canvas(
            modifier = Modifier
                .onSizeChanged { size -> canvasSize = size }
                .width(width.dp)
                .height(height.dp)
                .then(pointerMod)
                .border(width = 3.dp, color = MaterialTheme.colorScheme.primary)
                .background(MaterialTheme.colorScheme.secondary).clipToBounds()
                .pointerInput(Unit){
                    detectTransformGestures{centroid, pan, zoom, rotation ->
                        onZoom(zoom)
                    }
                }
        ) {
            withTransform({
                scale(scaleX = scale, scaleY =  scale)
            }) {
                if (arePolygons) {
                    math.calcCenter(currentFigure)
                    math.calcDistance(currentFigure, win.camera)
                    //math.calcDistance(currentFigure,this.LIGHT,'lumen')
                    math.sortByArtistAlgoritm(currentFigure.polygons)
                    for (polygon in currentFigure.polygons) {
                        val points = listOf(
                            currentFigure.points[polygon.points[0]],
                            currentFigure.points[polygon.points[1]],
                            currentFigure.points[polygon.points[2]],
                            currentFigure.points[polygon.points[3]]
                        )
                        val path = Path()
                        path.moveTo(xs(math.xs(points[0])), ys(math.ys(points[0])))
                        path.lineTo(xs(math.xs(points[1])), ys(math.ys(points[1])))
                        path.lineTo(xs(math.xs(points[2])), ys(math.ys(points[2])))
                        path.lineTo(xs(math.xs(points[3])), ys(math.ys(points[3])))
                        path.close()
                        drawPath(
                            path = path,
                            color = polygon.color,
                            style = Fill,
                        )
                    }
                }
                if (arePoints) {
                    for (point in currentFigure.points) {
                        drawCircle(
                            color = color,
                            radius = radius,
                            center = Offset(xs(math.xs(point)), ys(math.ys(point))),
                        )
                    }
                }
                if (areEdges) {
                    for (edge in currentFigure.edges) {
                        drawLine(
                            start = Offset(xs(math.xs(edge.p1)), ys(math.ys(edge.p1))),
                            end = Offset(xs(math.xs(edge.p2)), ys(math.ys(edge.p2))),
                            color = color,
                            strokeWidth = 1f
                        )
                    }
                }
            }
        }
        Text(
            text = "$test"
        )
    }
}
