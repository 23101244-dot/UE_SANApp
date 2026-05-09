package com.example.uesanapp.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.uesanapp.data.model.CountryModel

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
fun HomeScreen(){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .statusBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bienvenido a la pantalla principal")
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn() {
            items(mockCountries){country->
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp)

                ) {
                    Row(modifier = Modifier.padding(12.dp)) {
                        Image(
                            contentDescription = country.name,
                            modifier = Modifier.size(64.dp),
                            contentScale = ContentScale.Crop,
                            painter = rememberAsyncImagePainter(country.imageUrl)
                        )
                        Spacer(modifier = Modifier.height(9.dp))
                        Column {
                            Text(country.name,style= MaterialTheme.typography.titleMedium)
                            Text("Ranking FIFA 2026:${country.ranking}")
                        }

                    }

                }
            }
        }
    }
}