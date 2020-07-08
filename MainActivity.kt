package com.example.qrcodescanner

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.Result
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterActivity
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.activity_main.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.util.jar.Manifest

public class MainActivity : AppCompatActivity(),ZXingScannerView.ResultHandler{


    lateinit var Scannerview: ZXingScannerView
    var context:Context=this
    var grant:Context?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Scannerview=scanner
        grant=this
        Dexter.withContext(this)
            .withPermission(android.Manifest.permission.CAMERA)
            .withListener( object: PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    Scannerview.setResultHandler(this@MainActivity)
                    Scannerview.startCamera()
                    Log.d("SCANQR","Started")
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {

                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(baseContext,"You must accept these permissions",Toast.LENGTH_SHORT).show()
                }

            }).check()

    }

    override fun onDestroy() {
        Log.d("SCANQR","destroyed")
        super.onDestroy()
        Scannerview.stopCamera()
    }

    override fun onStop() {
        Log.d("SCANQR","stopped")
        super.onStop()
        Scannerview.stopCamera()
    }
    override fun handleResult(p0: Result?) {
        Log.d("SCANQR","value=${p0!!.text}")
        result.text=p0!!.text

        Toast.makeText(this,"value=${p0.text}",Toast.LENGTH_SHORT).show()
    }

}


