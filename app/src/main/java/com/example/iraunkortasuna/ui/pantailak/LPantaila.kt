package com.example.iraunkortasuna.ui.pantailak

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

//DataStore-rekin lan egitea: libs.versions.toml, eta build.gradle.kta aldatu behar dira
val Context.dataStore by preferencesDataStore(name = "datos")

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun LPantaila(navController: NavHostController) {
    val usuarioKey = stringPreferencesKey("usuario")
    val recordarKey = booleanPreferencesKey("recordar")

    val context = LocalContext.current
    //para la corrutina
    val scope = rememberCoroutineScope()

    var usuario by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var recordarUsuario by remember {
        mutableStateOf(false)
    }
    val datosGuardados by context.dataStore.data.collectAsState(
        initial = null
    )

    LaunchedEffect(datosGuardados) {
        datosGuardados?.let { prefs ->
            if (prefs[recordarKey] == true) {
                usuario = prefs[usuarioKey] ?: ""
                recordarUsuario = true
            }
        }
    }
    //la key del archvio va a ser infromacion
    val informacionKey = stringPreferencesKey("informacion")
    //el texto del out Line TextFiled
    var texto by remember {
        mutableStateOf("")
    }

    //esto contedra el daro xonetso de la store
    val textoD by context.dataStore.data
        .map { prefs ->
            prefs[informacionKey] ?: ""
        }
        .collectAsState(initial = "")

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){

        Spacer(modifier = Modifier.height(160.dp))

        Text("Iniciar sesión")

        OutlinedTextField(
            value = usuario,
            onValueChange = {
                usuario = it
            },
            label = {
                Text("Usuario")
            }
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Contraseña")
            }
        )
        Row(    verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = recordarUsuario,
                onCheckedChange = {
                    recordarUsuario = it
                }
            )

            Text("Remember user")
            Button(
                onClick = {

                    scope.launch {

                        context.dataStore.edit { prefs ->

                            if (recordarUsuario) {
                                prefs[usuarioKey] = usuario
                                prefs[recordarKey] = true
                            } else {
                                prefs.remove(usuarioKey)
                                prefs[recordarKey] = false
                            }

                        }

                        // Aquí irá la comprobación del login
                        // y la navegación a la siguiente pantalla.
                    }
                }
            ) {
                Text("LOGIN")
            }

        }
        Text("Dato guardado: $textoD")
    }
}


