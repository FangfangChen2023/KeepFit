package com.example.kotlin_2

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlin_2.data.model.HistoryItem

@Composable
fun HistoryListItem(
    historyItem: HistoryItem,
    onClick: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = historyItem.date,
            color = Color.Gray,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
    Divider(
        modifier = Modifier,
        color = Color.LightGray,
        startIndent = 1.dp
    )
    
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = historyItem.name,
            color = Color.Black,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "${historyItem.steps}",
            color = Color.Black,
            fontSize = 25.sp
        )

    }
    
    Spacer(modifier = Modifier.height(20.dp))
}