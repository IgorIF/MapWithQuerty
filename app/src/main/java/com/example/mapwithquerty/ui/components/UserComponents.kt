package com.example.mapwithquerty.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun UserAvatar(size: Dp, bitmap: ImageBitmap?) {
    if (bitmap != null) {
        Image(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .border(1.dp, Color.Gray, CircleShape),
            bitmap = bitmap,
            contentDescription = "User avatar"
        )
    } else {
        Canvas(Modifier.size(size + 2.dp)) {
            drawCircle(
                color = Color.Gray,
                alpha = 0.2f
            )
        }
    }
}