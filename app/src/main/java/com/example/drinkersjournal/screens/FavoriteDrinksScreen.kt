package com.example.drinkersjournal

import com.example.drinkersjournal.data.Drink





import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.drinkersjournal.data.BottomNavItem
import com.example.drinkersjournal.screens.BottomNavigationBar
import com.example.drinkersjournal.screens.SetBackgroundImage
import com.example.drinkersjournal.util.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FavoriteDrinksScreen(navController: NavController){
    var listSize by remember { mutableStateOf(DrinkersInfo.favDrinksList.size.toString()) }

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
                        icon = Icons.Default.List
                    ),
                    BottomNavItem(
                        name = "Favorites",
                        route = "view_list_screen",
                        icon = Icons.Default.Favorite
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

                itemsIndexed(DrinkersInfo.favDrinksList){ _:Int, drink:Drink ->
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 5.dp, vertical = 5.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                            .clickable {
                                DrinkersInfo.currentlyViewedDrinkId = drink.idDrink
                                navController.navigate(Screen.DrinkDetailsScreen.route)
                                //Log.d(TAG, drink.strDrink.toString() + " has been clicked")
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
                                Text(text = drink.strDrink.toString(),
                                    textAlign = TextAlign.Left,
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                    //.background(color = Color.Red)
                                )


                                // drink rating text
                                Text(text = drink.ratingText.toString(),
                                    textAlign = TextAlign.Left,
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Light,
                                    modifier = Modifier
                                        //.background(color = Color.Green)
                                        .padding(horizontal = 10.dp)
                                        .fillMaxSize()
                                )
                            }


                            // drink rating
                            Text(text = drink.rating.toString(),
                                textAlign = TextAlign.Justify,
                                color = Color.White,
                                fontSize = 40.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
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


