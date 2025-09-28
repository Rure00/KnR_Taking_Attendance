package com.rure.knr_takingattendance.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rure.knr_takingattendance.R
import com.rure.knr_takingattendance.presentation.navigation.Destination
import com.rure.knr_takingattendance.ui.theme.TossBlue
import com.rure.knr_takingattendance.ui.theme.Typography
import com.rure.knr_takingattendance.ui.theme.White

@Composable
fun TopAppBarComponent(navController: NavController, screen: Destination) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .height(65.dp)
            .background(TossBlue),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if(screen.showBackBtn) {
            IconButton(onClick = { navController.navigateUp() }) {
                Image(
                    painter = painterResource(R.drawable.back_arrow),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }
        } else {
            Spacer(modifier = Modifier.size(10.dp))
        }

        Text(
            text = screen.label,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .weight(1f),
            style = Typography.titleMedium,
            color = White
        )

        if(screen.showOption) {
            IconButton(onClick = {
                navController.navigate(Destination.Option.route)
            }) {
                Image(
                    painter = painterResource(R.drawable.option_img),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}