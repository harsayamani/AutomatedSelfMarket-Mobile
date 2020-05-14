package com.mobile.harsoft.automatedselfmarket.tokofragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.harsoft.automatedselfmarket.R
import com.mobile.harsoft.automatedselfmarket.adapter.KeranjangAdapter
import com.mobile.harsoft.automatedselfmarket.database.database
import com.mobile.harsoft.automatedselfmarket.model.Keranjang
import com.mobile.harsoft.automatedselfmarket.model.sqlitemodel.KeranjangSementara
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper
import kotlinx.android.synthetic.main.fragment_keranjang.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update


/**
 * A simple [Fragment] subclass.
 */
class KeranjangFragment : Fragment() {

    private var keranjangs: MutableList<Keranjang?> = mutableListOf()
    private var preferenceHelper: PreferenceHelper? = null
    private var hargaUpdate: Int = 0
    private lateinit var adapter: KeranjangAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_keranjang, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferenceHelper = PreferenceHelper(requireContext())

        adapter = KeranjangAdapter(requireContext(), keranjangs, {
            requireContext().database.use {
                update(
                    KeranjangSementara.TABLE_KERANJANG,
                    KeranjangSementara.KUANTITAS to it.kuantitas!! - 1,
                    KeranjangSementara.HARGA_UPDATE to it.harga_unit!! * (it.kuantitas - 1)
                ).whereArgs(
                    "(" + KeranjangSementara.KERANJANG_ID + "={id})",
                    "id" to it.id_keranjang.toString()
                ).exec()
            }
            adapter.notifyDataSetChanged()
            fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()

        }, {
            requireContext().database.use {
                update(
                    KeranjangSementara.TABLE_KERANJANG,
                    KeranjangSementara.KUANTITAS to it.kuantitas!! + 1,
                    KeranjangSementara.HARGA_UPDATE to it.harga_unit!! * (it.kuantitas + 1)
                ).whereArgs(
                    "(" + KeranjangSementara.KERANJANG_ID + "={id})",
                    "id" to it.id_keranjang.toString()
                ).exec()
            }
            adapter.notifyDataSetChanged()
            fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit()
        })

        rvKeranjang.layoutManager = LinearLayoutManager(context)
        rvKeranjang.adapter = adapter
    }

    override fun onStart() {
        showKeranjang()
        setTotalHarga()
        super.onStart()
    }

    private fun showKeranjang() {
        keranjangs.clear()
        requireContext().database.use {
            val result =
                select(KeranjangSementara.TABLE_KERANJANG).whereArgs(
                    "(" + KeranjangSementara.PELANGGAN_ID + "={id})",
                    "id" to preferenceHelper!!.getIdPelanggan().toString()
                )
            val keranjangList = result.parseList(classParser<Keranjang>())
            keranjangs.addAll(keranjangList)
            adapter.notifyDataSetChanged()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTotalHarga() {
        requireContext().database.use {
            val result =
                select(KeranjangSementara.TABLE_KERANJANG).whereArgs(
                    "(" + KeranjangSementara.PELANGGAN_ID + "={id})",
                    "id" to preferenceHelper!!.getIdPelanggan().toString()
                )
            val state = result.parseList(classParser<Keranjang>())
            hargaUpdate = 0
            for (data in state) {
                hargaUpdate += data.kuantitas!! * data.harga_unit!!
            }
            tvTotalHarga.text = "Rp.$hargaUpdate"
        }
    }
}
