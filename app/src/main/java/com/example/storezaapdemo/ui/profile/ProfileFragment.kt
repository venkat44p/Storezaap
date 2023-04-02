package com.example.storezaapdemo.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.storezaapdemo.R
import com.example.storezaapdemo.SharedPrefManager
import com.example.storezaapdemo.activities.ProfileActivity
import com.example.storezaapdemo.ui.home.HomeFragment
import com.example.storezaapdemo.ui.user.UserFragment


class ProfileFragment : Fragment() {

    private lateinit var etname: TextView
    private lateinit var etemail: TextView
    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        etname = view.findViewById(R.id.etname)
        etemail = view.findViewById(R.id.etemail)

        sharedPrefManager = SharedPrefManager(requireActivity())

        val userName = "Hey! " + sharedPrefManager.getUser().username
        etname.text = userName
        etemail.text = sharedPrefManager.getUser().email


        return view
    }
}