package com.einfoplanet.demo.ui.map

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.einfoplanet.demo.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.CancelableCallback
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        const val MAPS_DATA: String = "MAPS_DATA"
        const val USER_ADDRESS: String = "USER_ADDRESS"
        const val USER_LAT: String = "USER_LAT"
        const val USER_LNG: String = "USER_LNG"
    }

    private lateinit var mMap: GoogleMap
    private lateinit var userAddress: String
    private var addressLatitude: Double = 0.0
    private var addressLongitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val bundle: Bundle? = intent.getBundleExtra(MAPS_DATA)
        bundle?.let {
            userAddress = it.getString(USER_ADDRESS)!!
            addressLatitude = it.getString(USER_LAT)!!.toDouble()
            addressLongitude = it.getString(USER_LNG)!!.toDouble()
        }

        if (userAddress.isNotEmpty()) {
            txt_user_address.visibility = View.VISIBLE
            txt_user_address.text = "Address: $userAddress"
        } else {
            txt_user_address.visibility = View.GONE
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val latLngPosition = LatLng(addressLatitude, addressLongitude)
        mMap.addMarker(MarkerOptions().position(latLngPosition))

        val cameraPosition = CameraPosition.Builder()
                .target(latLngPosition)
                .zoom(googleMap.cameraPosition.zoom)
                .build()

        mMap.animateCamera(
                CameraUpdateFactory.newCameraPosition(cameraPosition),
                3000,
                object : CancelableCallback {

                    override fun onFinish() {}

                    override fun onCancel() {}
                }
        )
    }

    fun backPressed(view: View) = onBackPressed()

}
