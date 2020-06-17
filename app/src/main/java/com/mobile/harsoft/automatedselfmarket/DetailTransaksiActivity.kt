package com.mobile.harsoft.automatedselfmarket

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.harsoft.automatedselfmarket.adapter.DetailTransaksiAdapter
import com.mobile.harsoft.automatedselfmarket.api.ApiRepo
import com.mobile.harsoft.automatedselfmarket.model.DetailTransaksi
import com.mobile.harsoft.automatedselfmarket.model.Keranjang
import com.mobile.harsoft.automatedselfmarket.model.Produk
import com.mobile.harsoft.automatedselfmarket.model.Transaksi
import com.mobile.harsoft.automatedselfmarket.presenter.DetailTransaksiPresenter
import com.mobile.harsoft.automatedselfmarket.view.DetailTransaksiView
import kotlinx.android.synthetic.main.activity_detail_transaksi.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DetailTransaksiActivity : AppCompatActivity(), DetailTransaksiView {

    private var detail: MutableList<DetailTransaksi?> = mutableListOf()
    private var keranjang: MutableList<Keranjang?> = mutableListOf()
    private var produk: MutableList<Produk?> = mutableListOf()
    private lateinit var presenter: DetailTransaksiPresenter
    private lateinit var adapter: DetailTransaksiAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaksi)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val detailTransaksi = intent.getParcelableExtra<Transaksi>("detail_transaksi")
        val namaToko = intent.getStringExtra("nama_toko")
        val alamatToko = intent.getStringExtra("alamat_toko")

        tvIdTransaksi.text = "Transaksi#" + detailTransaksi?.id_transaksi.toString()
        tvNamaToko.text = "Toko $namaToko"
        tvAlamatToko.text = alamatToko
        tvTotalTagihan.text = "Total : Rp." + detailTransaksi?.total_tagihan.toString()
        tvDiterima.text = "Diterima : Rp." + detailTransaksi?.diterima.toString()
        tvKembalian.text = "Kembalian : Rp." + detailTransaksi?.kembalian.toString()
        tvTglTransaksi.text = detailTransaksi?.created_at

        val request = ApiRepo()
        presenter = DetailTransaksiPresenter(this, request)
        presenter.getDetailTransaksi(detailTransaksi?.id_transaksi)

        rvDetail.layoutManager = LinearLayoutManager(this)
        adapter = DetailTransaksiAdapter(this, keranjang, detail, produk)
        rvDetail.adapter = adapter

        shareDetailTransaksi()
    }

    private fun shareDetailTransaksi() {
        shareDetail.setOnClickListener {
            val bitmap = scrollView.drawToBitmap()

            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Automated Self Market")
            intent.putExtra(Intent.EXTRA_TEXT, "Detail transaksi saya!")
            intent.putExtra(Intent.EXTRA_STREAM, saveImage(bitmap))
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.type = "image/png"
            startActivity(Intent.createChooser(intent, "Share with..."))
        }
    }

    private fun saveImage(bitmap: Bitmap): Uri? {
        //TODO - Should be processed in another thread
        val imagesFolder = File(cacheDir, "images")
        var uri: Uri? = null
        try {
            imagesFolder.mkdirs()
            val file = File(imagesFolder, "shared_image.png")
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()
            uri = FileProvider.getUriForFile(
                this,
                "com.mobile.harsoft.automatedselfmarket.fileprovider",
                file
            )
        } catch (e: IOException) {
            Log.d(
                "Error",
                "IOException while trying to write file for sharing: " + e.message
            )
        }
        return uri
    }

    override fun showAlert(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun dataDetail(data: List<DetailTransaksi?>?) {
        detail.clear()
        detail.addAll(data!!)
        adapter.notifyDataSetChanged()
    }

    override fun dataKeranjang(data: List<Keranjang?>?) {
        keranjang.clear()
        keranjang.addAll(data!!)
    }

    override fun dataProduk(data: List<Produk?>?) {
        produk.clear()
        produk.addAll(data!!)
    }
}
