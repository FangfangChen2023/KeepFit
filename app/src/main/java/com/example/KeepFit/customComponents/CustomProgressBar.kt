package com.example.KeepFit.customComponents

import kotlin.math.roundToInt
//import DataBaseHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.KeepFit.data.model.DailyStatus

/*var goalRepository by mutableStateOf(GoalRepository())
var activeGoal by mutableStateOf(goalRepository.getActiveGoal())
var stepGoal by mutableStateOf(activeGoal?.steps)*/


@Composable
fun CustomProgressBar(
    currentDaily: DailyStatus,
    canvasSize: Dp = 333.dp,
    indicatorValue: Int = 0,
    backgroundIndicatorColor: Color = Color.Gray.copy(alpha = 0.1f),
    backgroundIndicatorStrokeWidth: Float = 50f,
    //foregroundIndicatorColor: Color = Color.Magenta,
    foregroundIndicatorStrokeWidth: Float = 50f,

    //smallText: String = "Goal: default",
    smallTextColor: Color = Color.Gray.copy(alpha = 0.6f),
    smallText2Color: Color = Color.Gray.copy(alpha = 0.6f),
    stepsInfoColor: Color = Color.Black,
    smallTextFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    smallText2FontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    stepsInfoFontSize: TextUnit = MaterialTheme.typography.h4.fontSize,

    ) {

    //var currentDaily = homeViewModel.dailyDB.value
    var stepGoal by remember { mutableStateOf(currentDaily.goalSteps)}
    var currentGoalName by remember { mutableStateOf(currentDaily.goalName)}

    //val context = LocalContext.current
    //var activeGoal = GoalItem("test", 5000, true)
    /*val db = DataBaseHandler(context)
    var activeGoal by remember {mutableStateOf(db.getActiveGoal())}*/
    //var stepGoal by remember { mutableStateOf(activeGoal.steps)}
    var maxIndicatorValue by remember { mutableStateOf(stepGoal)}
    var allowedMaxIndicatorValue by remember {
        mutableStateOf(maxIndicatorValue)
    }

    allowedMaxIndicatorValue = if (indicatorValue <= maxIndicatorValue!!) {
        indicatorValue
    } else {
        maxIndicatorValue
    }

    var smallText: String = "Goal: " + (currentGoalName)


    var animatedIndicatorValue by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = allowedMaxIndicatorValue) {
        animatedIndicatorValue = allowedMaxIndicatorValue!!.toFloat()
    }

    val sweepAngle by animateFloatAsState(
        targetValue = ((animatedIndicatorValue / maxIndicatorValue) * 100 * 3.6).toFloat(),
        animationSpec = tween(1000)
    )
    val animatedStepsInfo by animateColorAsState(
        targetValue = if(allowedMaxIndicatorValue == 0){
            Color.Gray.copy(alpha = 0.6f)
        }else {
            stepsInfoColor
        }
    )

    var foregroundIndicatorColor: Color = Color.Red
    val stepsInfo = "$indicatorValue/$maxIndicatorValue"
    //var percentage by remember {mutableStateOf((indicatorValue/maxIndicatorValue) * 100)}
    var percentage = ((indicatorValue.toDouble()/maxIndicatorValue) * 100).roundToInt()
    var smallText2 = "$percentage%" //by remember {mutableStateOf("$percentage%")}
    if (percentage >= 100) {foregroundIndicatorColor = Color.Green}
    else if (percentage >= 65 && percentage < 100) {foregroundIndicatorColor = Color.Yellow}
        Column(
        modifier = Modifier
            .size(canvasSize)
            .drawBehind {
                val barSize = size / 1.25f
                backgroundIndicator(
                    indicatorColor = backgroundIndicatorColor,
                    componentSize = barSize,
                    indicatorStrokeWidth = backgroundIndicatorStrokeWidth
                )
                foregroundIndicator(
                    indicatorColor = foregroundIndicatorColor,
                    indicatorStrokeWidth = foregroundIndicatorStrokeWidth,
                    componentSize = barSize,
                    sweepAngle = sweepAngle
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElementInsideBar(
            smallText = smallText,
            stepsInfo = stepsInfo,
            smallText2 = smallText2,
            smallTextColor = smallTextColor,
            smallText2Color = smallText2Color,
            stepsInfoColor = animatedStepsInfo,
            smallTextFontSize = smallTextFontSize,
            smallText2FontSize = smallText2FontSize,
            stepsInfoFontSize = stepsInfoFontSize,

            )

    }
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 270f,
        sweepAngle = 360f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.height - componentSize.height) / 2f,
            y = (size.width - componentSize.width) / 2f,
        )
    )

}

fun DrawScope.foregroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
    sweepAngle: Float
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 270f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            x = (size.height - componentSize.height) / 2f,
            y = (size.width - componentSize.width) / 2f,
        )
    )
}

@Composable
fun ElementInsideBar(
    smallText: String,
    stepsInfo: String,
    smallText2: String,
    smallTextColor: Color,
    smallText2Color: Color,
    stepsInfoColor: Color,
    smallTextFontSize: TextUnit,
    smallText2FontSize: TextUnit,
    stepsInfoFontSize: TextUnit
) {
    Text(
        text = smallText,
        color = smallTextColor,
        fontSize = smallTextFontSize,
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.height(15.dp))
    Text(
        text = stepsInfo,
        color = stepsInfoColor,
        fontSize = stepsInfoFontSize,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    Spacer(Modifier.height(15.dp))
    Text(
        text = smallText2,
        color = smallText2Color,
        fontSize = smallText2FontSize,
        textAlign = TextAlign.Center
    )
}