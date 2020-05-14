package com.mobile.harsoft.automatedselfmarket.tokofragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.mobile.harsoft.automatedselfmarket.R
import com.mobile.harsoft.automatedselfmarket.ScanProdukActivity
import kotlinx.android.synthetic.main.fragment_dashboard_toko.*

/**
 * A simple [Fragment] subclass.
 */
class DashboardTokoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_dashboard_toko, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnScanProduk.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    ScanProdukActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            )
        }
    }

}
