package com.example.drinkersjournal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.drinkersjournal.data.BottomNavItem
import com.example.drinkersjournal.data.Ingredient
import com.example.drinkersjournal.screens.CreateBottomSpace
import com.example.drinkersjournal.util.Screen

@Composable
fun BrowseDrinksScreen(navController: NavController){

    val ingredientList = mutableListOf<Ingredient>()


    ingredientList.add(Ingredient("Vodka", "https://www.thecocktaildb.com/images/ingredients/vodka-Medium.png"))
    ingredientList.add(Ingredient("Tequila", "https://www.thecocktaildb.com/images/ingredients/tequila-Medium.png"))
    ingredientList.add(Ingredient("Rum", "https://www.thecocktaildb.com/images/ingredients/rum-Medium.png"))
    ingredientList.add(Ingredient("Gin", "https://www.thecocktaildb.com/images/ingredients/gin-Medium.png"))
    ingredientList.add(Ingredient("Whiskey", "https://www.thecocktaildb.com/images/ingredients/whiskey-Medium.png"))
    ingredientList.add(Ingredient("Blended_Whiskey", "https://www.thecocktaildb.com/images/ingredients/Blended%20whiskey-Medium.png"))
    ingredientList.add(Ingredient("Light_Rum", "https://www.thecocktaildb.com/images/ingredients/Light%20rum-Medium.png"))
    ingredientList.add(Ingredient("Bourbon", "https://www.thecocktaildb.com/images/ingredients/bourbon-Medium.png"))
    ingredientList.add(Ingredient("Brandy", "https://www.thecocktaildb.com/images/ingredients/brandy-Medium.png"))
    ingredientList.add(Ingredient("Amaretto", "https://www.thecocktaildb.com/images/ingredients/amaretto-Medium.png"))
    ingredientList.add(Ingredient("Malibu_Rum", "https://www.thecocktaildb.com/images/ingredients/Malibu%20rum-Medium.png"))
    ingredientList.add(Ingredient("Dry_Vermouth", "https://www.thecocktaildb.com/images/ingredients/Dry%20vermouth-Medium.png"))
    ingredientList.add(Ingredient("Triple_Sec", "https://www.thecocktaildb.com/images/ingredients/Triple%20sec-Medium.png"))
    ingredientList.add(Ingredient("Coffee_Liqueur", "https://www.thecocktaildb.com/images/ingredients/Coffee%20liqueur-Medium.png"))
    ingredientList.add(Ingredient("Club_Soda", "https://www.thecocktaildb.com/images/ingredients/Club%20soda-Medium.png"))
    ingredientList.add(Ingredient("Grenadine", "https://www.thecocktaildb.com/images/ingredients/grenadine-Medium.png"))
    ingredientList.add(Ingredient("Orange_Juice", "https://www.thecocktaildb.com/images/ingredients/Orange%20juice-Medium.png"))
    ingredientList.add(Ingredient("Cranberry_Juice", "https://www.thecocktaildb.com/images/ingredients/Cranberry%20juice-Medium.png"))
    ingredientList.add(Ingredient("Blue Curacao", "https://www.thecocktaildb.com/images/ingredients/Blue%20curacao-Medium.png"))


    CreateIngredientList(ingredientList, navController)





}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateIngredientList(ingredientList: MutableList<Ingredient>, navController: NavController) {

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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(it)
        ) {
            itemsIndexed(ingredientList) { index, ingredient ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(16.dp)
                        .clickable {
                            DrinkersInfo.ingredientForDrinkList = ingredient.name
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
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
                contentAlignment = Alignment.BottomCenter
            ){
                Text(
                    text = drinkName,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}