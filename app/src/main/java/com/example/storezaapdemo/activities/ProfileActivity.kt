package com.example.storezaapdemo.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.storezaapdemo.R
import com.example.storezaapdemo.SharedPrefManager
import com.example.storezaapdemo.databinding.ActivityMainBinding
import com.example.storezaapdemo.ui.home.HomeFragment
import com.example.storezaapdemo.ui.user.UserFragment
import com.example.storezaapdemo.ui.user.UserViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.navigation.NavigationView

class ProfileActivity :  Fragment() {

    private lateinit var etname: TextView
    private lateinit var etemail: TextView
    private lateinit var logout : RelativeLayout
    private lateinit var sharedPrefManager: SharedPrefManager
    private lateinit var imageView: ImageView

    companion object {
        fun newInstance() = SignUpActivity()
    }

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_profile, container, false)



//        supportActionBar?.hide()

        etname = view.findViewById(R.id.etname)
        etemail = view.findViewById(R.id.etemail)
        logout = view.findViewById(R.id.logOutLayout)
        imageView = view.findViewById(R.id.imageProfile)

        imageView.setOnClickListener {

            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }


        logout.setOnClickListener{
            if (sharedPrefManager.isLoggedIn()) {


                android.app.AlertDialog.Builder(this.context)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to Logout?") // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes,
                        DialogInterface.OnClickListener { dialog, which ->
                            clearUserSession()
                        }) // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                  //  .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()


            }
        }


        sharedPrefManager = SharedPrefManager(requireActivity().application)

        val userName = "Hey! " + sharedPrefManager.getUser().username
        etname.text = userName
        etemail.text = sharedPrefManager.getUser().email



        return  view

    }

    private fun clearUserSession() {

        sharedPrefManager.logout()
        // Redirect user to Home screen
        val intent = Intent(this.context, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageView.setImageURI(data?.data)
    }
}


