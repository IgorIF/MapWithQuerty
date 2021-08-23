package com.example.mapwithquerty.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import com.example.mapwithquerty.data.models.*
import com.example.mapwithquerty.di.MainScreenComponent
import com.example.mapwithquerty.utils.daggerViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.squareup.picasso.Picasso
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.ui.graphics.graphicsLayer
import com.example.mapwithquerty.ui.components.UserAvatar
import com.example.mapwithquerty.utils.getUserAvatarExtension

@Composable
fun MainScreen(component: MainScreenComponent, onUserClick: (User) -> Unit) {

    val viewModel = daggerViewModel { component.getViewModel() }

    SwipeRefresh(
        state = rememberSwipeRefreshState(false),
        onRefresh = { viewModel.getPersons() }
    ) {
        if (viewModel.users.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            UsersListView(
                users = viewModel.users.value,
                picasso = component.getPicasso(),
                onUserClick = onUserClick
            )
        }
    }

}

@Composable
fun UsersListView(users: List<User>, picasso: Picasso, onUserClick: (User) -> Unit) {
    val listState = rememberLazyListState()
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        items(users) {
            val animatedProgress = remember { Animatable(0.8f) }
            LaunchedEffect(Unit) {
                animatedProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 300, easing = LinearEasing)
                )
            }

            val modifier = Modifier
                .fillParentMaxWidth()
                .padding(8.dp)
                .graphicsLayer {
                    scaleX = animatedProgress.value
                    scaleY = animatedProgress.value
                }

            UserCardView(
                modifier = modifier,
                user = it,
                picasso = picasso,
                onUserClick = onUserClick
            )
        }
    }
}

@Composable
fun UserCardView(modifier: Modifier, user: User, picasso: Picasso, onUserClick: (User) -> Unit) {

    var image by remember { mutableStateOf<ImageBitmap?>(null)}

    SideEffect {
        picasso.getUserAvatarExtension(
            user = user,
            onSuccess = {image = it}
        )
    }

    Card(
        modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onUserClick(user) }
    ) {
        Row(Modifier.padding(4.dp)) {

            UserAvatar(
                size = 70.dp,
                bitmap = image
            )
            
            Column(
                Modifier
                    .weight(1f)
                    .padding(start = 30.dp)
                    .align(Alignment.Bottom)
            ) {
                Text(
                    text = "${user.name.first} ${user.name.last}"
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${user.dob.age} years"
                )
            }
        }
    }

}