package com.example.uasdpm2011500042

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class adapter_kampus (
    private val getContext: Context,
    private val customListItem: ArrayList<campus>
): ArrayAdapter<campus>(getContext, 0, customListItem) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listLayout = convertView
        val holder: ViewHolder
        if (listLayout == null) {
            val infLateList = (getContext as Activity).layoutInflater
            listLayout = infLateList.inflate(R.layout.layout_item, parent, false)
            holder = ViewHolder()
            with(holder) {
                tvNmDosen = listLayout.findViewById(R.id.tvNmDosen)
                tvNIDN = listLayout.findViewById(R.id.tvNIDN)
                tvProdi = listLayout.findViewById(R.id.tvProdi)
                btnEdit = listLayout.findViewById(R.id.btnEdit)
                btnHapus = listLayout.findViewById(R.id.btnHapus)
            }
            listLayout.tag = holder
        }else
            holder = listLayout.tag as ViewHolder
        val listItem = customListItem[position]
        holder.tvNmDosen!!.setText(listItem.nmDosen)
        holder.tvNIDN!!.setText(listItem.NIDN)
        holder.tvProdi!!.setText(listItem.programstudi)

        holder.btnEdit!!.setOnClickListener{
            val i = Intent(context, etntri_data_dosen::class.java)
            i.putExtra("NIDN", listItem.NIDN)
            i.putExtra("nama", listItem.nmDosen)
            i.putExtra("jabatan", listItem.jabatan)
            i.putExtra("golpat", listItem.Golpat)
            i.putExtra("pendidikan", listItem.pendidikan)
            i.putExtra("keahlian", listItem.Bidang)
            i.putExtra("Prodi", listItem.programstudi)
            context.startActivity(i)
        }
        holder.btnHapus!!.setOnClickListener {
            val db = DbHelper(context)
            val alb = AlertDialog.Builder(context)
            val nidn = holder.tvNIDN!!.text
            val nama = holder.tvNmDosen!!.text
            val prodi = holder.tvProdi!!.text

            with(alb){
                setTitle("Konfirmasi Penghapusan")
                setCancelable(false)
                setMessage("""
                        Apakah Anda yakin akan menghapus ini?
                                
                                $nama[$nidn - $prodi]
                                """.trimIndent())
                setPositiveButton("Ya"){ _, _ ->
                    if(db.hapus("$nidn"))
                        Toast.makeText(
                            context,
                            "Data mata kuliah berhasil dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                    else
                        Toast.makeText(
                            context,
                            "Data mata kuliah gagal dihapus",
                            Toast.LENGTH_SHORT
                        ).show()
                }
                setNegativeButton("Tidak", null)
                create().show()
            }
        }

        return listLayout!!
    }

    class ViewHolder {
        internal var
                tvNmDosen: TextView? = null
        internal var
                tvNIDN: TextView? = null
        internal var
                tvProdi: TextView? = null
        internal var
                btnEdit: ImageButton? = null
        internal var
                btnHapus: ImageButton? = null

    }
}