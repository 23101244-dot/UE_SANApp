package com.example.uesanapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.room.Room
import coil.compose.rememberAsyncImagePainter
import com.example.uesanapp.data.model.AppDatabase
import com.example.uesanapp.data.model.CountryEntity

@Composable
fun FavoritesScreen(navController: NavHostController) {
    val context = LocalContext.current

    // Conexión a la base de datos local
    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "countries-db"
        ).allowMainThreadQueries().build()
    }

    // Lista local que se llenará con los datos de Room
    var favoriteCountries by remember { mutableStateOf(listOf<CountryEntity>()) }

    // Traemos los datos de Room al abrir la pantalla
    LaunchedEffect(Unit) {
        favoriteCountries = db.countryDao().getAllFavorites()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mis Favoritos (Room Local)", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        if (favoriteCountries.isEmpty()) {
            Text("Aún no tienes favoritos guardados localmente.")
        } else {
            LazyColumn {
                items(favoriteCountries) { country ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                contentDescription = country.name,
                                modifier = Modifier.size(64.dp),
                                contentScale = ContentScale.Crop,
                                painter = rememberAsyncImagePainter(country.imageUrl)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(country.name, style = MaterialTheme.typography.titleMedium)
                                Text("Ranking: ${country.ranking}")
                            }
                        }
                    }
                }
            }
        }
    }
}