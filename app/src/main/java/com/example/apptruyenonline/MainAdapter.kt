package com.example.apptruyenonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.apptruyenonline.R.layout.activity_main_adapter
import com.example.apptruyenonline.databinding.ActivityMainAdapterBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.HolderView> {
    private val inter: IAdapter

    constructor(inter: IAdapter) : super() {
        this.inter = inter
    }

    class HolderView(val binding: ActivityMainAdapterBinding, inter: IAdapter) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener{
                inter.getOnclick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderView {
        val binding =
            ActivityMainAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HolderView(binding,inter)
    }

    override fun getItemCount(): Int {
        return inter.getCount()
    }

    override fun onBindViewHolder(holder: HolderView, position: Int) {
        holder.binding.data = inter.getPositionData(position)


    }

    interface IAdapter {
        fun getCount(): Int
        fun getPositionData(position: Int): DataAdapter
        fun getOnclick(position: Int)
    }

}