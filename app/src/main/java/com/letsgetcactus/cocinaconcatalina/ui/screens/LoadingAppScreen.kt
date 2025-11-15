package com.letsgetcactus.cocinaconcatalina.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.letsgetcactus.cocinaconcatalina.R
import com.letsgetcactus.cocinaconcatalina.ui.theme.CocinaConCatalinaTheme
import com.letsgetcactus.cocinaconcatalina.ui.theme.White

@Composable
fun LoadingAppScreen(){
    Column(
        modifier = Modifier.fillMaxSize()
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data (R.raw.icon_gif)
                .decoderFactory(coil.decode.GifDecoder.Factory())
                .build(),
            contentDescription = stringResource(R.string.loading),
            modifier = Modifier.size(120.dp)
        )
       
    }

}


@Preview
@Composable
fun PreviewLoadingApp(){
    CocinaConCatalinaTheme(darkTheme = false) {
        LoadingAppScreen()
    }
}