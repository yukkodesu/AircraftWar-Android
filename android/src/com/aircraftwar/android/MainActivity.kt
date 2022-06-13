package com.aircraftwar.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aircraftwar.android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.scoreButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val intent = Intent(v.context,RankListActivity::class.java)
                startActivity(intent)
            }
        })

        binding.simpleButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val intent = Intent(v.context,AndroidLauncher::class.java)
                intent.putExtra("Difficulty",1)
                intent.putExtra("isAudioOn",binding.audioEnableCheck.isChecked)
                startActivity(intent)
            }
        })
        binding.OnlineButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val intent = Intent(v.context,AndroidLauncher::class.java)
                intent.putExtra("Difficulty",1)
                intent.putExtra("isAudioOn",binding.audioEnableCheck.isChecked)
                intent.putExtra("isOnline",true)
                startActivity(intent)
            }
        })

        binding.middleButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val intent = Intent(v.context,AndroidLauncher::class.java)
                intent.putExtra("Difficulty",2)
                intent.putExtra("isAudioOn",binding.audioEnableCheck.isChecked)
                startActivity(intent)
            }
        })

        binding.hardButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val intent = Intent(v.context,AndroidLauncher::class.java)
                intent.putExtra("Difficulty",3)
                intent.putExtra("isAudioOn",binding.audioEnableCheck.isChecked)
                startActivity(intent)
            }
        })
    }
}