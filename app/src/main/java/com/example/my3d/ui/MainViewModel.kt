package com.example.my3d.ui

import androidx.lifecycle.ViewModel
import com.example.my3d.math.JniPointTransformer
import com.example.my3d.math.PointTransformer
import com.example.my3d.structure.Figure
import com.example.my3d.structure.FigureFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class MainUiState(
    val arePoints: Boolean = true,
    val areEdges: Boolean = true,
    val arePolygons: Boolean = true,
    val indexFigure: Int = 0,
    val prop: Float = 10f,
    val scaleX: Float = 1f,
    val scaleY: Float = 1f,
    val scaleZ: Float = 1f,
    val figure: Figure = FigureFactory.create(0, 10f, 1f, 1f, 1f),
    val scale: Float = 1f,
    val test: Float = 0f,
)

class MainViewModel(
    private val pointTransformer: PointTransformer = JniPointTransformer(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    fun onPropChange(value: Float) {
        val prop = value.toInt().toFloat()
        _uiState.update { state ->
            when (state.indexFigure) {
                0 -> state.copy(prop = prop)
                else -> state.copy(
                    prop = prop,
                    figure = FigureFactory.create(state.indexFigure, prop, state.scaleX, state.scaleY, state.scaleZ),
                )
            }
        }
    }

    fun onScaleXChange(value: Float) {
        val scaleX = value.toInt().toFloat()
        _uiState.update { state ->
            state.copy(
                scaleX = scaleX,
                figure = FigureFactory.create(state.indexFigure, state.prop, scaleX, state.scaleY, state.scaleZ),
            )
        }
    }

    fun onScaleYChange(value: Float) {
        val scaleY = value.toInt().toFloat()
        _uiState.update { state ->
            state.copy(
                scaleY = scaleY,
                figure = FigureFactory.create(state.indexFigure, state.prop, state.scaleX, scaleY, state.scaleZ),
            )
        }
    }

    fun onScaleZChange(value: Float) {
        val scaleZ = value.toInt().toFloat()
        _uiState.update { state ->
            state.copy(
                scaleZ = scaleZ,
                figure = FigureFactory.create(state.indexFigure, state.prop, state.scaleX, state.scaleY, scaleZ),
            )
        }
    }

    fun onChangeFigure() {
        _uiState.update { state ->
            val indexFigure = (state.indexFigure + 1) % 3
            state.copy(
                indexFigure = indexFigure,
                figure = FigureFactory.create(indexFigure, state.prop, state.scaleX, state.scaleY, state.scaleZ),
            )
        }
    }

    fun onTogglePoints(checked: Boolean) {
        _uiState.update { it.copy(arePoints = checked) }
    }

    fun onToggleEdges(checked: Boolean) {
        _uiState.update { it.copy(areEdges = checked) }
    }

    fun onTogglePolygons(checked: Boolean) {
        _uiState.update { it.copy(arePolygons = checked) }
    }

    fun onZoom(zoom: Float) {
        _uiState.update { state ->
            val scale = (state.scale * zoom).coerceIn(0.2f, 10f)
            state.copy(scale = scale)
        }
    }

    fun onTransformPoints(valueX: Float, valueY: Float) {
        val state = _uiState.value
        pointTransformer.transformPoints(valueX, valueY, state.figure.points.toTypedArray())
        _uiState.update { it.copy(test = valueX) }
    }
}
