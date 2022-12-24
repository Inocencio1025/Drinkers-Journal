package com.example.drinkersjournal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.drinkersjournal.ui.theme.DrinkersJournalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // renders background image
            Image(painter = painterResource(id = R.drawable.ic_temporary_home_background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            
            
            Column() {

                // renders name of app on top of home screen
                Image(
                    painter = painterResource(id = R.drawable.ic_title),
                    contentDescription = "Title Line 1",
                    modifier = Modifier
                        .fillMaxHeight(.4f)
                        .fillMaxWidth()
                        .padding(16.dp)
                )

                Spacer(modifier = Modifier.fillMaxHeight(.1f))

                // All buttons on home screen
                Button(onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                    Text(text = "Try Random Drink")
                }

                Button(onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = "Browse Drinks")
                }

                Button(onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(text = "Your Drinker's List")
                }

                

            }
            // For app name on top of home screen

        }






            /*
            DrinkersJournalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }

             */
    }

    fun createButton(text: String){

    }
}

/*
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DrinkersJournalTheme {
        Greeting("Android")
    }
}

 */