package com.example.uasdpm2011500042

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    private lateinit var adpKampus: adapter_kampus
    private lateinit var DtKampus: ArrayList<campus>
    private lateinit var lvDosen: ListView
    private lateinit var linTidakAda: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnTambah = findViewById<Button>(R.id.btnTambah)
        lvDosen = findViewById(R.id.lvDosen)
        linTidakAda = findViewById(R.id.linTidakAda)

        DtKampus = ArrayList()
        adpKampus = adapter_kampus(this@MainActivity, DtKampus)

        lvDosen.adapter =adpKampus

        refresh()

        btnTambah.setOnClickListener {
            val i = Intent(this@MainActivity, etntri_data_dosen::class.java)
            startActivity(i)
        }
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus) refresh()
    }

    private fun refresh(){
        val db = DbHelper(this@MainActivity)
        val data = db.tampil()
        repeat(DtKampus.size) { DtKampus.removeFirst()}
        if(data.count > 0 ){
            while(data.moveToNext()){
                val Dosen = campus(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
                adpKampus.add(Dosen)
                adpKampus.notifyDataSetChanged()
            }
            lvDosen.visibility = View.VISIBLE
            linTidakAda.visibility  = View.GONE
        } else {
            lvDosen.visibility = View.GONE
            linTidakAda.visibility = View.VISIBLE
        }
    }
}