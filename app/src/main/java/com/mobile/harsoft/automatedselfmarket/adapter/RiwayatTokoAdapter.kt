package com.mobile.harsoft.automatedselfmarket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.harsoft.automatedselfmarket.R
import com.mobile.harsoft.automatedselfmarket.model.RiwayatToko
import com.mobile.harsoft.automatedselfmarket.model.Toko

class RiwayatTokoAdapter(
    private val context: Context,
    private val riwayatToko: List<RiwayatToko?>,
    private val toko: List<Toko?>
) : RecyclerView.Adapter<RiwayatTokoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_riwayat_toko, parent, false
        )
    )

    override fun getItemCount(): Int = riwayatToko.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(riwayatToko[position]!!, toko)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        private val tvNamaToko = itemView.findViewById<TextView>(R.id.tvNamaTokoRiwayat)
        private val tvAlamatToko = itemView.findViewById<TextView>(R.id.tvAlamatTokoRiwayat)

        fun bindItem(riwayatToko: RiwayatToko, toko: List<Toko?>) {
            tvTanggal.text = riwayatToko.created_at

            for (data in toko) {
                if (riwayatToko.id_toko!! == data!!.id_toko) {
                    tvNamaToko.text = data.nama_toko
                    tvAlamatToko.text = data.alamat
                    break
                }
            }
        }
    }

}