package com.letsgetcactus.cocinaconcatalina.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun ListRecipeHostScreen(
){
    ListRecipeContent()
}

@Composable
fun ListRecipeContent(
){
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        item { RecipeCard() }
    }


}

@Composable
private fun RecipeCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = MaterialTheme.shapes.small


    ){
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(250.dp)
        ) {
            //TODO: change recipe data for ddbb
            Image(
                painter = painterResource(R.drawable.recipe),
                contentDescription = stringResource(R.string.image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)
                    .background(Color.Black.copy(alpha = 0.4f))
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "Rica receta sencilla",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    text = "Esta receta es maravillosa",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListRecipeHost() {
    CocinaConCatalinaTheme(darkTheme = false) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(5) { // Simulamos 5 tarjetas
                RecipeCard()
            }
        }
    }
}
