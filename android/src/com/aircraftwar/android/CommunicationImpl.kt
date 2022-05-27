package com.aircraftwar.android

import android.content.Intent
import com.aircraftwar.android.application.CommunicationInterface

class CommunicationImpl(private val mActivity : AndroidLauncher) : CommunicationInterface {

    override fun goRankListActivityAndGetName(score : Int) {
        val intent = Intent(mActivity,RankListActivity::class.java)
        intent.putExtra("score",score)
        mActivity.startActivity(intent)
    }
}