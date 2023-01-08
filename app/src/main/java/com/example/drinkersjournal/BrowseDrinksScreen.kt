package com.example.drinkersjournal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun BrowseDrinksScreen(){
    SetBackgroundImage()

    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(16.dp)
    ){
        CreateIngredientCard(
            drinkName = "ass n tiddies",
            imageUrl = "https://www.thecocktaildb.com/images/ingredients/vodka-Medium.png"
        )
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
        elevation = 5.dp,
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