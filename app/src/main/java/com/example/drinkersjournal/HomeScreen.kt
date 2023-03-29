package com.example.drinkersjournal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.drinkersjournal.ui.theme.Pacifico


@Composable
fun HomeScreen(navController: NavController){

    SetBackgroundImage()




    Spacer(modifier = Modifier.fillMaxHeight(.1f))

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // App Logo
        CreateAppLogoImage(
            Modifier
                .fillMaxHeight(.4f)
                .fillMaxWidth()
                .padding(16.dp)
        )


        //All main menu buttons
        CreateMainMenuButton(label = "Try Random Drink", Screen.RandomDrinkScreen.route, navController,
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(vertical = 8.dp)

        )

        CreateMainMenuButton(label = "Browse Drinks", Screen.BrowseDrinksScreen.route, navController,
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(vertical = 8.dp)
        )

        CreateMainMenuButton(label = "Your Drinker's List", Screen.ViewListScreen.route, navController,
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(vertical = 8.dp)
        )
    }



}

@Composable
fun CreateAppLogoImage(modifier : Modifier){
    Image(
        painter = painterResource(id = R.drawable.ic_title),
        contentDescription = "Title",
        modifier = modifier
    )
}

@Composable
fun CreateMainMenuButton(label : String, route: String, navController: NavController, modifier : Modifier){
    val goldGradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0x00F57F17),
            Color(0x7fFFEE58),
            Color(0x00FFF9C4)
        ),

    )
    Button(
        onClick = {navController.navigate(route)},
        modifier = modifier.background(goldGradient),
        shape = RoundedCornerShape(10f),


        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        )
    ) {
        Text(
            text = label,
            fontSize = 32.sp,
            fontFamily = Pacifico
        )
    }
}


@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
){
    val backStackEntry = navController.currentBackStackEntryAsState()
    NavigationBar(
        modifier = modifier,
    ) {
        items.forEach{ item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name)
                        if(selected){
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp)
                        }
                    }
                }
            )
        }
    }
}


@Composable
fun SetBackgroundImage(){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_temporary_home_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }

}
