package com.mobile.harsoft.automatedselfmarket.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.harsoft.automatedselfmarket.R
import com.mobile.harsoft.automatedselfmarket.model.Toko
import com.mobile.harsoft.automatedselfmarket.model.Transaksi
import kotlinx.android.extensions.LayoutContainer

class TransaksiAdapter(
    private val context: Context,
    private val transaksi: List<Transaksi?>,
    private val toko: List<Toko?>,
    private val listener: (Transaksi) -> Unit
) : RecyclerView.Adapter<TransaksiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_transaksi, parent, false
        )
    )

    override fun getItemCount(): Int = transaksi.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(transaksi[position]!!, toko, listener)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        private val tvIdTransaksi = containerView.findViewById<TextView>(R.id.tvIdTransaksi)
        private val tvTanggalTransaksi =
            containerView.findViewById<TextView>(R.id.tvTanggalTransaksi)
        private val tvTagihan = containerView.findViewById<TextView>(R.id.tvTagihan)
        private val tvNamaToko = containerView.findViewById<TextView>(R.id.tvNamaToko)

        @SuppressLint("SetTextI18n")
        fun bindItem(
            transaksi: Transaksi,
            toko: List<Toko?>,
            listener: (Transaksi) -> Unit
        ) {
            tvIdTransaksi.text = "Transaksi#" + transaksi.id_transaksi
            tvTagihan.text = "Rp." + transaksi.total_tagihan.toString()
            tvTanggalTransaksi.text = transaksi.created_at

            for (data in toko) {
                if (data?.id_toko == transaksi.id_toko) {
                    tvNamaToko.text = data?.nama_toko
                    break
                }
            }

            containerView.setOnClickListener {
                listener(transaksi)
            }
        }
    }
}