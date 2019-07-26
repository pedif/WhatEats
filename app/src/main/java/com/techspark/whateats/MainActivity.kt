package com.techspark.whateats

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.ads.consent.*
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val consentInformation = ConsentInformation.getInstance(this)
            val publisherIds = arrayOf(getString(R.string.ad_publisher_id))
            consentInformation.requestConsentInfoUpdate(publisherIds, object : ConsentInfoUpdateListener {
                override fun onConsentInfoUpdated(consentStatus: ConsentStatus) {

                    if (!consentInformation.isRequestLocationInEeaOrUnknown)
                        loadAd(0)
                    else {
                        when (consentStatus) {
                            ConsentStatus.PERSONALIZED -> loadAd(0)
                            ConsentStatus.NON_PERSONALIZED -> loadAd(1)
                            else -> {
                                displayConsentForm()
                            }
                        }
                    }
                }

                override fun onFailedToUpdateConsentInfo(errorDescription: String) {
                }
            })
        }
    }

    fun loadAd(adType: Int) {
        MobileAds.initialize(this, resources.getString(R.string.ad_id))
        val extras = Bundle()
        extras.putString("npa", adType.toString())
        val adReq = AdRequest.Builder()
            .addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
            .build()
        ad_view.loadAd(adReq)
    }

    lateinit var consentForm: ConsentForm
    private fun displayConsentForm() {
        consentForm = ConsentForm.Builder(
            this,
            URL("https://technolanddomain.blogspot.com/p/privacy-policy-pedif-built-whateats-app.html")
        )
            .withListener(object : ConsentFormListener() {
                override fun onConsentFormOpened() {
                    super.onConsentFormOpened()
                }

                override fun onConsentFormLoaded() {
                    super.onConsentFormLoaded()
                    consentForm?.show()
                }

                override fun onConsentFormError(reason: String?) {
                    super.onConsentFormError(reason)
                    ConsentInformation.getInstance(this@MainActivity).consentStatus = ConsentStatus.UNKNOWN
                }

                override fun onConsentFormClosed(consentStatus: ConsentStatus?, userPrefersAdFree: Boolean?) {
                    super.onConsentFormClosed(consentStatus, userPrefersAdFree)

                    when (consentStatus) {
                        ConsentStatus.PERSONALIZED -> loadAd(0)
                        ConsentStatus.NON_PERSONALIZED -> loadAd(1)
                        ConsentStatus.UNKNOWN -> ConsentInformation.getInstance(this@MainActivity).consentStatus =
                            ConsentStatus.UNKNOWN
                    }
                }
            })
            .withPersonalizedAdsOption()
            .withNonPersonalizedAdsOption()
            .build()
        consentForm.load()
    }

}
