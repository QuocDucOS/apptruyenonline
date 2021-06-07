package com.example.apptruyenonline

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.apptruyenonline.databinding.LayoutContentBinding

class ActivityContent : AppCompatActivity() {
    private lateinit var bindingcontent: LayoutContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingcontent = DataBindingUtil.setContentView(this, R.layout.layout_content)
        val intt = intent
        var s = intt.getStringExtra("content1")

        var title = intt.getStringExtra("title1")
        var imgid = intt.getStringExtra("imgg1")

        Glide.with(bindingcontent.img1).load(imgid).error(R.drawable.khac).into(bindingcontent.img1)
        bindingcontent.txttitle.setText(title)
        bindingcontent.txtcontent.setText(s)
        setSupportActionBar(bindingcontent.backtoolbar)
        bindingcontent.backtoolbar.setNavigationOnClickListener {
           onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.back, R.anim.back_to_come)
    }


}