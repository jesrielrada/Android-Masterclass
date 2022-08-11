package com.example.kotlinflows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlinflows.ui.theme.KotlinFlowsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinFlowsTheme {
                val viewModel = viewModel<MainViewModel>()
                //val time = viewModel.countDownFlow.collectAsState(initial = 10)
                val count = viewModel.stateFlow.collectAsState(initial = 0)
                Box(modifier = Modifier.fillMaxSize()) {
//                    Text(
//                        text = time.value.toString(),
//                        fontSize = 30.sp,
//                        modifier = Modifier.align(Alignment.Center)
//                    )

                    Button(onClick = { viewModel.incrementCounter() }) {
                        Text(text = "Counter: ${count.value}")

                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KotlinFlowsTheme {
        Greeting("Android")
    }
}