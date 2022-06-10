package com.aircraftwar.android

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aircraftwar.android.databinding.ScoreItemLayoutBinding
import com.aircraftwar.android.application.datahandle.Score
import java.util.*

class ScoreListAdapter(val context : Context, private val scores : List<Score>) : RecyclerView.Adapter<ScoreListAdapter.ViewHolder>() {
    inner class ViewHolder(
        private var binding: ScoreItemLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(score: Score) {
            binding.scoreTextView1.text = score.username
            binding.scoreTextView2.text = score.userscore.toString()
            binding.scoreTextView3.text = Date(score.uid).toString()
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Score>() {
            override fun areItemsTheSame(oldItem: Score, newItem: Score): Boolean {
                return oldItem.uid == newItem.uid
            }

            override fun areContentsTheSame(oldItem: Score, newItem: Score): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(
            ScoreItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnLongClickListener(object : View.OnLongClickListener{
            override fun onLongClick(p0: View?): Boolean {
                val position = viewHolder.adapterPosition
                Toast.makeText(context,"Click! ${position}",Toast.LENGTH_SHORT).show()
                return true
            }
        })
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(scores[position])
    }

    override fun getItemCount(): Int {
        return  scores.size
    }
}