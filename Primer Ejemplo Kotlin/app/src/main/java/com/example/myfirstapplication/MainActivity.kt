package com.example.myfirstapplication


import android.os.Bundle
import android.service.autofill.OnClickAction
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.VectorProperty
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.semantics.SemanticsActions.OnClick
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapplication.ui.theme.MyFirstApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        style = TextStyle(
            fontFamily = FontFamily.SansSerif,
            color = Color.Red,
            drawStyle = Stroke(
                miter = 4f,
                join = StrokeJoin.Round,
            )
        )
    )
    Text (
        text= "Bienvenidos a la clase de Aplicada II",
        modifier = modifier,
        color = Color.LightGray,
        textDecoration = TextDecoration.Underline
    )
    }
}

@Composable
fun fila(name: String, modifier: Modifier = Modifier)
{
    Row(verticalAlignment = Alignment.CenterVertically)
    {
        Text(
            text= "Prepadado",
            modifier = modifier
        )
        Text(
            text= " Para Enel",
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyFirstApplicationTheme {
        fila(" World")
    }
}

@Composable
fun boton()
{
    var showGreeting by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally
        , verticalArrangement = Arrangement.Center)
    {
        Button(onClick = { showGreeting = true })
        {
            Text("LLego el Profe")

        }

        if (showGreeting)
        {
            HelloWolrdEnelAP2Theme {

                GreetingColumn("World")
            }

        }
    }



}