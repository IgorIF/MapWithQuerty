package com.example.mapwithquerty.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.example.mapwithquerty.data.models.*
import com.example.mapwithquerty.di.UserScreenComponent
import com.example.mapwithquerty.utils.getUserAvatarExtension

@Composable
fun UserScreen(component: UserScreenComponent, user: User) {
    
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var image by remember { mutableStateOf<ImageBitmap?>(null) }

            SideEffect {
                component.getPicasso().getUserAvatarExtension(
                    user = user,
                    onSuccess = {image = it}
                )
            }

            if (image != null) {
                Image(
                    modifier = Modifier
                        .size(260.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape),
                    bitmap = image!!,
                    contentDescription = "avatar"
                )
            }

            Spacer(Modifier.height(30.dp))
            
            Text(
                text = "${user.name.first} ${user.name.last}",
                style = MaterialTheme.typography.h5
            )

            Spacer(Modifier.height(30.dp))
            
            Divider()

        }
    }
    
}

/*
@Preview
@Composable
fun UserScreenPreview() {
    UserScreen(
        component = (LocalContext.current.applicationContext as Application).appComponent.userScreenComponent().create(),
        user = User(
            "male",
            Name("Jack", "Barker"),
            Location(
                "Houston",
                "Texas",
                Coordinates("29.749907", "-95.358421")
            ),
            "jackbarker@email.com",
            Dob("1993-07-20T09:44:18.674Z", 26),
            Picture("https://randomuser.me/api/portraits/men/75.jpg")
        )
    )
}*/
