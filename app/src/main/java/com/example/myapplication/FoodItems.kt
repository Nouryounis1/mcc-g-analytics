package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class FoodItems : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    private lateinit var db: FirebaseFirestore
    var startTime: Long = 0
    var endTime: Long = 0

    lateinit var kebebBtn:Button
    lateinit var burgerBtn:Button
    lateinit var pizzaBtn:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_items)
        kebebBtn  = findViewById(R.id.kebebBtn)
        burgerBtn = findViewById(R.id.burgerBtn)
        pizzaBtn = findViewById(R.id.pizzaBtn)
        db = FirebaseFirestore.getInstance()

        kebebBtn.setOnClickListener {
            xEvent("DSA12SS3","kebeb Button")
            setContentView(R.layout.kebabitem)


        }


        burgerBtn.setOnClickListener {
            xEvent("dadv3f","burger Button")
            setContentView(R.layout.burgeritem)


        }


        pizzaBtn.setOnClickListener {

            xEvent("xeg3g2","pizza Button")
            setContentView(R.layout.pizzaitem)


        }


        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        screenTracking("FoodItems")

    }

    private fun screenTracking(screenName: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "FoodItems")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }





    private fun xEvent(id: String, name: String) {
        val bundle = Bundle()
        bundle.putString("id", id)
        bundle.putString("name", name)
        firebaseAnalytics.logEvent("xEvent", bundle)

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

        addDataToFirestore("X1F2" , time , "FoodItems" )


        super.onDestroy()
    }





}