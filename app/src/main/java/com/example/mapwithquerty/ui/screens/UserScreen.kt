package com.example.mapwithquerty.ui.screens

import android.widget.ZoomControls
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mapwithquerty.data.models.*
import com.example.mapwithquerty.di.UserScreenComponent
import com.example.mapwithquerty.ui.components.UserAvatar
import com.example.mapwithquerty.utils.getUserAvatarExtension
import com.example.mapwithquerty.utils.rememberMapViewWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.launch

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

            UserAvatar(
                size = 260.dp,
                bitmap = image
            )

            Spacer(Modifier.height(30.dp))
            
            Text(
                text = "${user.name.first} ${user.name.last}",
                style = MaterialTheme.typography.h5
            )

            Spacer(Modifier.height(30.dp))
            
            Divider()


            val mapView = rememberMapViewWithLifecycle()
            MapViewContainer(map = mapView, latitude = "50", longitude = "40")

        }
    }
    
}


@Composable
private fun MapViewContainer(
    map: MapView,
    latitude: String,
    longitude: String
) {
    val cameraPosition = remember(latitude, longitude) {
        LatLng(latitude.toDouble(), longitude.toDouble())
    }

    LaunchedEffect(map) {
        val googleMap = map.awaitMap()
        googleMap.addMarker { position(cameraPosition) }
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
    }

    var zoom by rememberSaveable(map) { mutableStateOf(5f) }
/*    ZoomControls(zoom) {
        zoom = it.coerceIn(2f, 20f)
    }*/

    val coroutineScope = rememberCoroutineScope()
    AndroidView({ map }) { mapView ->
        // Reading zoom so that AndroidView recomposes when it changes. The getMapAsync lambda
        // is stored for later, Compose doesn't recognize state reads
        val mapZoom = zoom
        coroutineScope.launch {
            val googleMap = mapView.awaitMap()
            //googleMap.setZoom(mapZoom)
            // Move camera to the same place to trigger the zoom update
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(cameraPosition))
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
