package com.teslasoft.hackerapp.data

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class KeyKeeper {
    companion object {
        private var firebaseDatabase: FirebaseDatabase? = null
        private var newsKey = ""

        init{
            initialize()
        }

        private fun initialize() {
            firebaseDatabase = FirebaseDatabase.getInstance()
            val dbReference: DatabaseReference = firebaseDatabase?.getReference("/secrets/newsKey") ?: return

            dbReference.get()
                .addOnSuccessListener { data ->
                    newsKey = data.getValue(String::class.java) ?: ""
                    Log.i("KeyKeeper", "API Key retrieved successfully")
                }
                .addOnFailureListener { error ->
                    Log.e("KeyKeeper", "Error fetching API Key: ${error.message}")
                }
        }

//        fun getNewsKey(): String {
//            return newsKey
//        }

        fun getNewsKey(callback: (String) -> Unit) {
            if (newsKey.isNotEmpty()) {
                callback(newsKey)
            } else {
                // Wait for the key to be fetched
                val dbReference: DatabaseReference = firebaseDatabase?.getReference("/secrets/newsKey") ?: return
                dbReference.get()
                    .addOnSuccessListener { data ->
                        newsKey = data.getValue(String::class.java) ?: ""
                        callback(newsKey)
                    }
                    .addOnFailureListener { error ->
                        Log.e("KeyKeeper", "Error fetching API Key: ${error.message}")
                    }
            }
        }
    }
}