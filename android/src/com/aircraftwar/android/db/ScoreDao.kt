package com.aircraftwar.android.db

import com.aircraftwar.android.application.datahandle.Score

interface ScoreDao {
    fun findById(uid: Long): ArrayList<Score>
    fun printAllScores(): ArrayList<Score>
    fun add(score: Score)
    fun del(uid: Long)
    fun readFile()
    fun writeFile()
}