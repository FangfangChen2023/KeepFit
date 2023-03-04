package com.example.kotlin_2

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlin_2.data.model.DailyStatus
import com.example.kotlin_2.data.model.GoalItem
import com.example.kotlin_2.screen.UIEvent
import java.time.LocalDate

@Composable
fun GoalListItem(
    goalItem : GoalItem,
    onClick: () -> Unit,
    onEvent:(UIEvent) -> Unit,
){
    var color =
        if (goalItem.active){
            Color.Blue
        } else {
            Color.White
        }

    Row(
    modifier = Modifier
        .border(
            1.dp, Color.Blue,
            shape = RoundedCornerShape(12.dp)
        )
        .background(
            color,
            shape = RoundedCornerShape(12.dp)
        )
        .fillMaxSize()
        .padding(20.dp)
        .clickable { onClick() },
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if(goalItem.active){
            Text(
                text = goalItem.name,
                color = Color.White,
                //fontFamily = ,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${goalItem.steps}",
                color = Color.White,
                fontSize = 30.sp
            )
        }else {
            Column(
                modifier = Modifier.fillMaxHeight(),
            ) {
                Text(
                    text = goalItem.name,
                    color = Color.Black,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${goalItem.steps}",
                    color = Color.Black,
                    fontSize = 30.sp
                )
            }
            Switch(
                checked = false,
                onCheckedChange = {
                    if(it){
                        onEvent(UIEvent.SetGoalActive(goalItem))
                    }
                }
            )

        }

    }
}