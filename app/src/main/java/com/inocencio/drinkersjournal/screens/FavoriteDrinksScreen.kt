package com.inocencio.drinkersjournal.screens

import com.inocencio.drinkersjournal.data.Drink
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.inocencio.drinkersjournal.util.DrinkersInfo
import com.inocencio.drinkersjournal.data.DisplayDrink
import com.inocencio.drinkersjournal.ui.theme.drinkNameFont
import com.inocencio.drinkersjournal.ui.theme.drinkRatingTextFont
import com.inocencio.drinkersjournal.data.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteDrinksScreen(navController: NavController){
    val listSize by remember { mutableStateOf(DrinkersInfo.userFavDrinkList.size) }
    Scaffold(
        topBar = { CreateTopBar(text = "Favorites", navController = navController) },
        bottomBar = { CreateBottomNavBar(navController) },
        content = { paddingValues ->
            SetBackgroundImage()

            if (listSize == 0){
                CreateEmptyListText()
            } else {
                LazyColumn(
                    modifier = Modifier.padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding(),
                    )
                ) {
                    itemsIndexed(DrinkersInfo.userFavDrinkList) { _: Int, drink: Drink ->
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
                                    }
                            ) {
                                CreateListEntry(drink)
                            }
                        }
                    }
                }
            }
        }

/*
        ,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    CoroutineScope(Dispatchers.Main).launch {
                        DrinkersInfo.clearList()
                    }
                },

                shape = RectangleShape,
            ) {
                Text(text = listSize.toString())
            }
        },

 */

    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CreateListEntry(drink: Drink) {
    GlideImage(
        model = drink.strDrinkThumb.toString(),
        contentDescription = "Drink image",
        modifier = Modifier
            .fillMaxHeight(1f)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth(.85f)
            .padding(
                top = 5.dp,
                bottom = 5.dp,
                start = 5.dp,
                end = 5.dp
            )
    ) {
        val drinkName = remember { mutableStateOf(drink.strDrink.toString()) }
        Text(
            text = drinkName.value,
            textAlign = TextAlign.Left,
            color = Color.White,
            fontSize = 28.sp,
            overflow = TextOverflow.Ellipsis,
            fontFamily = drinkNameFont,
            modifier = Modifier
            //.background(color = Color.Red)
        )

        val ratingText = remember { mutableStateOf(drink.ratingText.toString()) }
        if (!ratingText.value.isNullOrEmpty()){
            Text(
                text = "\"" + ratingText.value + "\"",
                textAlign = TextAlign.Left,
                fontFamily = drinkRatingTextFont,
                color = Color.Green,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    //.background(color = Color.Green)
                    .padding(horizontal = 10.dp, vertical = 8.dp)
                    .fillMaxSize()
            )
        }
    }

    val rating = remember { mutableStateOf(drink.rating) }
    if (rating.value != "0"){
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var resizedFont by remember {
                mutableStateOf(12.sp)
            }
            Text(
                text = "RATING",
                textAlign = TextAlign.Center,
                color = Color.Yellow,
                fontSize = resizedFont,
                fontFamily = drinkNameFont,
                fontWeight = FontWeight.Bold,
                softWrap = false,
                onTextLayout = { result ->
                    if(result.didOverflowWidth) {
                        resizedFont *= 0.95
                    }
                },
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = drink.rating,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 40.sp,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun CreateEmptyListText() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Your Favorites List is Empty",
            color = Color.Gray,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}


