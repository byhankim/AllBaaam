package com.han.owlmergerprototype.community

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import com.han.owlmergerprototype.R

class OkDialog(owner: Activity) : Dialog(owner) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_create_post_alert)
        val okBTN: Button = findViewById(R.id.alert_ok_btn)
        okBTN.setOnClickListener {
            dismiss()
        }
    }
}