package com.example.drinkersjournal.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.drinkersjournal.DrinkersInfo
import com.example.drinkersjournal.data.BottomNavItem
import com.example.drinkersjournal.data.Ingredient
import com.example.drinkersjournal.ui.theme.drinkNameFont
import com.example.drinkersjournal.ui.theme.drinkRatingTextFont
import com.example.drinkersjournal.ui.theme.topBarFont
import com.example.drinkersjournal.util.Screen


val nonAlcoholicList = mutableListOf<Ingredient>()
val alcoholicList = mutableListOf<Ingredient>()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowseDrinksScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Browse By Ingredient",
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding(), bottom = paddingValues.calculateBottomPadding())
        ) {

            header {
                CreateCategoryTitle(text = "Alcoholic Ingredients")
            }
            itemsIndexed(alcoholicList) { _, ingredient ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(16.dp)
                        .clickable {

                            DrinkersInfo.setIngredientForSearch(ingredient.name)
                            navController.navigate(Screen.DrinkListByIngredientScreen.route)
                        }
                ){
                    CreateIngredientCard(
                        drinkName = ingredient.name,
                        imageUrl = ingredient.imageUrl
                    )
                }
            }

            header {
                CreateCategoryTitle(text = "Nonalcoholic Ingredients")
            }
            itemsIndexed(nonAlcoholicList) { _, ingredient ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(16.dp)
                        .clickable {
                            DrinkersInfo.setIngredientForSearch(ingredient.name)
                            navController.navigate(Screen.DrinkListByIngredientScreen.route)
                        }
                ){
                    CreateIngredientCard(
                        drinkName = ingredient.name,
                        imageUrl = ingredient.imageUrl
                    )
                }
            }
        }
        CreateBottomSpace()
    }
}

fun LazyGridScope.header(
    content: @Composable LazyGridItemScope.() -> Unit
) {
    item(span = { GridItemSpan(this.maxLineSpan) }, content = content)
}

@Composable
fun CreateCategoryTitle(text: String){
    Surface(
        modifier = Modifier.padding(top = 5.dp, start = 8.dp, end = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 32.sp,
                fontFamily = drinkRatingTextFont
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CreateIngredientCard(
    drinkName: String,
    imageUrl: String,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        //elevation = 5.dp
    ) {
        Box(
            modifier = Modifier.height(200.dp),
            contentAlignment = Alignment.Center

        ){
            GlideImage(
                model = imageUrl,
                contentDescription = drinkName,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.padding(8.dp)
            )

            // for transparent gradient
            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 300f
                    )
                )
            )

            //name text
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                var resizedFont by remember {
                    mutableStateOf(24.sp)
                }

                Text(
                    text = drinkName,
                    fontFamily = drinkNameFont,
                    color = Color.White,
                    fontSize = resizedFont,
                    softWrap = false,
                    onTextLayout = { result ->
                        if(result.didOverflowWidth) {
                            resizedFont *= 0.95
                        }
                    }

                )
            }
        }
    }
}

