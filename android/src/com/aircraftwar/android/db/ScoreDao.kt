package com.aircraftwar.android.db

interface ScoreDao {
    fun findById(uid: Long): ArrayList<Score>
    fun printAllScores(): ArrayList<Score>
    fun add(score: Score)
    fun del(uid: Long)
    fun readFile()
    fun writeFile()
}