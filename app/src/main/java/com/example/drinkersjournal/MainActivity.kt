package com.example.drinkersjournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.drinkersjournal.ui.theme.DrinkersJournalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            DrinkersJournalTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Navigation()

                }
            }


            //test drinks for favorites list
            DrinkersInfo.addTestDrink("17020", 0, "")
            DrinkersInfo.addTestDrink("13395", 1, "woblles")
            DrinkersInfo.addTestDrink("14688", 2, "")
            DrinkersInfo.addTestDrink("12762", 9, "kkjhgh")
        }
    }
}
