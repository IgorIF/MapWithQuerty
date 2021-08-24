package com.example.mapwithquerty.ui.screens

import android.Manifest
import android.location.Location
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.example.mapwithquerty.MainActivity
import com.example.mapwithquerty.ui.components.UserAvatar
import com.example.mapwithquerty.utils.getUserAvatarExtension
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalUnitApi
@ExperimentalPermissionsApi
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

@ExperimentalUnitApi
@ExperimentalPermissionsApi
@Composable
fun UsersListView(users: List<User>, picasso: Picasso, onUserClick: (User) -> Unit) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        items(users) {
            val animatedProgress = remember { Animatable(0.8f) }

            LaunchedEffect(Unit) {
                animatedProgress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 200, easing = LinearEasing)
                )
            }

            val cardModifier = Modifier
                .fillParentMaxWidth()
                .padding(8.dp)
                .graphicsLayer {
                    if (listState.isScrollInProgress) {
                        scaleX = animatedProgress.value
                        scaleY = animatedProgress.value
                    }
                }

            UserCardView(
                modifier = cardModifier,
                user = it,
                picasso = picasso,
                onUserClick = onUserClick
            )
        }
    }
}

@ExperimentalUnitApi
@ExperimentalPermissionsApi
@Composable
fun UserCardView(modifier: Modifier, user: User, picasso: Picasso, onUserClick: (User) -> Unit) {
    var image by remember { mutableStateOf<ImageBitmap?>(null)}
    val navigationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)
    val lastLocation = (LocalContext.current as MainActivity).lastLocation

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
                    text = "${user.dob.age} years"
                )

                if (lastLocation != null && navigationPermissionState.hasPermission) {
                    val results = FloatArray(1)
                    val distance = Location.distanceBetween(lastLocation.latitude, lastLocation.longitude, user.location.coordinates.latitude, user.location.coordinates.longitude, results)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Box(modifier = Modifier
                            .width(80.dp)
                            .border(1.dp, Color(0.804f, 0.914f, 0.675f, 0.5f), CircleShape)
                            .background(color = Color(0.804f, 0.914f, 0.675f, 0.114f), CircleShape)
                        ) {
                            Row(
                                modifier = Modifier.padding(start = 2.dp, top = 1.dp, bottom = 1.dp, end = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier.size(20.dp),
                                    imageVector = Icons.Outlined.LocationOn,
                                    contentDescription = "Location icon",
                                    tint = Color(0.804f, 0.914f, 0.675f, 0.6f)
                                )
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "${(results[0]/1000).toInt()} km",
                                    textAlign = TextAlign.End,
                                    fontSize = TextUnit(10f, TextUnitType.Sp),
                                    color = Color(0.612f, 0.706f, 0.498f, 0.9f)
                                )
                            }
                        }
                    }
                }

            }
        }
    }

}