package com.aircraftwar.android

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aircraftwar.android.databinding.ActivityRankListBinding
import com.aircraftwar.android.db.*

class RankListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRankListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val score = intent.getIntExtra("score", -1)
        val scoreDb = ScoreDaoImpl(this)
        val adapter = ScoreListAdapter(this, scoreDb.getScores())
        binding.rvlist.adapter = adapter
        binding.rvlist.layoutManager = LinearLayoutManager(this)
        if (score != -1) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            val v = LayoutInflater.from(this).inflate(R.layout.layout_alert, null)
            builder.setView(v)
            val editText = v.findViewById<EditText>(R.id.alertdialogtext)
            builder.setPositiveButton("确认", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    Log.d("Alert", "Okay")
                    scoreDb.add(Score(System.currentTimeMillis(),editText.text.toString(),score))
                    scoreDb.writeFile()
                    adapter.notifyDataSetChanged()
                }
            })
            builder.setNegativeButton("取消", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    Log.d("Alert", "Cancel")
                }
            })
            builder.show()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}