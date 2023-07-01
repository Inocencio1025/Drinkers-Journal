package com.example.drinkersjournal.screens

import com.example.drinkersjournal.data.Drink





import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.drinkersjournal.DrinkersInfo
import com.example.drinkersjournal.data.BottomNavItem
import com.example.drinkersjournal.ui.theme.drinkRatingTextFont
import com.example.drinkersjournal.util.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FavoriteDrinksScreen(navController: NavController){
    var listSize by remember { mutableStateOf(DrinkersInfo.userFavList.size.toString()) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "Try Drink",
                        route = "random_drink_screen",
                        icon = Icons.Default.Refresh
                    ),
                    BottomNavItem(
                        name = "Browse",
                        route = "browse_drinks_screen",
                        icon = Icons.Default.Search
                    ),
                    BottomNavItem(
                        name = "Favorites",
                        route = "view_list_screen",
                        icon = Icons.Default.List
                    )
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                }
            )
        }
    ) {


        SetBackgroundImage()

        Box(){

            LazyColumn(
                modifier = Modifier.padding(it)
            ) {

                itemsIndexed(DrinkersInfo.userFavList) { _: Int, drink: Drink ->
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
                            }
                    ) {

                            // drink image
                            GlideImage(
                                model = drink.strDrinkThumb.toString(),
                                contentDescription = "Drink image",
                                modifier = Modifier
                                    .fillMaxHeight(1f)
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(.85f)
                                    .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
                                //.background(Color.Green)
                            ) {


                                // drink name
                                Text(
                                    text = drink.strDrink.toString(),
                                    textAlign = TextAlign.Left,
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                    //.background(color = Color.Red)
                                )


                                // drink rating text
                                Text(
                                    text = drink.ratingText.toString(),
                                    textAlign = TextAlign.Left,
                                    fontFamily = drinkRatingTextFont,
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    modifier = Modifier
                                        //.background(color = Color.Green)
                                        .padding(horizontal = 10.dp, vertical = 8.dp)
                                        .fillMaxSize()
                                )
                            }


                            // drink rating
                            Text(
                                text = drink.rating.toString(),
                                textAlign = TextAlign.Justify,
                                color = Color.White,
                                fontSize = 40.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                }


                }

            Row() {
                Button(
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch{
                            DrinkersInfo.clearList()

                        }
                    },
                    modifier = Modifier.width(150.dp)
                ) {
                    Text(text = listSize)
                }

            }
        }





    }
}


