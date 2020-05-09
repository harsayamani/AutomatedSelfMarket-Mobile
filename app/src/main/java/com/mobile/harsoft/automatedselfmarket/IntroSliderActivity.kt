package com.mobile.harsoft.automatedselfmarket

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.AppIntro2
import com.mobile.harsoft.automatedselfmarket.api.ApiRepo
import com.mobile.harsoft.automatedselfmarket.introfragments.Intro1Fragment
import com.mobile.harsoft.automatedselfmarket.introfragments.Intro2Fragment
import com.mobile.harsoft.automatedselfmarket.introfragments.Intro3Fragment
import com.mobile.harsoft.automatedselfmarket.introfragments.Intro4Fragment
import com.mobile.harsoft.automatedselfmarket.model.Pelanggan
import com.mobile.harsoft.automatedselfmarket.presenter.TambahPelangganPresenter
import com.mobile.harsoft.automatedselfmarket.util.PreferenceHelper
import com.mobile.harsoft.automatedselfmarket.view.PelangganView
import kotlinx.android.synthetic.main.fragment_intro4.*

class IntroSliderActivity : AppIntro2(), PelangganView {

    private var preferenceHelper: PreferenceHelper? = null
    private lateinit var presenter: TambahPelangganPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        preferenceState()
    }

    private fun preferenceState() {
        preferenceHelper = PreferenceHelper(this)

        if (preferenceHelper!!.getIntro().equals("no")) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            this.finish()
        }

        addSlide(Intro1Fragment())
        addSlide(Intro2Fragment())
        addSlide(Intro3Fragment())
        addSlide(Intro4Fragment())
        setIndicatorColor(R.color.colorAccent, R.color.colorBlack)
        setNavBarColor(R.color.colorBlack)
        showSkipButton(false)
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)

        preferenceHelper!!.putIntro("no")

        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        this.finish()
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)

        if (etNamaPelanggan.text.isEmpty()) {
            etNamaPelanggan.error = "Nama harus diisi!"
        } else if (etAlamatPelanggan.text.isEmpty()) {
            etAlamatPelanggan.error = "Alamat harus diisi!"
        } else {
            val request = ApiRepo()
            presenter = TambahPelangganPresenter(this, request, preferenceHelper)
            presenter.tambahPelanggan(
                etNamaPelanggan.text.toString().trim(),
                etAlamatPelanggan.text.toString().trim()
            )

            if (preferenceHelper?.getIntro() == "no") {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
            } else {
                val intent = Intent(this, SplashScreenActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
            }
        }
    }

    override fun showAlert(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun pelangganData(data: List<Pelanggan?>) {
    }
}
