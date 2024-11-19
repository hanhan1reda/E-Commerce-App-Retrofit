package com.example.mvvmtemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import com.example.mvvmtemplate.presentation.ui.HomeScreen
import com.example.mvvmtemplate.presentation.ui.LoginScreen
import com.example.mvvmtemplate.presentation.ui.UserScreen
import com.example.mvvmtemplate.ui.theme.MvvmTemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MvvmTemplateTheme {
                /*
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                */
                val sharedPreferences = getSharedPreferences("auth", MODE_PRIVATE)

              //  Navigator(screen = if (sharedPreferences.getString("token","")?.isNotEmpty() == true) UserScreen() else  LoginScreen())
                Navigator(screen = HomeScreen())
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MvvmTemplateTheme {
        Greeting("Android")
    }
}