fun setBrowseList() {
    //alcoholic drinks
    alcoholicList.add(Ingredient("Vodka", "https://www.thecocktaildb.com/images/ingredients/vodka-Medium.png"))
    alcoholicList.add(Ingredient("Tequila", "https://www.thecocktaildb.com/images/ingredients/tequila-Medium.png"))
    alcoholicList.add(Ingredient("Rum", "https://www.thecocktaildb.com/images/ingredients/rum-Medium.png"))
    alcoholicList.add(Ingredient("Gin", "https://www.thecocktaildb.com/images/ingredients/gin-Medium.png"))
    alcoholicList.add(Ingredient("Whiskey", "https://www.thecocktaildb.com/images/ingredients/whiskey-Medium.png"))
    alcoholicList.add(Ingredient("Blended Whiskey", "https://www.thecocktaildb.com/images/ingredients/Blended%20whiskey-Medium.png"))
    alcoholicList.add(Ingredient("Irish Whiskey", "https://www.thecocktaildb.com/images/ingredients/Irish%20Whiskey-Medium.png"))
    alcoholicList.add(Ingredient("Light Rum", "https://www.thecocktaildb.com/images/ingredients/Light%20rum-Medium.png"))
    alcoholicList.add(Ingredient("Malibu Rum", "https://www.thecocktaildb.com/images/ingredients/Malibu%20rum-Medium.png"))
    alcoholicList.add(Ingredient("Bourbon", "https://www.thecocktaildb.com/images/ingredients/bourbon-Medium.png"))
    alcoholicList.add(Ingredient("Brandy", "https://www.thecocktaildb.com/images/ingredients/brandy-Medium.png"))
    alcoholicList.add(Ingredient("Cognac", "https://www.thecocktaildb.com/images/ingredients/cognac-Medium.png"))
    alcoholicList.add(Ingredient("Cointreau", "https://www.thecocktaildb.com/images/ingredients/cointreau-Medium.png"))
    alcoholicList.add(Ingredient("Amaretto", "https://www.thecocktaildb.com/images/ingredients/amaretto-Medium.png"))
    alcoholicList.add(Ingredient("Midori Melon Liqueur", "https://www.thecocktaildb.com/images/ingredients/midori-Medium.png"))
    alcoholicList.add(Ingredient("Dry Vermouth", "https://www.thecocktaildb.com/images/ingredients/Dry%20vermouth-Medium.png"))
    alcoholicList.add(Ingredient("Triple Sec", "https://www.thecocktaildb.com/images/ingredients/Triple%20sec-Medium.png"))
    alcoholicList.add(Ingredient("Chambord Raspberry Liqueur", "https://www.thecocktaildb.com/images/ingredients/Chambord%20Raspberry%20Liqueur-Medium.png"))
    alcoholicList.add(Ingredient("Campari", "https://www.thecocktaildb.com/images/ingredients/Campari-Medium.png"))
    alcoholicList.add(Ingredient("Coffee Liqueur", "https://www.thecocktaildb.com/images/ingredients/Coffee%20liqueur-Medium.png"))
    alcoholicList.add(Ingredient("Kahlua", "https://www.thecocktaildb.com/images/ingredients/Kahlua.png"))
    alcoholicList.add(Ingredient("Baileys Irish Cream", "https://www.thecocktaildb.com/images/ingredients/Baileys%20Irish%20Cream-Medium.png"))

    // nonalcoholic drinks
    nonAlcoholicList.add(Ingredient("Club Soda", "https://www.thecocktaildb.com/images/ingredients/Club%20soda-Medium.png"))
    nonAlcoholicList.add(Ingredient("Ginger Ale", "https://www.thecocktaildb.com/images/ingredients/Ginger%20Ale-Medium.png"))
    nonAlcoholicList.add(Ingredient("Ginger Beer", "https://www.thecocktaildb.com/images/ingredients/Ginger%20Beer-Medium.png"))
    nonAlcoholicList.add(Ingredient("Orange Juice", "https://www.thecocktaildb.com/images/ingredients/Orange%20juice-Medium.png"))
    nonAlcoholicList.add(Ingredient("Cranberry Juice", "https://www.thecocktaildb.com/images/ingredients/Cranberry%20juice-Medium.png"))
    nonAlcoholicList.add(Ingredient("Pineapple Juice", "https://www.thecocktaildb.com/images/ingredients/Cranberry%20juice-Medium.png"))
    nonAlcoholicList.add(Ingredient("Grenadine", "https://www.thecocktaildb.com/images/ingredients/grenadine-Medium.png"))
    nonAlcoholicList.add(Ingredient("Blue Curacao", "https://www.thecocktaildb.com/images/ingredients/Blue%20curacao-Medium.png"))
    nonAlcoholicList.add(Ingredient("Lime Juice", "https://www.thecocktaildb.com/images/ingredients/Lime%20Juice-Medium.png"))
    nonAlcoholicList.add(Ingredient("Sweet and Sour", "https://www.thecocktaildb.com/images/ingredients/Sour%20Mix-Medium.png"))
    nonAlcoholicList.add(Ingredient("Lime", "https://www.thecocktaildb.com/images/ingredients/Lime-Medium.png"))
    nonAlcoholicList.add(Ingredient("Lemon", "https://www.thecocktaildb.com/images/ingredients/lemon-Medium.png"))
    nonAlcoholicList.add(Ingredient("Strawberries", "https://www.thecocktaildb.com/images/ingredients/Strawberries-Medium.png"))
    nonAlcoholicList.add(Ingredient("Sugar", "https://www.thecocktaildb.com/images/ingredients/Sugar-Medium.png"))
    nonAlcoholicList.add(Ingredient("Mint", "https://www.thecocktaildb.com/images/ingredients/mint-Medium.png"))
}