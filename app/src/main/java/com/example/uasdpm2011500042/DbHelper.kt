package com.example.uasdpm2011500042

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper (context: Context): SQLiteOpenHelper(context, "kampus", null, 1){
    var NIDN = ""
    var nmDosen = ""
    var jabatan = ""
    var Golpat =""
    var pendidikan =""
    var Bidang = ""
    var programstudi = ""

    private val tabel = "lecturer"
    private var sql = ""

    override fun onCreate(db: SQLiteDatabase?) {
        sql = """create table $tabel(
            NIDN char(10) primary key,
            nm_dosen varchar(50) not null,
            jabatan varchar (15) not null,
            golonganpangkat varchar (30) not null,
            pendidikan char (2) not null,
            Bidang varchar (30) not null,
            Prodi varchar(50) not null
            )
        """.trimIndent()
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        sql = "drop table if exists $tabel"
        db?.execSQL(sql)
    }

    fun simpan(): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv) {
            put("NIDN", NIDN)
            put("nm_dosen", nmDosen)
            put("jabatan", jabatan)
            put("golonganpangkat", Golpat)
            put("pendidikan", pendidikan)
            put("keahlian", Bidang)
            put("programstudi", programstudi)
        }
        val cmd = db.insert(tabel, null, cv)
        db.close()
        return cmd != -1L
    }
    fun ubah(kode:String): Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        with(cv) {
            put("nm_dosen", nmDosen)
            put("jabatan", jabatan)
            put("golonganpangkat", Golpat)
            put("pendidikan", pendidikan)
            put("keahlian", Bidang)
            put("programstudi", programstudi)
        }
        val cmd = db.update(tabel, cv, "NIDN = ?", arrayOf(kode))
        db.close()
        return cmd != -1
    }
    fun hapus(kode:String): Boolean {
        val db = writableDatabase
        val cmd = db.delete(tabel, "NIDN = ?", arrayOf(kode))
        return cmd != -1
    }

    fun tampil(): Cursor {
        val db = writableDatabase
        val reader = db.rawQuery("select * from $tabel", null)
        return reader
    }
}