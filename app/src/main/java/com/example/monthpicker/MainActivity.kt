package com.example.monthpicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.monthpicker.ui.screens.MonthPicker
import com.example.monthpicker.ui.theme.MonthPickerTheme
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonthPickerTheme {
                val visible = remember{mutableStateOf(false)}
                val date = remember { mutableStateOf("") }
                val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
                val currentYear = Calendar.getInstance().get(Calendar.YEAR)
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
                        visible.value = true
                    }) {
                        Text(text = "Month Picker",
                        modifier = Modifier.padding(10.dp))
                    }
                    if(visible.value) {
                        MonthPicker(
                            visible = visible.value,
                            currentMonth = currentMonth,
                            currentYear = currentYear,
                            positiveClick = { month, year ->
                                date.value = "$month/$year"
                                visible.value = false
                            },
                            negativeClick = {
                                visible.value = false
                            })
                    }
                    Text(text = date.value, modifier = Modifier.clickable { visible.value = true }
                        .padding(20.dp))
                }
            }
        }
    }
}
