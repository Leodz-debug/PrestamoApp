package com.example.prestamoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.prestamoapp.presentation.navigation.AppNavigation
import com.example.prestamoapp.ui.theme.PrestamoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            PrestamoAppTheme {
                AppNavigation()
            }
        }
    }
}