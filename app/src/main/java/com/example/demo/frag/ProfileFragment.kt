package com.example.demo.frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.util.ActivityBase
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ProfileFragment : Fragment() {

    private val toolbar: Toolbar? = null
    private val collapsingToolbar: CollapsingToolbarLayout? = null
    private val appBarLayout: AppBarLayout? = null
    private val recList: RecyclerView? = null
    private val collapseMenu: Menu? = null
    private val etProfile:FloatingActionButton?=null
    private val appBarExpanded = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         view.findViewById<LinearLayout>(R.id.et_profile).setOnClickListener {
             (requireActivity() as ActivityBase).toasty_error("Profile Module is under process")
         }
        }

}
