package com.example.storezaapdemo.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.storezaapdemo.R
import com.example.storezaapdemo.RetrofitClient
import com.example.storezaapdemo.ui.services.ServicesFragment
import com.example.storezaapdemo.ui.user.UserFragment
import com.example.storezaapdemo.ui.user.UserViewModel
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SignUpActivity :  Fragment(), View.OnClickListener{

    private lateinit var loginlink: TextView
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var register: Button
    companion object {
        fun newInstance() = SignUpActivity()
    }

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_sign_up, container, false)


        //hide status bar
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )

        name = view.findViewById(R.id.etname)
        email = view.findViewById(R.id.etemail)
        password = view.findViewById(R.id.etpassword)
        register = view.findViewById(R.id.btnregister)
        loginlink = view.findViewById(R.id.loginlink)

        loginlink.setOnClickListener(this)
        register.setOnClickListener(this)
        return view
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnregister -> registerUser()
            R.id.loginlink -> switchOnLogin()
        }
    }

    private fun registerUser() {
        val userName = name.text.toString()
        val userEmail = email.text.toString()
        val userPassword = password.text.toString()

        if (userName.isEmpty()) {
            name.requestFocus()
            name.error = "Please enter your name"
            return
        }
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

        val call = RetrofitClient
            .getInstance()
            .getApi()
            .register(userName, userEmail, userPassword)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    val responseBody: ResponseBody = Objects.requireNonNull(response.body())!!
                    val body = responseBody.string()
                    val jsonObject = JSONObject(body)
                    val loginResponse = jsonObject.getString("message")
                    val errorResponse = jsonObject.getString("error")
                    if (errorResponse.equals("200")) {
                        switchOnLogin()
                        Toast.makeText(
                            activity,
                            loginResponse,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            activity,
                            loginResponse,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun switchOnLogin() {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        if (transaction != null) {
            transaction.replace(R.id.nav_host_fragment_content_main, UserFragment())
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
//        val i = Intent(this@SignUpActivity, LoginActivity::class.java)
//        startActivity(i)
    }
}
