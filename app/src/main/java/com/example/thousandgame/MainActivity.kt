package com.example.thousandgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var amountOfPlayers: Int = 0
    private lateinit var btn_start: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start = findViewById(R.id.btn_start)
        btn_start.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_start ->{
                val intent = Intent(this, GameActivity::class.java)
                val playerNames: ArrayList<String> = ArrayList()

                val tv_name1 = findViewById<TextView>(R.id.et_name1)
                val tv_name2 = findViewById<TextView>(R.id.et_name2)
                val tv_name3 = findViewById<TextView>(R.id.et_name3)
                val tv_name4 = findViewById<TextView>(R.id.et_name4)
                val tv_name5 = findViewById<TextView>(R.id.et_name5)

                if(tv_name1.text.isNotEmpty()){
                    amountOfPlayers++
                    playerNames.add(tv_name1.text.toString())
                }

                if(tv_name2.text.isNotEmpty()){
                    amountOfPlayers++
                    playerNames.add(tv_name2.text.toString())
                }

                if(tv_name3.text.isNotEmpty()){
                    amountOfPlayers++
                    playerNames.add(tv_name3.text.toString())
                }

                if(tv_name4.text.isNotEmpty()){
                    amountOfPlayers++
                    playerNames.add(tv_name4.text.toString())
                }

                if(tv_name5.text.isNotEmpty()){
                    amountOfPlayers++
                    playerNames.add(tv_name5.text.toString())
                }

                intent.putExtra("PLAYERS", amountOfPlayers)
                intent.putExtra("PLAYER_NAMES", playerNames)

                if(amountOfPlayers > 1){
                    startActivity(intent)
                    finish()
                }else{
                    //todo: add a toast saying that there is not enough players
                }

            }
        }
    }
}