package com.aircraftwar.android.db

import android.content.Context
import android.util.Log
import com.aircraftwar.android.application.datahandle.Score
import com.aircraftwar.android.application.datahandle.ScoreSyncer
import java.io.*
import java.util.ArrayList

class ScoreDaoImpl(private val context: Context) : ScoreDao {

    private val f = File(this.context.filesDir, "scoreData.ser")
    private var scores = ArrayList<Score>()


    private var fi: FileInputStream? = null
    private var fo: FileOutputStream? = null

    init {
        readFile()
        syncWithServer()
        printAllScores()
    }

    fun syncWithServer(){
        val syncer = ScoreSyncer()
        Thread(Runnable {
            val scoreNew = syncer.syncScore(scores)
            if(scoreNew != null){
                scores = scoreNew as ArrayList<Score>
            }
        })
    }

    override fun findById(uid: Long): ArrayList<Score> {
        val res = arrayListOf<Score>()
        for (item in scores) {
            if (item.uid == uid) {
                res.add(item)
            }
        }
        return res
    }


    fun getScores(): ArrayList<Score> {
        return scores
    }

    override fun printAllScores(): ArrayList<Score> {
        scores.sortByDescending { it.userscore }
        Log.d("SCORE","init")
        var i = 1
        for (item in scores) {
            Log.d("SCORE","第" + i + "名: " + item.username + " " + item.userscore)
            i++
        }
        return scores
    }

    override fun add(score: Score) {
        scores.add(score)
    }

    override fun del(uid: Long) {
        scores.removeIf { item -> item.uid == uid }
    }

    override fun readFile() {
        val res : ArrayList<Score>
        if (f.exists()) {
            fi = FileInputStream(f)
            val inStream = ObjectInputStream(fi)
            res = inStream.readObject() as ArrayList<Score>
            res.forEach{
                scores.add(it)
            }
        }
    }

    override fun writeFile() {
        try {
            if (f.exists()) {
                f.delete()
            }
            fo = FileOutputStream(f)
            val out = ObjectOutputStream(fo)
            out.writeObject(scores)
        } catch (i: IOException) {
            i.printStackTrace()
        }
    }

    fun clear() {
        scores.clear()
    }
}