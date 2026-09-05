package com.lmtoff.catalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.lmtoff.catalog.ui.LmtoffApp
import com.lmtoff.catalog.ui.theme.LmtoffTheme

/** Uygulamanın tek Activity'si; tüm arayüz Compose ile [LmtoffApp] içinde kurulur. */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LmtoffTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LmtoffApp()
                }
            }
        }
    }
}
