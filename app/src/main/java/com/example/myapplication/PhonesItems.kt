package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class PhonesItems : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    private lateinit var db: FirebaseFirestore
    var startTime: Long = 0
    var endTime: Long = 0
    lateinit var s6Btn:Button
    lateinit var s7Btn:Button
    lateinit var s10Btn:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phones_items)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        screenTracking("PhonesItems")
        s6Btn = findViewById(R.id.s6Btn)
        s7Btn = findViewById(R.id.s7Btn)
        s10Btn = findViewById(R.id.s10Btn)
        db = FirebaseFirestore.getInstance()

        s6Btn.setOnClickListener {


            xEvent("d4w4","s6 Button")

            setContentView(R.layout.s6item)

        }


        s7Btn.setOnClickListener {


            xEvent("w6s6","s7 Button")
            setContentView(R.layout.sevenitem)

        }

        s10Btn.setOnClickListener {


            xEvent("S4W1S1","s10 Button")
            setContentView(R.layout.stenitem)

        }

    }



    private fun screenTracking(screenName: String) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "PhonesItems")
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

        addDataToFirestore("X1F2" , time , "PhonesItems" )


        super.onDestroy()
    }




}