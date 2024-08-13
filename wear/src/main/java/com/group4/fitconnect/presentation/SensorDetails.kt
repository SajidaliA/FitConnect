package com.group4.fitconnect.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.group4.fitconnect.R

@Composable
fun SensorDetails(
    currentSteps: Int,
    heartRate: Int,
    shareClicked: () -> Unit,
    resetClicked: () -> Unit
) {
    var isRunning by remember { mutableStateOf(false) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 10.sp,
            color = MaterialTheme.colors.primary,
            text = stringResource(R.string.steps)
        )
        Text(
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            text = currentSteps.toString()
        )
        Text(
            fontSize = 8.sp,
            color = colorResource(id = R.color.grey_line),
            text = "5000"
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        shape = CircleShape, color = colorResource(
                            id = R.color.background_light
                        )
                    )
            ) {
                Image(
                    modifier = Modifier.size(8.dp),
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.border_blue)),
                    painter = painterResource(id = R.drawable.heart_bit),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    text = heartRate.toString()
                )
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(1.dp),
                    fontSize = 6.sp,
                    color = MaterialTheme.colors.primary,
                    text = stringResource(id = R.string.hear_bit)
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Image(
                modifier = Modifier
                    .clickable {
                        isRunning = !isRunning
                        resetClicked()
                    }
                    .size(40.dp)
                    .background(
                        color = colorResource(id = R.color.background_light),
                        CircleShape,
                    )
                    .padding(12.dp),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.accent)),
                painter = painterResource(id = R.drawable.reset),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(5.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        shape = CircleShape, color = colorResource(
                            id = R.color.background_light
                        )
                    )
            ) {
                Image(
                    modifier = Modifier
                        .size(8.dp),
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.border_blue)),
                    painter = painterResource(id = R.drawable.calories),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    text = (currentSteps * 0.045).toString()
                )
                Text(
                    modifier = Modifier.padding(1.dp),
                    fontSize = 6.sp,
                    color = MaterialTheme.colors.primary,
                    text = stringResource(id = R.string.kcal)
                )
            }
        }
        Share(shareClicked)
    }
}
