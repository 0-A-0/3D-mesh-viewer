package com.example.my3d.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen(modifier: Modifier = Modifier, viewModel: MainViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val arePoints = uiState.arePoints
    val areEdges = uiState.areEdges
    val arePolygons = uiState.arePolygons
    val prop = uiState.prop
    val scaleX = uiState.scaleX
    val scaleY = uiState.scaleY
    val scaleZ = uiState.scaleZ
    val figure = uiState.figure
    val scale = uiState.scale
    val test = uiState.test
    val switchModifier = Modifier.scale(0.7f)
    val triangle: Shape = remember { GenericShape { size, _->
        moveTo(size.width*1f/7f,0f)
        lineTo(size.width*4f/5f,0f)
        lineTo(size.width,size.height/2f)
        lineTo(size.width*4f/5f,size.height)
        lineTo(size.width*1f/7f,size.height)
        close()
    } }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(Modifier.height(50.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "density: ",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$prop",

            )
            Slider(
                value = prop,
                onValueChange = { viewModel.onPropChange(it) },
                valueRange = 1f..50f,
                modifier = Modifier.width(150.dp)
            )
        }
//            Spacer(modifier = Modifier.width(1.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "scaleX: ",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$scaleX"
            )
            Slider(
                value = scaleX,
                onValueChange = { viewModel.onScaleXChange(it) },
                valueRange = 1f..10f,
                modifier = Modifier.width(150.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "scaleY: ",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$scaleY"
            )
            Slider(
                value = scaleY,
                onValueChange = { viewModel.onScaleYChange(it) },
                valueRange = 1f..10f,
                modifier = Modifier.width(150.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "scaleZ: ",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$scaleZ"
            )
            Slider(
                value = scaleZ,
                onValueChange = { viewModel.onScaleZChange(it) },
                valueRange = 1f..10f,
                modifier = Modifier.width(150.dp)
            )
        }
        MyCanvas(
            state = CanvasUiState(
                figure = figure,
                arePoints = arePoints,
                areEdges = areEdges,
                arePolygons = arePolygons,
                scale = scale,
                test = test,
            ),
            onTransformPoints = viewModel::onTransformPoints,
            onZoom = viewModel::onZoom,
        )
        Button(
            onClick = { viewModel.onChangeFigure() },
            shape = triangle,
        ) { Text("Change figure")}
        Row {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.End
            ) {
                Spacer(modifier = Modifier.height(0.dp))
                Text(
                    text = "Points",
                )
                Text(
                    text = "Edges"
                )
                Text(
                    text = "Polygons"
                )
            }
            Spacer(
                modifier = Modifier.width(10.dp)
            )
            Column(
              //  verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(2.dp))
                Switch(
                    checked = arePoints,
                    enabled = true,
                    onCheckedChange = { viewModel.onTogglePoints(it) },
                    modifier = switchModifier,
                )
                Switch(
                    checked = areEdges,
                    enabled = true,
                    onCheckedChange = { viewModel.onToggleEdges(it) },
                    modifier = switchModifier
                )
                Switch(
                    checked = arePolygons,
                    enabled = true,
                    onCheckedChange = { viewModel.onTogglePolygons(it) },
                    modifier = switchModifier
                )
            }
        }
    }
}
