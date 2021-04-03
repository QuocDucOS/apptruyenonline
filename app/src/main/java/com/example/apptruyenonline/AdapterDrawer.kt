package com.example.apptruyenonline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apptruyenonline.databinding.DrawerLayoutBinding

class AdapterDrawer : RecyclerView.Adapter<AdapterDrawer.ViewDrawer> {
    private val inter: IDrawer

    constructor(inter: IDrawer) : super() {
        this.inter = inter
    }

    class ViewDrawer(val binding: DrawerLayoutBinding, inter: IDrawer) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener{
                inter.getOnClick(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewDrawer {
        val binding =
            DrawerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewDrawer(binding, inter)
    }

    override fun getItemCount(): Int {
        return inter.getSize()
    }

    override fun onBindViewHolder(holder: ViewDrawer, position: Int) {
        holder.binding.datadrawer = inter.getData(position)
    }

    interface IDrawer {
        fun getSize(): Int
        fun getData(position: Int): DataDrawer
        fun getOnClick(position: Int)
    }
}