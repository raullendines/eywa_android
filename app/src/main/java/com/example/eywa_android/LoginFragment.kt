package com.example.eywa_android

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController


class LoginFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)

    }

    override fun onStart() {
        super.onStart()
        var img_on = requireView().findViewById<ImageView>(R.id.btnShow)
        var img_off = requireView().findViewById<ImageView>(R.id.btnHide)
        var password = requireView().findViewById<EditText>(R.id.textPassword)
        var btnLogin = requireView().findViewById<Button>(R.id.btnLogin)
        var btnRegister = requireView().findViewById<TextView>(R.id.btnRegisterLogin)

        btnRegister.setOnClickListener(){
            //change fragments
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        btnLogin.setOnClickListener(){
            var username = requireView().findViewById<EditText>(R.id.textUsername)
            var password = requireView().findViewById<EditText>(R.id.textPassword)

            val intentHome = Intent(activity, Home::class.java)
            startActivity(intentHome)
            //Omplir amb les dades per contrastar amb el JSON
        }
        //Turn on password
        img_on.setOnClickListener {
            if (img_on.isVisible){
                img_on.visibility = View.INVISIBLE
                img_off.visibility = View.VISIBLE
                password.setInputType(InputType.TYPE_CLASS_TEXT);
                println("on")
            }
        }

        //Turn off password
        img_off.setOnClickListener {
            if (img_off.isVisible){
                img_on.visibility =View.VISIBLE
                img_off.visibility =View.INVISIBLE
                password.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                println("off")
            }
        }
    }
}