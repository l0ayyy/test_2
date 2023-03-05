package com.example.myapplication

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.myapplication.databinding.ActivityMain2Binding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity2 : AppCompatActivity() {

    val db = Firebase.firestore
    lateinit var binding: ActivityMain2Binding
    lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        listView = binding.listView

        db.collection("contacts")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val contactsList = mutableListOf<Map<String, Any>>()

                for (document in querySnapshot) {
                    contactsList.add(document.data)
                }

                val adapter = object : ArrayAdapter<Map<String, Any>>(this, android.R.layout.simple_list_item_1, contactsList) {
                    override fun getView(position: Int, convertView: android.view.View?, parent: android.view.ViewGroup): android.view.View {
                        val contactData = getItem(position)

                        val listItem = convertView ?: layoutInflater.inflate(R.layout.info_layout, parent, false)

                        listItem.findViewById<TextView>(R.id.textname).text = contactData?.get("name").toString()
                        listItem.findViewById<TextView>(R.id.textnumber).text = contactData?.get("number").toString()
                        listItem.findViewById<TextView>(R.id.textaddress).text = contactData?.get("address").toString()

                        return listItem
                    }
                }

                listView.adapter = adapter
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error getting documents", e)
            }



    }

    }
