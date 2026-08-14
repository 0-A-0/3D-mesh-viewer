package com.example.my3d

import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.my3d.ui.theme.My3DTheme
import com.example.my3d.ui.theme.canvas.MyCanvas
import com.example.my3d.ui.theme.figures.Cube
import com.example.my3d.ui.theme.figures.Cylinder
import com.example.my3d.ui.theme.figures.Sphere
import com.example.my3d.ui.theme.structure.Figure
import com.example.my3d.ui.theme.structure.Point

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            My3DTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
object WIN {
    val left: Float = -5f
    val bottom: Float = -5f
    val width: Float = 10f
    val height: Float = 10f
    val focus: Point = Point(0f, 0f, 30f)
    val camera: Point = Point(0f, 0f, 50f)
    //val zoom: Float = 1.0f
}
@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var arePoints by remember { mutableStateOf(true) }
    var areEdges by remember { mutableStateOf(true) }
    var arePolygons by remember { mutableStateOf(true) }
    var indexFigure by remember { mutableIntStateOf(0) }
    var prop:Float by remember { mutableFloatStateOf(10f) }
    var scaleX:Float by remember { mutableFloatStateOf(1f) }
    var scaleY:Float by remember { mutableFloatStateOf(1f) }
    var scaleZ:Float by remember { mutableFloatStateOf(1f) }
    var figure:Figure by remember { mutableStateOf(Cube()) }
    val canvas = remember { MyCanvas(win = WIN, arePoints = arePoints, areEdges = areEdges, arePolygons = arePolygons)}
    val switchModifier = Modifier.scale(0.7f)
    val triangle:Shape = remember { GenericShape{ size, _->
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
                onValueChange = {
                    prop = it.toInt().toFloat()
                    figure = when (indexFigure) {
                        0 -> figure
                        1 -> Cylinder(prop, scaleX, scaleY, scaleZ)
                        2 -> Sphere(prop, scaleX, scaleY, scaleZ)
                        else -> Cube( scaleX, scaleY, scaleZ)
                    }
                },
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
                onValueChange = {
                    scaleX = it.toInt().toFloat()
                    figure = when (indexFigure) {
                        0 -> Cube( scaleX, scaleY, scaleZ)
                        1 -> Cylinder(prop, scaleX, scaleY, scaleZ)
                        2 -> Sphere(prop, scaleX, scaleY, scaleZ)
                        else -> Cube( scaleX, scaleY, scaleZ)
                    }
                },
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
                onValueChange = {
                    scaleY = it.toInt().toFloat()
                    figure = when (indexFigure) {
                        0 -> Cube( scaleX, scaleY, scaleZ)
                        1 -> Cylinder(prop, scaleX, scaleY, scaleZ)
                        2 -> Sphere(prop, scaleX, scaleY, scaleZ)
                        else -> Cube( scaleX, scaleY, scaleZ)
                    }
                },
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
                onValueChange = {
                    scaleZ = it.toInt().toFloat()
                    figure = when (indexFigure) {
                        0 -> Cube( scaleX, scaleY, scaleZ)
                        1 -> Cylinder(prop, scaleX, scaleY, scaleZ)
                        2 -> Sphere(prop, scaleX, scaleY, scaleZ)
                        else -> Cube( scaleX, scaleY, scaleZ)
                    }
                },
                valueRange = 1f..10f,
                modifier = Modifier.width(150.dp)
            )
        }
        canvas.drawFigure(figure)
        Button(
            onClick = {
                indexFigure = (indexFigure + 1) % 3
                figure = when(indexFigure){
                    0 -> Cube( scaleX, scaleY, scaleZ)
                    1 -> Cylinder(prop,scaleX,scaleY,scaleZ)
                    2 -> Sphere(prop,scaleX,scaleY,scaleZ)
                    else -> Cube( scaleX, scaleY, scaleZ)
                }
            },
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
                    onCheckedChange = { arePoints = it },
                    modifier = switchModifier,
                )
                Switch(
                    checked = areEdges,
                    enabled = true,
                    onCheckedChange = { areEdges = it },
                    modifier = switchModifier
                )
                Switch(
                    checked = arePolygons,
                    enabled = true,
                    onCheckedChange = { arePolygons = it },
                    modifier = switchModifier
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    My3DTheme {
        Greeting()
    }
}

@Preview(showBackground = true)
@Composable
fun DarkGreetingPreview() {
    My3DTheme(darkTheme = true) {
        Greeting()
    }
}