package com.example.uesanapp.presentation.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
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
import com.example.uesanapp.data.model.CountryModel
import com.google.firebase.firestore.FirebaseFirestore

val mockCountries = listOf(
    CountryModel( name = "Colombia", ranking=5, imageUrl="https://flagcdn.com/w320/co.png" ),
    CountryModel( name = "Francia", ranking=3, imageUrl="https://flagcdn.com/w320/fr.png" ),
    CountryModel( name = "Brasil", ranking=8, imageUrl="https://flagcdn.com/w320/br.png" ),
    CountryModel( name = "España", ranking=2, imageUrl="https://flagcdn.com/w320/es.png" ),
    CountryModel( name = "Portugal", ranking=7, imageUrl="https://flagcdn.com/w320/pt.png" ),
    CountryModel( name = "Argentina", ranking=1, imageUrl="https://flagcdn.com/w320/ar.png" ),
    CountryModel( name = "Japon", ranking=10, imageUrl="https://flagcdn.com/w320/jp.png" ),
    CountryModel( name = "Peru", ranking=50, imageUrl="https://flagcdn.com/w320/pe.png" )
)

@Composable
fun HomeScreen(navController: NavHostController){
    val context = LocalContext.current
    val firestore = remember { FirebaseFirestore.getInstance() }

    val db = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "countries-db"
        ).allowMainThreadQueries().build()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Ranking FIFA 2026", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(mockCountries) { country ->
                // Verificamos si ya existe en Room al iniciar
                val existsInRoom = remember { db.countryDao().getAllFavorites().any { it.name == country.name } }
                var isFavorite by remember { mutableStateOf(existsInRoom) }

                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Row(
                        modifier = Modifier.padding(12.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                contentDescription = country.name,
                                modifier = Modifier.size(64.dp),
                                contentScale = ContentScale.Crop,
                                painter = rememberAsyncImagePainter(country.imageUrl)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(country.name, style = MaterialTheme.typography.titleMedium)
                                Text("Ranking FIFA 2026: ${country.ranking}")
                            }
                        }

                        IconButton(onClick = {
                            isFavorite = !isFavorite
                            val entity = CountryEntity(country.name, country.ranking, country.imageUrl)

                            if (isFavorite) {
                                // Guardar en ambos
                                db.countryDao().insertFavorite(entity)
                                firestore.collection("favoritos").document(country.name).set(country)
                                Toast.makeText(context, "Guardado en local y nube", Toast.LENGTH_SHORT).show()
                            } else {
                                // Borrar en ambos
                                db.countryDao().deleteFavorite(entity)
                                firestore.collection("favoritos").document(country.name).delete()
                                Toast.makeText(context, "Eliminado de local y nube", Toast.LENGTH_SHORT).show()
                            }
                        }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorito",
                                tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}