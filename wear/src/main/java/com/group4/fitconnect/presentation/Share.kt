package com.group4.fitconnect.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.group4.fitconnect.R

@Composable
fun Share(shareData: () -> Unit) {
    Spacer(modifier = Modifier.height(10.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .height(20.dp)
            .background(
                color = colorResource(id = R.color.background_light),
                RoundedCornerShape(20.dp),
            )
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .clickable { shareData() },
    ) {
        Image(
            modifier = Modifier
                .size(7.dp),
            colorFilter = ColorFilter.tint(colorResource(id = R.color.border_blue)),
            painter = painterResource(id = R.drawable.share),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = stringResource(id = R.string.share),
            fontSize = 7.sp,
            color = MaterialTheme.colors.primary)

    }
}