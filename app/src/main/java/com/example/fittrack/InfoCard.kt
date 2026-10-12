package com.example.fittrack

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlin.math.max

data class TextData(
    val text: String,
    val fontSize: TextUnit = TextUnit.Unspecified,
    val fontWeight: FontWeight = FontWeight.Normal,
    val color: Color = Color.Black,
    val isBorderVisible: Boolean = false,
    val backgroundColor: Color = Color.Transparent
) {
    companion object {
        val borderShape = RoundedCornerShape(10.dp)
        val blank = TextData("")
    }

    fun isBlank(): Boolean = text.isBlank()
    fun isNotBlank(): Boolean = text.isNotBlank()
}

@Composable
private fun Text(text: TextData) {

    var modifier = Modifier
        .clip(TextData.borderShape)
        .background(text.backgroundColor)
    if (text.isBorderVisible) {
        modifier = modifier.border(1.dp, Color.Black, TextData.borderShape)
    }

    Box(modifier) {
        Text(
            text = text.text,
            modifier = Modifier.padding(5.dp, 1.dp, 5.dp, 1.dp),
            fontSize = text.fontSize,
            fontWeight = text.fontWeight,
            color = text.color
        )
    }
}

//All texts in column1 should be aligned to the start
//All texts in column2 should be aligned to the end
//A row is skipped if both columns are blank at the same index

@Composable
private fun TextRow(text1: TextData = TextData.blank, text2: TextData = TextData.blank) {
    if (text1.isBlank() && text2.isBlank()) return

    val arrangement: Arrangement.Horizontal =
        if (text2.isBlank()) Arrangement.Start
        else if (text1.isBlank()) Arrangement.End
        else Arrangement.SpaceBetween
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = arrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (text1.isNotBlank()) Text(text1)
        if (text2.isNotBlank()) Text(text2)
    }
}

@Composable
private fun TextRows(column1Texts: List<TextData>, column2Texts: List<TextData>) {
    val numRows = max(column1Texts.size, column2Texts.size)
    for (i in 0 until numRows) {
        if (i >= column1Texts.size) {
            TextRow(text2 = column2Texts[i])
        } else if (i >= column2Texts.size) {
            TextRow(text1 = column1Texts[i])
        } else {
            TextRow(column1Texts[i], column2Texts[i])
        }

    }
}

data class InfoCardData(
    val column1Texts: List<TextData> = emptyList(),
    val column2Texts: List<TextData> = emptyList(),
)

@Composable
fun InfoCard(item: Item, onClick: (() -> Unit)? = null) {
    val data = item.toInfoCardData()
    InfoCard(data.column1Texts, data.column2Texts, onClick)
}

//This Card will have 2 columns and max(c1.size, c2.size) rows.
//Column1 texts will be displayed on the left side and column2 texts
//will be displayed on the right.
@Composable
fun InfoCard(
    column1Texts: List<TextData> = emptyList(),
    column2Texts: List<TextData> = emptyList(),
    onClick: (() -> Unit)? = null
) {
    BaseCard(onClick = onClick) {
        TextRows(column1Texts, column2Texts)
    }
}

//There will be no visual changes to the card if it's disabled.
@Composable
fun BaseCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable (ColumnScope.() -> Unit)
) {
    val cd = CardDefaults
    val color = Color.White
    val elevation = 5.dp

    Card(
        onClick = onClick ?: {},
        modifier = modifier,
        enabled = onClick != null,
        colors = cd.cardColors(containerColor = color, disabledContainerColor = color),
        elevation = cd.cardElevation(defaultElevation = elevation, disabledElevation = elevation),
    ) {
        Column(
            Modifier
                .padding(5.dp, 9.dp, 5.dp, 9.dp)
                .fillMaxWidth(),
            Arrangement.spacedBy(8.dp),
            Alignment.CenterHorizontally,
            content = content
        )
    }
}

@Composable
fun InfoCardList(modifier: Modifier = Modifier, items: List<Item>, onNavigateTo: ((Int) -> Unit)? = null) {

    LazyColumn(
        modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(items, { it.id }) {
            var onClick: (() -> Unit)? = null
            if(onNavigateTo != null) onClick = { onNavigateTo(it.id) }

            InfoCard(it, onClick = onClick)
        }
        //item{} will work as a bottom spacer so that there is room for the last card's shadow
        item{}
    }

}