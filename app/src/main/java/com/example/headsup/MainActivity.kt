package com.example.headsup

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Surface
import android.widget.*
import androidx.core.view.isVisible
import com.example.headsup.database.Celebrity
import com.example.headsup.database.DatabaseHandler
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private lateinit var dbHandler: DatabaseHandler

    private lateinit var llTop: LinearLayout
    private lateinit var llMain: LinearLayout
    private lateinit var llCelebrity: LinearLayout

    private lateinit var tvTime: TextView

    private lateinit var tvName: TextView
    private lateinit var tvTaboo1: TextView
    private lateinit var tvTaboo2: TextView
    private lateinit var tvTaboo3: TextView

    private lateinit var tvMain: TextView
    private lateinit var btStart: Button
    private lateinit var btData: Button

    private var gameActive = false
    private lateinit var celebrities: ArrayList<Celebrity>

    private var celeb = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = DatabaseHandler(this)

        llTop = findViewById(R.id.llTop)
        llMain = findViewById(R.id.llMain)
        llCelebrity = findViewById(R.id.llCelebrity)

        tvTime = findViewById(R.id.tvTime)

        tvName = findViewById(R.id.tvName)
        tvTaboo1 = findViewById(R.id.tvTaboo1)
        tvTaboo2 = findViewById(R.id.tvTaboo2)
        tvTaboo3 = findViewById(R.id.tvTaboo3)

        tvMain = findViewById(R.id.tvMain)
        btStart = findViewById(R.id.btStart)
        btStart.setOnClickListener { getCelebrities() }

        btData = findViewById(R.id.btData)
        btData.setOnClickListener {
            val intent = Intent(this, Data::class.java)
            startActivity(intent)
        }

        celebrities = arrayListOf()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val rotation = windowManager.defaultDisplay.rotation
        if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180){
            if(gameActive){
                celeb++
                newCelebrity(celeb)
                updateStatus(false)
            }else{
                updateStatus(false)
            }
        }else{
            if(gameActive){
                updateStatus(true)
            }else{
                updateStatus(false)
            }
        }
    }

    private fun newTimer(){
        if(!gameActive){
            gameActive = true
            tvMain.text = "Please Rotate Device"
            btStart.isVisible = false
            btData.isVisible = false
            val rotation = windowManager.defaultDisplay.rotation
            if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180){
                updateStatus(false)
            }else{
                updateStatus(true)
            }

            object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tvTime.text = "Time: ${millisUntilFinished / 1000}"
                }

                override fun onFinish() {
                    gameActive = false
                    tvTime.text = "Time: --"
                    tvMain.text = "Heads Up!"
                    btStart.isVisible = true
                    btData.isVisible = true
                    updateStatus(false)
                }
            }.start()
        }
    }

    private fun newCelebrity(id: Int){
        if(id < celebrities.size){
            tvName.text = celebrities[id].name
            tvTaboo1.text = celebrities[id].taboo1
            tvTaboo2.text = celebrities[id].taboo2
            tvTaboo3.text = celebrities[id].taboo3
        }
    }

    fun getCelebrities(){
        celebrities = dbHandler.getCelebrities()
        celebrities.shuffle()
        newCelebrity(0)
        newTimer()
    }

    private fun updateStatus(showCelebrity: Boolean){
        if(showCelebrity){
            llCelebrity.isVisible = true
            llMain.isVisible = false
        }else{
            llCelebrity.isVisible = false
            llMain.isVisible = true
        }
    }
}