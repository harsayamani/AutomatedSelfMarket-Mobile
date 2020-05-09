package com.mobile.harsoft.automatedselfmarket.mainfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.harsoft.automatedselfmarket.R
import com.mobile.harsoft.automatedselfmarket.adapter.RiwayatTokoAdapter
import com.mobile.harsoft.automatedselfmarket.api.ApiRepo
import com.mobile.harsoft.automatedselfmarket.model.RiwayatToko
import com.mobile.harsoft.automatedselfmarket.model.Toko
import com.mobile.harsoft.automatedselfmarket.presenter.RiwayatTokoPresenter
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper
import com.mobile.harsoft.automatedselfmarket.view.RiwayatTokoView
import kotlinx.android.synthetic.main.fragment_riwayat_toko.*

/**
 * A simple [Fragment] subclass.
 */
class RiwayatTokoFragment : Fragment(), RiwayatTokoView {

    private var preferenceHelper: PreferenceHelper? = null
    private var riwayatToko: MutableList<RiwayatToko?> = mutableListOf()
    private var toko: MutableList<Toko?> = mutableListOf()
    private lateinit var presenter: RiwayatTokoPresenter
    private lateinit var adapter: RiwayatTokoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_riwayat_toko, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferenceHelper = PreferenceHelper(this.requireContext())
        val request = ApiRepo()
        presenter = RiwayatTokoPresenter(this, request)
        presenter.getRiwayatToko(preferenceHelper?.getIdPelanggan())

        rvRiwayatToko.layoutManager = LinearLayoutManager(context)
        adapter = RiwayatTokoAdapter(this.requireContext(), riwayatToko, toko)
        rvRiwayatToko.adapter = adapter

        swipe.setOnRefreshListener {
            presenter.getRiwayatToko(preferenceHelper?.getIdPelanggan())
            swipe.isRefreshing = false
        }
    }

    override fun showAlert(message: String?) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun dataRiwayatToko(data: List<RiwayatToko?>) {
        swipe.isRefreshing = false
        riwayatToko.clear()
        riwayatToko.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun dataToko(data: List<Toko?>) {
        toko.clear()
        toko.addAll(data)
    }
}
