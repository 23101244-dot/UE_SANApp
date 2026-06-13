package com.example.uesanapp.presentation.home

import android.widget.Toast
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
import coil.compose.rememberAsyncImagePainter
import com.example.uesanapp.data.model.CountryEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun FavoritesScreen(navController: NavHostController) {
    val context = LocalContext.current
    val firestore = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val uid = auth.currentUser?.uid

    var favoriteCountries by remember { mutableStateOf(listOf<CountryEntity>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(uid) {
        if (uid != null) {
            // Leemos la subcolección específica del usuario logueado
            firestore.collection("usuarios")
                .document(uid)
                .collection("favoritos")
                .get()
                .addOnSuccessListener { documents ->
                    val list = documents.map { doc ->
                        CountryEntity(
                            name = doc.getString("name") ?: "",
                            ranking = (doc.getLong("ranking") ?: 0).toInt(),
                            imageUrl = doc.getString("imageUrl") ?: ""
                        )
                    }
                    favoriteCountries = list
                    isLoading = false
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error al cargar favoritos", Toast.LENGTH_SHORT).show()
                    isLoading = false
                }
        } else {
            isLoading = false
        }
    }

    Column(modifier = Modifier.padding(16.dp).fillMaxSize().statusBarsPadding()) {
        Text("Mis Favoritos (Nube)", style = MaterialTheme.typography.titleLarge)

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (favoriteCountries.isEmpty()) {
            Text("No tienes favoritos guardados en la nube.")
        } else {
            LazyColumn {
                items(favoriteCountries) { country ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                        Row(modifier = Modifier.padding(12.dp).fillMaxWidth()) {
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