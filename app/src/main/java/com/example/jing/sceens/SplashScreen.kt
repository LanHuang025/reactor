package com.example.jing

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.jing.activity.MainActivity
import com.example.jing.activity.SplashActivity

@Composable
fun SplashScreen(context: Context){
    Translatebar()
    val color:Color = if (isSystemInDarkTheme()){
        Color.White
    }else{
        Color.Black
    }
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.splash))
    val progress by animateLottieCompositionAsState(composition = composition)
    if (progress==1.0f){
        context.startActivity(Intent(context, MainActivity::class.java))
        SplashActivity().finish()
    }
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        LottieAnimation(composition = composition, progress = {progress})
        Text(text = "反应堆", fontSize = 60.sp,color=color,
            modifier = Modifier.padding(16.dp))
        CircularProgressIndicator(modifier = Modifier.padding(16.dp), color = Color(0xFFF48FB1))
    }
}