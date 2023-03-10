package com.example.thousandgame

class Player() {

    private var points: Int = 0
    private var turn: Boolean = false
    var winner: Boolean = false
    private var place: Int = 0
    private var dicesToRoll: Int = 5
    var name: String = "default name"

    //todo: draw(), no need???

    constructor(name: String) : this() {
        this.name = name
    }

    public fun getTurn(): Boolean{
        return this.turn
    }

    public fun setTurn(turn: Boolean){
        this.turn = turn
    }

    fun getPoints(): Int{
        return this.points
    }

    fun setPoints(points: Int){
        this.points = points
    }

    public fun isWinner(): Boolean{
        return this.winner
    }

    public fun getPlace(): Int{
        return this.place
    }

    public fun setPlace(place: Int){
        this.place = place
    }

    public fun getDicesToRoll(): Int{
        return this.dicesToRoll
    }

    private fun setDicesToRoll(dices: Int){
        this.dicesToRoll = dices
    }
}