package com.mobile.harsoft.automatedselfmarket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.harsoft.automatedselfmarket.R
import com.mobile.harsoft.automatedselfmarket.model.DetailTransaksi
import com.mobile.harsoft.automatedselfmarket.model.Keranjang
import com.mobile.harsoft.automatedselfmarket.model.Produk

class DetailTransaksiAdapter(
    private val context: Context,
    private val keranjang: List<Keranjang?>,
    private val detail: List<DetailTransaksi?>,
    private val produk: List<Produk?>
) : RecyclerView.Adapter<DetailTransaksiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        LayoutInflater.from(context).inflate(
            R.layout.item_detail_transaksi, parent, false
        )
    )

    override fun getItemCount(): Int = detail.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(detail[position]!!, keranjang, produk)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNo = itemView.findViewById<TextView>(R.id.tvNo)
        private val tvProduk = itemView.findViewById<TextView>(R.id.tvProduk)
        private val tvKuantitas = itemView.findViewById<TextView>(R.id.tvKuantitas)
        private val tvHarga = itemView.findViewById<TextView>(R.id.tvHarga)
        private val tvTotal = itemView.findViewById<TextView>(R.id.tvTotal)

        fun bindItem(detail: DetailTransaksi, keranjang: List<Keranjang?>, produk: List<Produk?>) {
            tvNo.text = (adapterPosition + 1).toString()
            for (data in keranjang) {
                if (detail.id_keranjang == data?.id_keranjang) {
                    tvKuantitas.text = data?.kuantitas.toString()
                    tvHarga.text = (data?.harga_update!! / data.kuantitas!!).toString()
                    tvTotal.text = data.harga_update.toString()
                    for (prod in produk) {
                        if (prod?.id_produk == data.id_produk) {
                            tvProduk.text = prod?.nama_produk.toString()
                            break
                        }
                    }
                    break
                }
            }
        }
    }
}