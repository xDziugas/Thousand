package com.example.thousandgame

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Button
import com.google.android.material.color.utilities.Score
import java.util.*
import kotlin.collections.getOrDefault


class Dice() {

    //todo: controls all dices and their images
    //todo: draws X RANDOM images/animations, pagal juos score ims, pradzioj galima tsg displaying random reiksmes kad veikia
    //todo: displays points: X
    //todo: changes btn_roll text to "roll X dices"

    var dicesToRoll: Int = 5
    var points: Int = 0
    var timesRolled: Int = 0
    var burningTurns: Int = 0
    lateinit var gameActivity: GameActivity
    lateinit var btnEndTurn: Button
    lateinit var btnRollDice: Button

    constructor(gameActivity: GameActivity) : this() {
        this.gameActivity = gameActivity
        this.btnEndTurn = gameActivity.findViewById(R.id.btn_end_turn)
        this.btnRollDice = gameActivity.findViewById(R.id.btn_roll_dices)
    }

    fun roll(){

        timesRolled++
        val diceRolls = mutableMapOf<Int, Int>()

        val random = Random()
        for(i in 0 until dicesToRoll){
            val result = random.nextInt(6) + 1
            val count = diceRolls.getOrDefault(result, 0)
            diceRolls[result] = count + 1
        }

         calculateScore(diceRolls)

        if(dicesToRoll == 0)
            dicesToRoll = 5
    }

    private fun calculateScore(diceRolls: MutableMap<Int, Int>){
        var score: Int = 0

        timesRolled++

        Log.d(TAG, "Scores:  " + diceRolls.getOrDefault(1, 0) +
                diceRolls.getOrDefault(2, 0) +
                diceRolls.getOrDefault(3, 0) +
                diceRolls.getOrDefault(4, 0) +
                diceRolls.getOrDefault(5, 0) +
                diceRolls.getOrDefault(6, 0))

        for(dots in 1..6){
            if(diceRolls.getOrDefault(dots, 0) == 5){
                if(dots == 1)
                    score += dots * 100 * 10
                else
                    score += dots * 1000

                dicesToRoll -= 5
                break
            }

            if(diceRolls.getOrDefault(dots, 0) == 4){
                if(dots == 1)
                    score += dots * 100 * 10 / 2
                else
                    score += dots * 100 / 2

                dicesToRoll -= 4
                continue
            }

            if(diceRolls.getOrDefault(dots, 0) == 3){
                if(dots == 1)
                    score += dots * 100
                else
                    score += dots * 10

                dicesToRoll -= 3
                continue
            }

            if(diceRolls.getOrDefault(dots, 0) == 2){
                if(dots == 1){
                    score += 20
                    dicesToRoll -= 2
                }
                if(dots == 5){
                    score += 10
                    dicesToRoll -= 2
                }
                continue
            }

            if(diceRolls.getOrDefault(dots, 0) == 1){
                if(dots == 1){
                    score += 10
                    dicesToRoll--
                }
                if(dots == 5){
                    score += 5
                    dicesToRoll--
                }
                continue
            }
        }

        Log.d(TAG, "Total score: $score")
        Log.d(TAG, "Total points: $points")

        if(score == 0){
            points = 0
            dicesToRoll = 5
            //btnRollDice.isClickable = false
        }else{
            points += score
        }

        if(dicesToRoll == 0){
            dicesToRoll = 5
        }

        if(score + points > 1000){
            burningTurns++
            //btnEndTurn.isClickable = false

            if(burningTurns >= 3){
                points = 0
                burningTurns = 0 //nebutina
            }
        }else{
            //btnEndTurn.isClickable = true
        }

    }
}