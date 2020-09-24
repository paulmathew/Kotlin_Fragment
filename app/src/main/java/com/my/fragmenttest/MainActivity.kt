package com.my.fragmenttest

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        b1.setOnClickListener {
            openFragment("1")
        }


    }

    fun openFragment(tag: String) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (tag == "1")
            fragmentTransaction.replace(R.id.frame, BlankFragment.newInstance("Word ", " Para"))
        else
            fragmentTransaction.replace(
                R.id.frame,
                BlankFragment2.newInstance("2nd Fragment", "Display")
            )
        fragmentTransaction.commit()

    }
}