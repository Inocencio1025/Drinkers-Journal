package com.example.drinkersjournal.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.drinkersjournal.R
import com.example.drinkersjournal.data.BottomNavItem
import com.example.drinkersjournal.ui.theme.drinkNameFont
import com.example.drinkersjournal.ui.theme.topBarFont

/*
    All composables here are reused on more than one screen.
    Composables that are unique to that Screen are
    store with that Screen
 */

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTopBar(text: String, navController: NavController) {
    CenterAlignedTopAppBar(
        title = { Text(
            text = text,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontFamily = topBarFont
        ) },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Filled.ArrowBack, "backIcon")
            }
        },
    )
}

@Composable
fun CreateBottomNavBar(navController: NavController) {
    BottomNavigationBar(
        items = listOf(
            BottomNavItem(
                name = "Browse",
                route = "browse_drinks_screen",
                icon = Icons.Default.Search
            ),
            BottomNavItem(
                name = "Try Drink",
                route = "random_drink_screen",
                icon = Icons.Default.Refresh
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
        tonalElevation = 5.dp
    ) {
        items.forEach{ item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            NavigationBarItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

//For RandomDrinkScreen and DrinkDetailsScreen--------------------------------
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalAnimationApi::class) //for glideImage
@Composable
fun CreateDrinkImage(img: String) {

    val state = remember {
        MutableTransitionState(false).apply {
            // Start the animation immediately.
            targetState = true
        }
    }

    Box(modifier = Modifier.padding(24.dp)){
        AnimatedVisibility(visibleState = state) {
            GlideImage(
                model = img,
                contentDescription = "",
                modifier = Modifier
                    .height(200.dp)
                    .animateEnterExit(
                        enter = fadeIn(),
                        exit = fadeOut()
                    )
            )
        }

    }


}

@Composable
fun CreateNameText(nameStr: String){
    var resizedFont by remember {
        mutableStateOf(50.sp)
    }

    Text(
        text = nameStr,
        color = Color.White,
        fontSize = resizedFont,
        modifier = Modifier.layoutId("drinkName"),
        fontFamily = drinkNameFont,
        softWrap = false,
        onTextLayout = { result ->
            if(result.didOverflowWidth) {
                resizedFont *= 0.95
            }
        }
    )
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun CreateIngredientText(ingStr: String) {
    Text(
        text = ingStr,
        color = Color.White,
        fontSize = 20.sp,
        modifier = Modifier
            .layoutId("ingredients")
            .padding(4.dp)
    )
}

@Composable
fun CreateInstructionText(instrStr: String){
    Spacer(modifier = Modifier.height(30.dp))

    Text(
        text = "Directions:",
        textDecoration = TextDecoration.Underline,
        color = Color.Red,
        fontSize = 20.sp
    )

    Text(
        text = instrStr,
        color = Color.White,
        fontSize = 24.sp,
        modifier = Modifier.padding(vertical = 20.dp, horizontal = 20.dp ),
        fontFamily = topBarFont
    )
}


