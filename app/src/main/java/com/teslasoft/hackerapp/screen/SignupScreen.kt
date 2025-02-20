package com.teslasoft.hackerapp.screen

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.teslasoft.hackerapp.MainActivity
import com.teslasoft.hackerapp.R
import com.teslasoft.hackerapp.component.AppBar

@ExperimentalMaterial3Api
@Composable
fun SignupScreen(activity: Activity? = null) {

    val login = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val passwordConfirmation = remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppBar(activity = activity, accountAccess = false ) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .defaultMinSize(minHeight = 300.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0x88095B3D),
                            MaterialTheme.colorScheme.background
                        ), startY = 0.0f,
                        endY = 540.0f
                    )
                )
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = "Signup",
                    modifier = Modifier
                        .align(Alignment.Start),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        fontSize = 30.sp, color = MaterialTheme.colorScheme.outline
                    ),
                    fontFamily = FontFamily(Font(R.font.alata_regular)),
                    fontWeight = FontWeight(500)
                )


                OutlinedTextField(
                    shape = RoundedCornerShape(16.dp),
                    value = login.value,
                    onValueChange = { login.value = it },
                    label = { Text("login") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    leadingIcon =  {Icon(
                        Icons.Filled.Person,
                        contentDescription = "Username",
                        tint = MaterialTheme.colorScheme.outline
                    )}
                )

                OutlinedTextField(
                    shape = RoundedCornerShape(16.dp),
                    value = password.value,
                    onValueChange = { password.value = it },
                    visualTransformation = PasswordVisualTransformation(),
                    label = { Text("Password") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    leadingIcon =  {Icon(
                        Icons.Filled.Lock,
                        contentDescription = "Password",
                        tint = MaterialTheme.colorScheme.outline
                    )}
                )

                OutlinedTextField(
                    shape = RoundedCornerShape(16.dp),
                    value = passwordConfirmation.value,
                    onValueChange = { passwordConfirmation.value = it },
                    visualTransformation = PasswordVisualTransformation(),
                    label = { Text("Confirm password") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onBackground,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    leadingIcon =  {Icon(
                        Icons.Filled.Lock,
                        contentDescription = "Password",
                        tint = MaterialTheme.colorScheme.outline
                    )}
                )


                Button(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                    onClick = {

                        if (login.value == "" || password.value == "") {
                            Toast.makeText(activity, "Missing input(s)", Toast.LENGTH_SHORT)
                                .show()
                        }else if(password.value != passwordConfirmation.value){
                            Toast.makeText(activity, "Passwords do not match", Toast.LENGTH_SHORT)
                                .show()
                        }
                        else {
                           val firebaseAuth = FirebaseAuth.getInstance()
//
//                            firebaseAuth.signInWithEmailAndPassword(login.value, password.value)
//                                .addOnSuccessListener { user ->
//                                    run {
//                                        // process user data
//                                        activity?.startActivity(
//                                            Intent(
//                                                activity,
//                                                MainActivity::class.java
//                                            )
//                                        )
//                                    }
//                                }.addOnFailureListener { error ->
//                                    run {
//                                        // Error
//                                        activity?.runOnUiThread {
//                                            MaterialAlertDialogBuilder(activity)
//                                                .setTitle("Error")
//                                                .setMessage(error.message)
//                                                .setPositiveButton("OK") { _, _ -> }
//                                                .setCancelable(false)
//                                                .show()
//                                        }
//                                    }
//                                }

                        firebaseAuth.createUserWithEmailAndPassword(login.value, password.value).addOnSuccessListener { user -> run {
                            // process user data
                            activity?.startActivity(
                                Intent(
                                    activity,
                                    MainActivity::class.java
                                )
                            )
                        } }.addOnFailureListener { error -> run {
                            // Error
                            activity?.runOnUiThread {
                                MaterialAlertDialogBuilder(activity)
                                    .setTitle("Error")
                                    .setMessage(error.message)
                                    .setPositiveButton("OK") { _, _ -> }
                                    .setCancelable(false)
                                    .show()
                            }
                        }}

//                        if(firebaseAuth.currentUser == null) {
//                            // user is signed in
//                        } else {
//                            // user is not signed in
//                        }
                        }

                    }) {
                    Text(
                        text = "Signup", style = TextStyle(
                            fontSize = 18.sp, color = MaterialTheme.colorScheme.background
                        ), fontFamily = FontFamily(Font(R.font.alata_regular)),
                        fontWeight = FontWeight(500)
                    )
                }


            }

        }
    }

}
