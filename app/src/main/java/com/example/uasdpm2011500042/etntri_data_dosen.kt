package com.example.uasdpm2011500042

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class etntri_data_dosen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etntri_data_dosen)

        val modeEdit = intent.hasExtra("NIDN") && intent.hasExtra("nama") &&
                intent.hasExtra("jabatan") && intent.hasExtra("golonganpangkat") &&
                intent.hasExtra("pendidikan") && intent.hasExtra("keahlian") &&
                intent.hasExtra("Prodi")
        title = if(modeEdit) "Edit Data Dosen" else "Entri Data Dosen"

        val etKdNIDN = findViewById<EditText>(R.id.etKdNIDN)
        val etNmDosen = findViewById<EditText>(R.id.etNmDosen)
        val spnJabatan = findViewById<Spinner>(R.id.spnJabatan)
        val spnGolpat = findViewById<Spinner>(R.id.spnGolpat)
        val rdS2 = findViewById<RadioButton>(R.id.rdS2)
        val rdS3 = findViewById<RadioButton>(R.id.rdS3)
        val etBidang = findViewById<EditText>(R.id.etBidang)
        val etProdi = findViewById<EditText>(R.id.etProdi)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val jabatan = arrayOf("Tenaga Pengajar","Asisten Ahli","Lektor","Lektor Kepala","Guru Besar")
        val Golpat = arrayOf("III/a - Penata Muda","III/b - Penata Muda Tingkat I","III/c - Penata","III/d - Penata Tingkat I",
            "IV/a - Pembina","IV/b - Pembina Tingkat I","IV/c - Pembina Utama Muda","IV/d - Pembina Utama Madya",
            "IV/e - Pembina Utama")
        val adpGolpat = ArrayAdapter(
            this@etntri_data_dosen,
            android.R.layout.simple_spinner_dropdown_item,
            Golpat
        )
        spnGolpat.adapter = adpGolpat

        val adpJabatan = ArrayAdapter(
            this@etntri_data_dosen,
            android.R.layout.simple_spinner_dropdown_item,
            jabatan
        )
        spnJabatan.adapter = adpJabatan

        if(modeEdit) {
            val kodeNidn = intent.getStringExtra("NIDN")
            val nama = intent.getStringExtra("nama")
            val nvjabatan = intent.getStringExtra("jabatan")
            val nvgolpat = intent.getStringExtra("golpat")
            val pendidikan= intent.getStringExtra("pendidikan")
            val keahlian = intent.getStringExtra("keahlian")
            val studi = intent.getStringExtra("Prodi")

            etKdNIDN.setText(kodeNidn)
            etNmDosen.setText(nama)
            spnJabatan.setSelection(jabatan.indexOf(nvjabatan))
            spnGolpat.setSelection(Golpat.indexOf(nvgolpat))
            if(pendidikan == "S2") rdS2.isChecked = true else rdS3.isChecked = true
            etBidang.setText(keahlian)
            etProdi.setText(studi)
        }
        etKdNIDN.isEnabled = !modeEdit

        btnSimpan.setOnClickListener {
            if("${etKdNIDN.text}".isNotEmpty() && "${etNmDosen.text}".isNotEmpty()
                && "${etBidang.text}".isNotEmpty() && "${etProdi.text}".isNotEmpty() &&
                (rdS2.isChecked || rdS3.isChecked)) {
                val db = DbHelper(this@etntri_data_dosen)
                db.NIDN = "${etKdNIDN.text}"
                db.nmDosen = "${etNmDosen.text}"
                db.jabatan = spnJabatan.selectedItem as String
                db.Golpat = spnGolpat.selectedItem as String
                db.pendidikan = if(rdS2.isChecked) "S2" else "S3"
                db.Bidang = "${etBidang.text}"
                db.programstudi = "${etProdi.text}"
                if(if(!modeEdit) db.simpan() else db.ubah("${etKdNIDN.text}")) {

                    Toast.makeText(
                        this@etntri_data_dosen,
                        "Data Dosen pengampu berhasil disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }else
                    Toast.makeText(
                        this@etntri_data_dosen,
                        "Data Dosen Pengampu kuliah gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()
            }else
                Toast.makeText(
                    this@etntri_data_dosen,
                    "Data Dosen Pengampu belum lengkap",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }
}