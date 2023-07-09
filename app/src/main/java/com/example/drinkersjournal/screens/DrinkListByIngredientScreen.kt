package com.example.drinkersjournal.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.drinkersjournal.DrinkersInfo
import com.example.drinkersjournal.data.DisplayDrink
import com.example.drinkersjournal.data.DrinkByIngredients
import com.example.drinkersjournal.ui.theme.drinkNameFont
import com.example.drinkersjournal.util.Screen

// list of drinks with a common ingredient fetched by the api
val drinksByIngredient = mutableListOf<DrinkByIngredients>()

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DrinkListByIngredientScreen (navController: NavController){
    Scaffold(
        topBar = { CreateTopBar(text = DrinkersInfo.ingredientAppBarTextHolder, navController = navController) },
        content = { paddingValues ->
            SetBackgroundImage()

            LazyColumn(
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding())
            ) {
                itemsIndexed(drinksByIngredient) { _, drink ->
                    Surface(
                        modifier = Modifier.padding(top = 5.dp, start = 8.dp, end = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 5.dp, vertical = 5.dp)
                                .fillMaxWidth()
                                .height(100.dp)
                                .clickable {
                                    DisplayDrink.drinkId.value = drink.idDrink
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
                                overflow = TextOverflow.Ellipsis,
                                fontFamily = drinkNameFont,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }
                }
            }
        }
    )
}