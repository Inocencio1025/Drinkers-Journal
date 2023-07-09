package com.example.drinkersjournal.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.drinkersjournal.DrinkersInfo
import com.example.drinkersjournal.data.BottomNavItem
import com.example.drinkersjournal.data.DisplayDrink
import com.example.drinkersjournal.ui.theme.drinkNameFont
import com.example.drinkersjournal.ui.theme.drinkRatingTextFont
import com.example.drinkersjournal.ui.theme.topBarFont
import kotlinx.coroutines.launch

// values for screen view
private var stringRating = mutableStateOf("")
private var intRating = mutableStateOf(0)
// flags because im a bad programmer
private var isRating = mutableStateOf(false)
private var isDoneRating = mutableStateOf(true)
private var isInList = mutableStateOf(false)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkDetailsScreen (navController: NavController) {
    // Searches drink ID that is currently stored in DisplayDrink
    // and fill the rest of its fields. isIntList is bool
    // for if retrieved drink is in fav list
    isInList.value = DrinkersInfo.getInfoDrink()
    resetFlags()

    val currentDrink = DrinkersInfo.userFavList.find { it.idDrink == DisplayDrink.drinkId.value }
    if (currentDrink != null) {
        stringRating.value = currentDrink.ratingText.toString()
        intRating.value = currentDrink.rating.toInt()
    }

    Scaffold(
        topBar = { CreateTopBar("Drink Details", navController )},
        bottomBar = { CreateBottomNavBar(navController) },
        floatingActionButton = { CreateFAB() },
        content = { paddingValues ->
            SetBackgroundImage()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding()
                    )
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {

                CreateDrinkImage(DisplayDrink.imageUrlStr.value)
                CreateNameText(nameStr = DisplayDrink.drinkName.value)

                //Appearance is Conditional
                CreateRating()
                CreateRateButton()

                // Displays ingredients
                var i = 0
                DisplayDrink.ingredients.forEach {
                    if (DisplayDrink.measurements.getOrNull(i) != null)
                        CreateIngredientText(ingStr = DisplayDrink.measurements[i] + " " + it)
                    else {
                        CreateIngredientText(ingStr = it)
                    }
                    i++
                }

                // display instructions
                CreateInstructionText(instrStr = DisplayDrink.instructions.value)
                CreateBottomSpace()
            }
        }
    )
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
private fun CreateFAB() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    FloatingActionButton(
        onClick = {

            val id = DisplayDrink.drinkId.value
            coroutineScope.launch {
                if (!isInList.value) {
                    DrinkersInfo.addDrinkById(id = id, "", "0")
                    isInList.value = !isInList.value
                    Toast.makeText(
                        context,
                        DisplayDrink.drinkName.value + " added to favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    DrinkersInfo.deleteFromList(id)
                    isInList.value = !isInList.value
                    Toast.makeText(
                        context,
                        DisplayDrink.drinkName.value + " removed from favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        },
        contentColor = Color.Yellow,
        shape = CircleShape
    ) {
        if (!isInList.value) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Add to Favorites"
            )
        } else {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Add to Favorites"
            )
        }
    }
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




// ------------------Composable for drink display----------------------------------------------//


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



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRating() {
    //Text
    AnimatedVisibility(
        visible= stringRating.value != "" && !isRating.value,
        enter = slideInVertically () + expandVertically() + fadeIn(),
        exit = slideOutVertically () + shrinkVertically() + fadeOut()
    ){
        Text(text = "\"" + stringRating.value + "\"",
            color = Color.Green,
            fontSize = 24.sp,
            fontFamily = drinkRatingTextFont,
            textAlign = TextAlign.Center
        )
    }

    //Displaying the stars
    AnimatedVisibility(visible = intRating.value > 0 || isRating.value) {
        CreateRatingStars(
            maxRating = 5,
            currentRating = intRating.value,
            onRatingChanged = { newRating ->
                intRating.value = if (newRating == intRating.value) 0 else newRating
            }
        )
    }

    //TextBox
    AnimatedVisibility(visible = isRating.value){
        TextField(
            value = stringRating.value,
            onValueChange = { newText ->
                stringRating.value = newText
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                containerColor = MaterialTheme.colorScheme.inversePrimary,
                focusedLabelColor = Color.Transparent,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSecondary,
                cursorColor = Color.Black

            ),
            label = { Text(text = "Rate Here")},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)

        )
    }
}


@Composable
fun CreateRatingStars(
    maxRating: Int,
    currentRating: Int,
    onRatingChanged: (Int) -> Unit
) {
    Row(Modifier.padding(8.dp)) {
        for (i in 1..maxRating) {
            val isFilled = i <= currentRating

            val starIcon = if (isFilled) {
                Icons.Filled.Star
            } else {
                Icons.Outlined.Star
            }

            Icon(
                imageVector = starIcon,
                contentDescription = null,
                modifier = Modifier
                    .clickable {
                        onRatingChanged(i)
                    }
                    .padding(4.dp),
                tint = if (isFilled) Color.Yellow else Color.Yellow.copy(
                    alpha = .4f
                )
            )
        }
    }
}

@Composable
private fun CreateRateButton() {
    val colors = listOf(Color.Transparent, MaterialTheme.colorScheme.tertiary, Color.Transparent)
    val gradient = Brush.horizontalGradient(colors = colors)

    val borderColors = listOf(Color.Transparent, MaterialTheme.colorScheme.onPrimary, Color.Transparent)
    val borderGradient = Brush.horizontalGradient(colors = borderColors)

    AnimatedVisibility(visible = isInList.value) {
        Column(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .width(128.dp)
                .height(64.dp)
        ) {
            Divider(color = Color.Transparent, thickness = 1.dp, modifier = Modifier.background(borderGradient))

            val coroutineScope = rememberCoroutineScope()

            Button(
                modifier = Modifier
                    .background(gradient),
                onClick = {
                    coroutineScope.launch{
                        DrinkersInfo.addRatingToDrinkByID(DisplayDrink.drinkId.value, stringRating.value, intRating.value)

                        //set flags
                        isRating.value = !isRating.value
                        isDoneRating.value = !isDoneRating.value

                    }
                }
            ) {

                if(isRating.value){
                    Text(text = "Save Rating")
                }
                else if (DrinkersInfo.userFavList.find { DisplayDrink.drinkId.value == it.idDrink }
                        ?.hasRating() == true)
                    Text(text = "Edit Rating")
                else
                    Text(text = "Add Rating")
            }
            Divider(color = Color.Transparent, thickness = 1.dp, modifier = Modifier
                .background(borderGradient))

        }

    }


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

private fun resetFlags() {
    stringRating.value = ""
    intRating.value = 0
    isRating.value = false
}

@Composable
fun CreateBottomSpace(){ Spacer(modifier = Modifier.height(50.dp)) }