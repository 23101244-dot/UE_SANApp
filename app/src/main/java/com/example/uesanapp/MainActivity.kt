package com.example.uesanapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.uesanapp.presentation.navigation.AppNavGraph
import com.example.uesanapp.ui.theme.UESANAppTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.firestoreSettings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- INICIO DE LA CONFIGURACIÓN DE PERSISTENCIA ---
        val db = FirebaseFirestore.getInstance()
        val settings = firestoreSettings {
            isPersistenceEnabled = true // Esto hace que trabaje offline
        }
        db.firestoreSettings = settings
        // --- FIN DE LA CONFIGURACIÓN ---

        enableEdgeToEdge()
        setContent {
            UESANAppTheme {
                AppNavGraph()
            }
        }
    }
}