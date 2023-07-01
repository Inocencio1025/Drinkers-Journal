package com.example.drinkersjournal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.drinkersjournal.screens.SetBackgroundImage
import com.example.drinkersjournal.util.Screen

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DrinkListByIngredientScreen (navController: NavController){

    SetBackgroundImage()



    LazyColumn() {
        itemsIndexed(DrinkersInfo.drinksByIngredient) { index, drink ->
            Surface(
                modifier = Modifier.padding(top = 5.dp, start = 8.dp, end = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 5.dp)
                        .fillMaxWidth()
                        .height(100.dp)
                        .clickable {
                            DrinkersInfo.drinkId.value = drink.idDrink
                            navController.navigate(Screen.DrinkDetailsScreen.route)
                        },
                ) {

                    // drink image
                    GlideImage(
                        model = drink.strDrinkThumb,
                        contentDescription = "Drink image",
                        modifier = Modifier
                            .fillMaxHeight(1f)
                    )

                    // drink name
                    Text(
                        text = drink.strDrink,
                        textAlign = TextAlign.Left,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(5.dp)
                        //.background(color = Color.Red)
                    )
                }
            }
        }
    }
}