package com.example.thousandgame

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlin.coroutines.CoroutineContext

class GameActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Job()

    var gameOver: Boolean = false
    var turnOver: Boolean = false
    var playerToMove: Int = 0

    private val semaphore = Semaphore(1)

    var players: ArrayList<Player> = ArrayList()
    private var amountOfPlayers: Int = 0

    private lateinit var btnRollDices: Button
    private lateinit var btnEndTurn: Button
    private lateinit var scoresLayout: LinearLayout
    private lateinit var tvPlayerScore: TextView

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        amountOfPlayers = intent.getIntExtra("PLAYERS", 2)

        btnRollDices = findViewById(R.id.btn_roll_dices)
        btnEndTurn = findViewById(R.id.btn_end_turn)
        scoresLayout = findViewById(R.id.ll_score_board)
        tvPlayerScore = findViewById(R.id.tv_player_points)

        createPlayers()
        createScoreBoard() //updates a scoreboard

        launch(Dispatchers.IO){

            while(!gameOver){
                createScoreBoard()
                semaphore.acquire()

                playerToMove = (playerToMove + 1) % amountOfPlayers
                nextTurn(players[playerToMove])

                //update scores on the main thread
                withContext(Dispatchers.Main){
                    val scoreBoard: ArrayList<TextView> = createScoreBoard()
                    scoresLayout.removeAllViews()

                    for(i in 0 until players.size){
                        if(i == playerToMove)
                            scoreBoard[i].setTextColor(R.color.primarydark)
                        scoresLayout.addView(scoreBoard[i])
                    }
                }
                turnOver = false
            }
        }
        coroutineContext.cancel()
    }

    private fun nextTurn(player: Player){
        val dices = Dice()

        btnRollDices.setOnClickListener(onClick(btnRollDices, dices, player))
        btnEndTurn.setOnClickListener(onClick(btnEndTurn, dices, player))

        //winning condition
        if(dices.points == 1000 && dices.burningTurns == 0){
            player.winner = true
            gameOver = true
            return
        }

        if(dices.points == 0 && dices.timesRolled > 0){
            return
        }

        if(turnOver){
            return
        }



    }


    @SuppressLint("SetTextI18n")
    private fun onClick(v: View?, dices: Dice, player: Player): View.OnClickListener {
        return View.OnClickListener {
            when(v?.id){
                R.id.btn_roll_dices ->{
                    dices.roll()

                    tvPlayerScore.text = "Points: " + dices.points
                    btnRollDices.text = "Roll " + dices.dicesToRoll + " dices"
                    if(dices.points == 0){
                        btnRollDices.isClickable = false
                    }
                }

                R.id.btn_end_turn ->{
                    var pointsCur = player.getPoints()
                    pointsCur += dices.points
                    player.setPoints(pointsCur)

                    btnRollDices.isClickable = true
                    btnRollDices.text = "Roll 5 dices"
                    tvPlayerScore.text = "Points: 0"

                    turnOver = true
                    semaphore.release()
                }
            }
        }
    }

    private fun createPlayers() {
        val names = intent.getStringArrayListExtra("PLAYER_NAMES")

        for(i in 0 until amountOfPlayers){
            val player = Player(names?.get(i) ?: "default name")
            players.add(player)  //create a player with a name from intent
        }
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    private fun createScoreBoard(): ArrayList<TextView>{

        val scoreBoard: ArrayList<TextView> = ArrayList()

        for(i in 0 until players.size){
            val tvPlayer = TextView(this)
            tvPlayer.text = "${(i+1)}. ${players[i].name}: ${players[i].getPoints()}"
            tvPlayer.textSize = 20F
            tvPlayer.setTextColor(R.color.black)
            tvPlayer.typeface = Typeface.DEFAULT_BOLD
            scoreBoard.add(tvPlayer)
        }
        return scoreBoard
    }
}