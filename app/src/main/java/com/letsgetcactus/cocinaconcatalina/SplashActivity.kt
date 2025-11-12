package com.letsgetcactus.cocinaconcatalina


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import com.letsgetcactus.cocinaconcatalina.ui.screens.LoadingAppScreen
import kotlinx.coroutines.delay


@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            LoadingAppScreen()


            LaunchedEffect(Unit) {
                delay(3000)
                startActivity(Intent(this@SplashActivity, MainActivity::class.java)) //Goes to MainActivity
                finish() //finishes the activity so you can not navigate back
            }
        }
    }
}
