package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class LaptopItems : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    private lateinit var db: FirebaseFirestore
    var startTime: Long = 0
    var endTime: Long = 0

    lateinit var pavBtn:Button
    lateinit var proBtn:Button
    lateinit var hpsBtn:Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laptop_items)

        pavBtn = findViewById(R.id.pavBtn)
        proBtn = findViewById(R.id.proBtn)
        hpsBtn = findViewById(R.id.hpsBtn)
        db = FirebaseFirestore.getInstance()

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        screenTracking("LaptopItems")


        pavBtn.setOnClickListener {

            xEvent("C1G2","Pavallion Button")
            setContentView(R.layout.pavilionitem)


        }

        proBtn.setOnClickListener {
            xEvent("S21G2","ProBook Button")
            setContentView(R.layout.probookitem)



        }

        hpsBtn.setOnClickListener {

            xEvent("H21Q2","HP S Button")
            setContentView(R.layout.hpsitem)



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
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "LaptopItems")
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

        addDataToFirestore("A1B2" , time , "LaptopItems" )


        super.onDestroy()
    }




}