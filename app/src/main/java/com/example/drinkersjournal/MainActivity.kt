package com.example.drinkersjournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.drinkersjournal.ui.theme.DrinkersJournalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Navigation()

            //test drinks for favorites list
            DrinkersInfo.addTestDrink("17060", 0, "asss checkss")
            DrinkersInfo.addTestDrink("17020", 0, "")
            DrinkersInfo.addTestDrink("13395", 1, "woblles")
            DrinkersInfo.addTestDrink("14688", 2, "")
            DrinkersInfo.addTestDrink("12762", 9, "kkjhgh")
        }
    }
}
