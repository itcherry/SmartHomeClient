package com.chernysh.smarthome.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.chernysh.smarthome.R
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.AbsoluteLayout
import com.chernysh.smarthome.utils.dpToPx
import com.chernysh.smarthome.utils.expandClickArea
import kotlinx.android.synthetic.main.layout_flat_main.*


/**
 * Created by Andrii Chernysh on 2019-05-04
 * If you have any questions, please write: andrii.chernysh@uptech.team
 */
class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }
}