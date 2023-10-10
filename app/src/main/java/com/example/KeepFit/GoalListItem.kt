package com.example.KeepFit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.KeepFit.data.model.GoalItem

@Composable
fun GoalListItem(goalItem : GoalItem, onClick: () -> Unit){
    var color =
        if (goalItem.active){
            Color.Blue
        } else {
            Color.Transparent
        }

    Row(
    modifier = Modifier
        .border(1.dp, Color.Blue,
        shape = RoundedCornerShape(12.dp)
        )
        .background(
            color,
            shape = RoundedCornerShape(12.dp)
        )
        .fillMaxSize()
        .padding(24.dp)
        .clickable { onClick() },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (goalItem.active){
        Text(
            text = goalItem.name,
            color = Color.White,
            //fontFamily = ,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${goalItem.steps}",
            color = Color.White,
            fontSize = 30.sp
        ) }
        else {
            Text(
                text = goalItem.name,
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${goalItem.steps}",
                color = Color.Black,
                fontSize = 30.sp
            )
        }
    }
}