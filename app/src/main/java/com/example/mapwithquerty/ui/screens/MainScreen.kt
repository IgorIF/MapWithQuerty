package com.example.mapwithquerty.ui.screens

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mapwithquerty.Application
import com.example.mapwithquerty.data.models.*
import com.example.mapwithquerty.di.MainScreenComponent
import com.example.mapwithquerty.utils.daggerViewModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception


@Composable
fun MainScreen(component: MainScreenComponent) {

    val viewModel = daggerViewModel { component.getViewModel() }

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(viewModel.users.value) {
            UserCardView(
                user = it,
                picasso = component.getPicasso()
            )
        }
    }

}

@Composable
fun UserCardView(user: User, picasso: Picasso?) {

    var image by remember { mutableStateOf<ImageBitmap?>(null)}

    SideEffect {
        val target = object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                image = bitmap?.asImageBitmap()
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
            override fun onPrepareLoad(placeHolderDrawable: Drawable?){}
        }

        picasso?.load(user.picture.large)?.into(target)
    }


    Card(modifier = Modifier
        .fillMaxWidth()) {
        Row(modifier = Modifier.padding(4.dp)) {


            if (image != null) {
                Image(
                    modifier = Modifier.size(70.dp).clip(CircleShape),
                    bitmap = image!!,
                    contentDescription = "icon"
                )
            } else {
                Canvas(modifier = Modifier.size(70.dp)) {
                    drawCircle(color = Color.Black)
                }
            }

            image?.let {



            }
            Text(text = user.name.first)
        }

    }

}


@Preview(name = "UserCardView")
@Composable
fun UserCardViewPreview() {
    UserCardView(
        User(
            "male",
            Name("Jack", "Barker"),
            Location(
                "Houston",
                "Texas",
                Coordinates("29.749907", "-95.358421")
            ),
            "jackbarker@email.com",
            Picture("https://randomuser.me/api/portraits/men/75.jpg")
        ), null
    )
}