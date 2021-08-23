package com.example.mapwithquerty.ui.screens

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mapwithquerty.data.models.*
import com.example.mapwithquerty.di.MainScreenComponent
import com.example.mapwithquerty.utils.daggerViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer


@Composable
fun MainScreen(component: MainScreenComponent, onUserClick: (User) -> Unit) {

    val viewModel = daggerViewModel { component.getViewModel() }
    
    SwipeRefresh(
        state = rememberSwipeRefreshState(false),
        onRefresh = { viewModel.getPersons(30) }
    ) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.users.value.isEmpty()) {
                CircularProgressIndicator()
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    items(viewModel.users.value) {
                        val animatedProgress = remember { Animatable(initialValue = 0.8f) }
                        LaunchedEffect(Unit) {
                            animatedProgress.animateTo(
                                targetValue = 1f,
                                animationSpec = tween(300, easing = LinearEasing)
                            )
                        }
                        val modifier = Modifier.fillParentMaxWidth().padding(8.dp).graphicsLayer { scaleY = animatedProgress.value; scaleX = animatedProgress.value }
                        UserCardView(
                            modifier = modifier,
                            user = it,
                            picasso = component.getPicasso(),
                            onUserClick = onUserClick
                        )
                    }
                }
            }
        }
    }

}


@Composable
fun UserCardView(modifier: Modifier, user: User, picasso: Picasso, onUserClick: (User) -> Unit) {

    var image by remember { mutableStateOf<ImageBitmap?>(null)}

    SideEffect {
        getAvatar(
            picasso = picasso,
            user = user,
            onSuccess = {image = it}
        )
    }

    Card(modifier = modifier
        .fillMaxWidth()
        .height(80.dp)
        .clickable(onClick = {onUserClick(user)})) {
        Row(modifier = Modifier
            .padding(4.dp)
        ) {

            if (image != null) {
                Image(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape),
                    bitmap = image!!,
                    contentDescription = "icon"
                )
            } else {
                Canvas(modifier = Modifier.size(72.dp)) {
                    drawCircle(
                        color = Color.Gray,
                        alpha = 0.2f
                    )
                }
            }
            
            Column(modifier = Modifier
                .weight(1f)
                .padding(start = 30.dp)
                .align(Alignment.Bottom)) {
                Text(
                    text = "${user.name.first} ${user.name.last}"
                )
                Text(modifier = Modifier.weight(1f),
                    text = "${user.dob.age} years"
                )
            }
        }
    }

}

/*@Preview(name = "UserCardView")
@Composable
fun UserCardViewPreview() {
    UserCardView( Modifier,
        User(
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
        ), , {}
    )
}*/

fun getAvatar(
    picasso: Picasso,
    user: User,
    onSuccess: (ImageBitmap?) -> Unit
) {
    val target = object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            onSuccess(bitmap?.asImageBitmap())
        }
        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {}
        override fun onPrepareLoad(placeHolderDrawable: Drawable?){}
    }
    picasso?.load(user.picture.large)?.into(target)
}