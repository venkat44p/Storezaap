package com.example.storezaapdemo.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.storezaapdemo.R
import com.example.storezaapdemo.RetrofitClient
import com.example.storezaapdemo.SharedPrefManager
import com.example.storezaapdemo.activities.ProfileActivity
import com.example.storezaapdemo.activities.SignUpActivity
import com.example.storezaapdemo.model.User
import com.example.storezaapdemo.ui.services.ServicesFragment
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Objects.requireNonNull


class UserFragment : Fragment() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var forgetpassword: TextView
    private lateinit var registerlink: TextView
    private lateinit var sharedPrefManager: SharedPrefManager

    companion object {
        fun newInstance() = UserFragment()
    }

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)


        email = view.findViewById(R.id.etemail)
        password = view.findViewById(R.id.etpassword)
        forgetpassword = view.findViewById(R.id.forgetPassword)
        login = view.findViewById(R.id.btnlogin)
        registerlink = view.findViewById(R.id.registerlink)

        registerlink.setOnClickListener {
            switchOnRegister()
        }
        login.setOnClickListener {
           userLogin()

        }

        /*forgetpassword.setOnClickListener {
            Intent intent = Intent(this, ForgetActivity::class.java)
            startActivity(intent)
        }*/

        sharedPrefManager = SharedPrefManager(requireActivity().application)

        return view
    }

    private fun userLogin() {
        val userEmail = email.text.toString()
        val userPassword = password.text.toString()

        if (userEmail.isEmpty()) {
            email.requestFocus()
            email.error = "Please enter your email"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            email.requestFocus()
            email.error = "Please enter correct email"
            return
        }
        if (userPassword.isEmpty()) {
            password.requestFocus()
            password.error = "Please enter your password"
            return
        }
        if (userPassword.length < 8) {
            password.requestFocus()
            password.error = "Please enter your password"
            return
        }


        val call: Call<ResponseBody> =
            RetrofitClient.getInstance().getApi().login(userEmail, userPassword)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                val responseBody: ResponseBody = requireNonNull(response.body())!!
                val body = responseBody.string()
                val jsonObject = JSONObject(body)
                if (response.isSuccessful) {
                    val loginResponse = jsonObject.getString("message")
                val errorResponse = jsonObject.getString("error")
                    if (errorResponse.equals("200")) {



                        val jsonuser = JSONObject(jsonObject.getString("user"))
                        Log.d(
                            "TAG",
                            jsonuser.getString("username") + "onResponse: ......................." + body
                        )

                        var user = User(
                            jsonuser.getString("id"),
                            jsonuser.getString("username"),
                            jsonuser.getString("email")
                        );
                        sharedPrefManager.saveUser(user)
                        val transaction = activity?.supportFragmentManager?.beginTransaction()
                        if (transaction != null) {
                            transaction.replace(R.id.nav_host_fragment_content_main, ProfileActivity())
                            transaction.disallowAddToBackStack()
                            transaction.commit()
                        }

//                        val intent = Intent(activity, ProfileActivity::class.java)
//                        intent.flags =
//                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        startActivity(intent)
                        Toast.makeText(activity, loginResponse.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }else{
                        Toast.makeText(activity, loginResponse.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }else{
                    Toast.makeText(activity, "Error please try again later", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun switchOnRegister() {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.replace(R.id.nav_host_fragment_content_main, SignUpActivity())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }


//        val i = Intent(activity, SignUpActivity::class.java)
//        startActivity(i)
    }

    override fun onStart() {
        super.onStart()

        if (sharedPrefManager.isLoggedIn()) {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            if (transaction != null) {
                transaction.replace(R.id.nav_host_fragment_content_main, ProfileActivity())
                transaction.disallowAddToBackStack()
                transaction.commit()
            }

//            val intent = Intent(activity, ProfileActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        // TODO: Use the ViewModel
    }

}