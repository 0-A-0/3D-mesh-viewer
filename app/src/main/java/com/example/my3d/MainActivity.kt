package com.example.my3d

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.my3d.ui.MainScreen
import com.example.my3d.ui.MainViewModel
import com.example.my3d.ui.theme.My3DTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            My3DTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    My3DTheme {
        MainScreen(viewModel = MainViewModel())
    }
}

@Preview(showBackground = true)
@Composable
fun DarkMainScreenPreview() {
    My3DTheme(darkTheme = true) {
        MainScreen(viewModel = MainViewModel())
    }
}
