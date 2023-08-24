package com.example.monthpicker.ui.screens

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.monthpicker.ui.theme.Purple40
import com.example.monthpicker.ui.theme.PurpleGrey40

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MonthPicker(
    visible: Boolean,
    currentMonth: Int,
    currentYear: Int,
    positiveClick: (Int, Int) -> Unit,
    negativeClick: () -> Unit
) {
    val months =
        listOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")
    val month = remember { mutableStateOf(months[currentMonth]) }
    val year = remember { mutableIntStateOf(currentYear) }

    val interactionSource = remember { MutableInteractionSource() }

    if (visible) {
        AlertDialog(
            containerColor = Color.White,
            title = {},
            text = {
                Column() {
                    Row(
                        modifier =Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Rounded.KeyboardArrowLeft, contentDescription = "",
                            modifier = Modifier
                                .size(35.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        year.intValue--
                                    }
                                ))

                        Text(
                            text = year.intValue.toString(),
                            modifier = Modifier.padding(horizontal = 20.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(imageVector = Icons.Rounded.KeyboardArrowRight,
                            contentDescription = "",
                            modifier = Modifier
                                .size(35.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = interactionSource,
                                    onClick = {
                                        year.intValue++
                                    }
                                ))
                    }

                    Card(
                        modifier = Modifier
                            .padding(15.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(0.dp)
                    ) {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth()
                                .background(Color.White)
                                .padding( vertical = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(
                                16.dp,
                                alignment = Alignment.CenterHorizontally
                            ),
                            verticalArrangement = Arrangement.spacedBy(
                                16.dp,
                                alignment = Alignment.CenterVertically
                            )
                        ) {
                            months.forEach {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clickable(
                                            indication = null,
                                            interactionSource = interactionSource,
                                            onClick = {
                                                month.value = it
                                            }
                                        )
                                        .background(Color.Transparent),
                                    contentAlignment = Alignment.Center
                                ) {
                                    val animatedSize = animateDpAsState(
                                        targetValue = if (month.value == it) 60.dp else 0.dp,
                                        animationSpec = tween(
                                            durationMillis = 500,
                                            easing = LinearOutSlowInEasing

                                        )
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(animatedSize.value)
                                            .background(
                                                color = if (month.value == it) Purple40 else Color.Transparent,
                                                shape = CircleShape
                                            )
                                    )
                                    Text(
                                        text = it,
                                        color = if (month.value == it) Color.White else Color.Black
                                    )

                                }
                            }
                        }
                    }
                }
            },
           confirmButton = {
               OutlinedButton(modifier = Modifier.padding(5.dp), onClick =  {
                   positiveClick(months.indexOf(month.value)+1,year.intValue)
               } ) {
               Text(text = "Ok")
           }
           },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(Purple40),
                    modifier = Modifier.padding(5.dp),
                     onClick = { negativeClick()}) {
                Text(text = "Cancel")
            } },
            onDismissRequest = {negativeClick()})
    }
}