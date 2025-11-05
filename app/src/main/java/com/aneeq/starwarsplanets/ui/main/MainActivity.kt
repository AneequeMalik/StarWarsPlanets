package com.aneeq.starwarsplanets.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aneeq.starwarsplanets.ui.list.PlanetListScreen
import com.aneeq.starwarsplanets.ui.theme.StarWarsPlanetsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StarWarsPlanetsTheme {
                PlanetListScreen()
            }
        }
    }
}