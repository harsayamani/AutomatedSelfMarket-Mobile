package com.mobile.harsoft.automatedselfmarket.mainfragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.harsoft.automatedselfmarket.DetailTransaksiActivity
import com.mobile.harsoft.automatedselfmarket.R
import com.mobile.harsoft.automatedselfmarket.adapter.TransaksiAdapter
import com.mobile.harsoft.automatedselfmarket.api.ApiRepo
import com.mobile.harsoft.automatedselfmarket.model.Toko
import com.mobile.harsoft.automatedselfmarket.model.Transaksi
import com.mobile.harsoft.automatedselfmarket.presenter.TransaksiPresenter
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper
import com.mobile.harsoft.automatedselfmarket.view.TransaksiView
import kotlinx.android.synthetic.main.fragment_transaksi.*


class TransaksiFragment : Fragment(), TransaksiView {

    private var preferenceHelper: PreferenceHelper? = null
    private var transaksi: MutableList<Transaksi?> = mutableListOf()
    private var toko: MutableList<Toko?> = mutableListOf()
    private lateinit var presenter: TransaksiPresenter
    private lateinit var adapter: TransaksiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_transaksi, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceHelper = PreferenceHelper(this.requireContext())
        val request = ApiRepo()
        presenter = TransaksiPresenter(this, request)
        presenter.getTransaksi(preferenceHelper?.getIdPelanggan())

        rvTransaksi.layoutManager = LinearLayoutManager(context)
        adapter = TransaksiAdapter(this.requireContext(), transaksi, toko) {
            var namaToko = ""
            var alamatToko = ""

            for (data in toko) {
                if (data?.id_toko == it.id_toko) {
                    namaToko = data?.nama_toko.toString()
                    alamatToko = data?.alamat.toString()
                    break
                }
            }

            val intent = Intent(context, DetailTransaksiActivity::class.java)
            intent.putExtra("detail_transaksi", it)
            intent.putExtra("nama_toko", namaToko)
            intent.putExtra("alamat_toko", alamatToko)
            startActivity(intent)
        }

        rvTransaksi.adapter = adapter

        swipe.setOnRefreshListener {
            presenter.getTransaksi(preferenceHelper?.getIdPelanggan())
            swipe.isRefreshing = false
        }
    }

    override fun showAlert(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun dataTransaksi(data: List<Transaksi?>?) {
        swipe.isRefreshing = false
        transaksi.clear()
        transaksi.addAll(data!!)
        adapter.notifyDataSetChanged()
    }

    override fun dataToko(data: List<Toko?>?) {
        toko.clear()
        toko.addAll(data!!)
    }
}
