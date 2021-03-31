package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    var startTime: Long = 0
    var endTime: Long = 0
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    lateinit var foodBtn: Button
    lateinit var hpBtn: Button
    lateinit var phonesBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        screenTracking("category")
        foodBtn = findViewById(R.id.foodbtn)
        hpBtn = findViewById(R.id.hpbtn)
        phonesBtn = findViewById(R.id.phoneBtn)
        db = FirebaseFirestore.getInstance()
        startTime = System.currentTimeMillis()

        foodBtn.setOnClickListener {
            xEvent("food1A2B", "Food Buttom")
            val i = Intent(this, FoodItems::class.java)
            startActivity(i)
        }


        hpBtn.setOnClickListener {

            xEvent("Laptop1A2B", "HP button")
            val i = Intent(this, LaptopItems::class.java)
            startActivity(i)

        }


        phonesBtn.setOnClickListener {
            xEvent("Phones1A2B", "Phones button")
            val i = Intent(this, PhonesItems::class.java)
            startActivity(i)


        }





    }


    private fun xEvent(id: String, name: String) {
        val bundle = Bundle()
        bundle.putString("id", id)
        bundle.putString("name", name)
        firebaseAnalytics.logEvent("xEvent", bundle)

    }


    private fun screenTracking(screenName: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }


    private fun addDataToFirestore(id: String, time: String, screenName: String){
        val dbItems = db.collection("Items")
var screenTracking = screenTracking(id, time, screenName)
        dbItems.add(screenTracking).addOnSuccessListener {
            Toast.makeText(
                    this,
                    "Your screenTracking has been added to Firebase Firestore",
                    Toast.LENGTH_SHORT
            ).show();


        }.addOnFailureListener {

            Toast.makeText(this, "Fail to add  screenTracking ", Toast.LENGTH_SHORT).show();



        }

    }


    override fun onDestroy() {

        endTime = System.currentTimeMillis()

        var timeSpend = endTime - startTime
        val simpleDateFormat = SimpleDateFormat("ss")
        val date = Date(timeSpend)
        val time = simpleDateFormat.format(date) + " Seconds"

        addDataToFirestore("A1B2", time, "MainActivity")


        super.onDestroy()
    }


}