package com.teslasoft.hackerapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.teslasoft.hackerapp.data.db.model.Term
import com.teslasoft.hackerapp.screen.TermsScreen
import com.teslasoft.hackerapp.ui.theme.HackerAppTheme

@ExperimentalMaterial3Api
class TermsActivity : ComponentActivity() {

    private var firebaseDatabase: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseDatabase = FirebaseDatabase.getInstance()

        //initializeDatabaseWithTerms()

        enableEdgeToEdge()
        setContent {
            HackerAppTheme {
                TermsActivityContent()
            }
        }

    }

    @Composable
    fun TermsActivityContent() {
        val termsList = remember { mutableStateListOf<Term>() }
     //   val isLoading = remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            getTerms(termsList)
          //  isLoading.value = false
        }

//        if (isLoading.value) {
//            CircularProgressIndicator()
//        } else {
            TermsScreen(this, termsList = termsList)
                //     }
    }

    fun getTerms(termsList: MutableList<Term>) {
        val dbReference: DatabaseReference = firebaseDatabase?.getReference("/terms") ?: return


        dbReference.get().addOnSuccessListener { data ->
            if (data.exists()) {
                val terms = mutableListOf<Term>()
                for (termSnapshot in data.children) {
                    val name = termSnapshot.child("name").getValue(String::class.java) ?: ""
                    val description =
                        termSnapshot.child("description").getValue(String::class.java) ?: ""
                    terms.add(Term(name, description))
                }
                // Update termsList with new data
                termsList.clear()  // Clear existing data
                termsList.addAll(terms)  // Add new data
            }
        }
            .addOnFailureListener { error ->
                Log.i("MyApp", error.message ?: "")
            }
    }


    // Set/modify
    fun setTerm(name: String, description: String) {
        val dbReference: DatabaseReference = firebaseDatabase?.getReference("/terms") ?: return
        val termData = mapOf("name" to name, "description" to description)

        dbReference.push().setValue(termData).addOnSuccessListener { state ->
            run {
                if (state != null) {
                    Log.i("MyApp", state.toString())
                } else {
                    Log.i("MyApp", "Term added successfully, but no state returned.")
                }
            }
        }
            .addOnFailureListener { error ->
                run {
                    Log.i("MyApp", error.message ?: "")
                }
            }
    }


    // Delete
    fun removeTerm(index: Int) {
        val dbReference: DatabaseReference =
            firebaseDatabase?.getReference("/terms/$index") ?: return

        dbReference.removeValue().addOnSuccessListener { state ->
            run {
                Log.i("MyApp", state.toString())
            }
        }
            .addOnFailureListener { error ->
                run {
                    Log.i("MyApp", error.message ?: "")
                }
            }
    }

    fun initializeDatabaseWithTerms() {
        val terms = listOf(
           Pair("Cache", "Pronounced cash, a special high-speed storage mechanism. It can be either a reserved section of main memory or an independent high-speed storage device. Two types of caching are commonly used in personal computers: memory caching and disk caching.")
        )

        for ((name, description) in terms) {
            setTerm(name, description)
        }
    }


}