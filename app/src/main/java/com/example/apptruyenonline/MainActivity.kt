package com.example.apptruyenonline

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptruyenonline.databinding.ActivityMainBinding

import org.jsoup.Jsoup

class MainActivity : AppCompatActivity(), MainAdapter.IAdapter, AdapterDrawer.IDrawer {
    private lateinit var binding: ActivityMainBinding
    private var arr = mutableListOf<DataAdapter>()
    private var arrdrawer = mutableListOf<DataDrawer>()
    private var pages = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val adter = MainAdapter(this)
        val adter2 = AdapterDrawer(this)
        actionBar()
        autorenew()
        loadAsyn("https://truyencotich.vn/truyen-co-tich/co-tich-viet-nam")
        asyntaskdd("https://truyencotich.vn/truyen-co-tich/co-tich-viet-nam")
        binding.rc.layoutManager = GridLayoutManager(this, 2)
        binding.rc.adapter = adter
        binding.rc2.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rc2.adapter = adter2

    }

    private fun autorenew() {
        binding.imgbtn.setOnClickListener {
            pages++
            loadAsyn("https://truyencotich.vn/truyen-co-tich/co-tich-viet-nam/page/$pages")
        }
    }

    override fun getCount() = arr.size

    override fun getPositionData(position: Int): DataAdapter = arr[position]

    override fun getOnclick(position: Int) {
        asynHtml(arr[position].linkHtml, position)
    }


    private fun loadAsyn(link: String) {
        @SuppressLint("StaticFieldLeak")
        binding.rl.visibility = View.VISIBLE
        val asyn = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<String, Void, MutableList<DataAdapter>>() {
            override fun doInBackground(vararg params: String): MutableList<DataAdapter> {
                return loadData(params[0])

            }

            override fun onPostExecute(result: MutableList<DataAdapter>) {
                super.onPostExecute(result)
                binding.rl.visibility = View.GONE
                if (result.size != null) {
                    arr.addAll(result!!)
                    binding.rc.adapter!!.notifyDataSetChanged()
                    binding.rc.smoothScrollToPosition(arr.size - result.size)
                }
            }
        }
        asyn.execute(link)
    }

    private fun loadData(link: String): MutableList<DataAdapter> {

        val element = Jsoup.connect(link).get()
        var arr2 = mutableListOf<DataAdapter>()
        if (element != null) {
            val elt = element.select("div.clear")
            val el = elt.get(4)
            for (elt1 in el.select("article.entry-grid")) {
                val eltImg = elt1.selectFirst("img").attr("src")
                val eltName = elt1.selectFirst("h2.entry-title")
                    .text()
                val eltcUrl = elt1.selectFirst("h2.entry-title")
                    .selectFirst("a").attr("href").toString()
                val eltContent = elt1.selectFirst("p.post-excerpt").text()
                arr2.add(DataAdapter(eltImg, eltName, eltContent, eltcUrl))
            }

        }
        return arr2
    }

    private fun actionBar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener {
            binding.drawer.openDrawer(GravityCompat.START)

        }
    }

    private fun asyntaskdd(link: String) {
        @SuppressLint("StaticFieldLeak")
        val asyn = object : AsyncTask<String, Void, MutableList<DataDrawer>>() {
            override fun doInBackground(vararg params: String): MutableList<DataDrawer> {
                return loadDataTiltle(params[0])
            }

            override fun onPostExecute(result: MutableList<DataDrawer>) {
                super.onPostExecute(result)
                arrdrawer.addAll(result)
                binding.rc2.adapter!!.notifyDataSetChanged()
                binding.rc2.smoothScrollToPosition(arrdrawer.size - result.size)
            }
        }
        asyn.execute(link)
    }

    private fun loadDataTiltle(link: String): MutableList<DataDrawer> {
        val eltdrawer = Jsoup.connect(link).get()
        val arrtitle = mutableListOf<DataDrawer>()
        if (eltdrawer != null) {
            val eltTitle = eltdrawer.select("div.wrap")
            val eltsub = eltTitle.get(2)
            for (elt in eltsub.select("li")) {
                val eltcontent = elt.selectFirst("li.menu-item").text()
                val url = elt.selectFirst("li.menu-item")
                    .selectFirst("a").attr("href").toString()
                arrtitle.add(DataDrawer(eltcontent, url))
            }
        }
        return arrtitle
    }

    override fun getSize(): Int = arrdrawer.size

    override fun getData(position: Int): DataDrawer = arrdrawer[position]

    override fun getOnClick(position: Int) {
        initdiffrentData(position)
        binding.drawer.closeDrawer(GravityCompat.START)
    }

    private fun initdiffrentData(position: Int) {
        arr.clear()
        loadAsyn(arrdrawer[position].urll)
        val adter = MainAdapter(this)
        binding.rc.layoutManager = GridLayoutManager(this, 2)
        binding.rc.adapter = adter
        binding.rc.smoothScrollToPosition(position)
        pages = 1
        binding.imgbtn.setOnClickListener {
            pages++
            loadAsyn(arrdrawer[position].urll + "/page/$pages")
        }
        Toast.makeText(this, arrdrawer[position].title, Toast.LENGTH_SHORT).show()
    }

    private fun getContent(link: String): String {

        val elthtml = Jsoup.connect(link).get()
        var str = ""

        if (elthtml != null) {
            val eltt = elthtml.select("div.site-content")
            for (eltget in eltt.select("article")) {
                str = eltget.select("p" + "\n").text()
            }
        }
        return str
    }

    private fun asynHtml(link: String, position: Int) {

        val asyn = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<String, Void, String>() {

            override fun doInBackground(vararg params: String): String {

                return getContent(params[0])

            }

            override fun onPostExecute(result: String) {
                //super.onPostExecute(result)
                val intt = Intent(this@MainActivity, ActivityContent::class.java)
                intt.putExtra("content1", result)
                intt.putExtra("title1", arr[position].name)
                intt.putExtra("imgg1", arr[position].img)
                startActivity(intt)
                overridePendingTransition(R.anim.come, R.anim.come_to_back)
            }

        }

        asyn.execute(link)

    }


}



