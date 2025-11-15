package com.letsgetcactus.cocinaconcatalina.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme

@Composable
fun ImageAndTextComponent(
    textToShow: Int,
    colorText: Color,
    textStyle: TextStyle,
    imgToShow: Int,
    imgDescription: Int,
    iconSize:Int
){
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(imgToShow),
            contentDescription = imgDescription.toString(),
            modifier = Modifier.size(iconSize.dp)
        )
        Spacer(Modifier.size(8.dp))
        Text(text = stringResource(textToShow),
            color= colorText,
            style = textStyle
          )
    }
}


@Preview
@Composable
fun PreviewImageAndText(){
    CocinaConCatalinaTheme(darkTheme = false) {
        ImageAndTextComponent(
            textToShow = R.string.ingredient,
            colorText = MaterialTheme.colorScheme.primary,
            textStyle = MaterialTheme.typography.bodyMedium,
            imgToShow = R.drawable.icon,
            imgDescription = R.string.image_description,
            iconSize = 32
        )
    }
}