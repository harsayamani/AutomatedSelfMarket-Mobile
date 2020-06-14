package com.mobile.harsoft.automatedselfmarket.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobile.harsoft.automatedselfmarket.R
import com.mobile.harsoft.automatedselfmarket.model.Keranjang


class KeranjangAdapter(
    private val context: Context,
    private val keranjang: List<Keranjang?>,
    private val listener: (Keranjang) -> Unit,
    private val listener2: (Keranjang) -> Unit,
    private val listener3: (Keranjang) -> Unit
) : RecyclerView.Adapter<KeranjangAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_keranjang, parent, false
            )
        )

    override fun getItemCount(): Int = keranjang.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(keranjang[position]!!, listener, listener2, listener3)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNamaProduk = itemView.findViewById<TextView>(R.id.tvNamaProduk)
        private val tvKategoriProduk = itemView.findViewById<TextView>(R.id.tvKategori)
        private val tvHarga = itemView.findViewById<TextView>(R.id.tvHarga)
        private val tvKuantitas = itemView.findViewById<TextView>(R.id.tvKuantitas)
        private val btnPlus = itemView.findViewById<TextView>(R.id.btnPlus)
        private val btnMinus = itemView.findViewById<TextView>(R.id.btnMinus)
        private val ivHapusKeranjang = itemView.findViewById<ImageView>(R.id.ivHapusKeranjang)

        @SuppressLint("SetTextI18n")
        fun bindItem(
            keranjang: Keranjang,
            listener: (Keranjang) -> Unit,
            listener2: (Keranjang) -> Unit,
            listener3: (Keranjang) -> Unit
        ) {
            tvNamaProduk.text = keranjang.nama_produk
            tvKategoriProduk.text = keranjang.kategori_produk
            tvHarga.text = "Rp." + keranjang.harga_update.toString()
            tvKuantitas.text = keranjang.kuantitas.toString()

            if (keranjang.kuantitas != 1) {
                btnMinus.setOnClickListener {
                    listener(keranjang)
                }
            } else if (keranjang.kuantitas == 1) {
                btnMinus.visibility = View.GONE
            }

            btnPlus.setOnClickListener {
                listener2(keranjang)
            }

            ivHapusKeranjang.setOnClickListener {
                listener3(keranjang)
            }
        }
    }
}