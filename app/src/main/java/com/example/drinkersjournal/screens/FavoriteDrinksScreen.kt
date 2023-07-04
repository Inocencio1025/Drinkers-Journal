package com.example.drinkersjournal.screens

import androidx.compose.foundation.background
import com.example.drinkersjournal.data.Drink
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.drinkersjournal.DrinkersInfo
import com.example.drinkersjournal.data.BottomNavItem
import com.example.drinkersjournal.ui.theme.drinkNameFont
import com.example.drinkersjournal.ui.theme.drinkRatingTextFont
import com.example.drinkersjournal.ui.theme.topBarFont
import com.example.drinkersjournal.util.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteDrinksScreen(navController: NavController){
    val listSize by remember { mutableStateOf(DrinkersInfo.userFavList.size) }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Favorites",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = topBarFont
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
            )
        },
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
    ) { paddingValues ->

        SetBackgroundImage()

        if (listSize == 0){
            CreateEmptyListText()
        } else{
            Box {
                LazyColumn(
                    modifier = Modifier.padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    )
                ) {
                    //Generates Favorites List Here
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
                                CreateDrinkInList(drink)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CreateDrinkInList(drink: Drink) {
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
            .padding(
                top = 5.dp,
                bottom = 5.dp,
                start = 5.dp,
                end = 5.dp
            )
        //.background(Color.Green)
    ) {
        // drink name
        Text(
            text = drink.strDrink.toString(),
            textAlign = TextAlign.Left,
            color = Color.White,
            fontSize = 28.sp,
            overflow = TextOverflow.Ellipsis,
            fontFamily = drinkNameFont,
            modifier = Modifier
            //.background(color = Color.Red)
        )
        // drink rating text
        if (!drink.ratingText.isNullOrEmpty()){
            Text(
                text = "\"" + drink.ratingText.toString() + "\"",
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
    if (drink.rating != 0){
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

            // drink rating
            Text(
                text = drink.rating.toString(),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 40.sp,
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }


}


// displays only if list is empty
@Composable
fun CreateEmptyListText() {
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


