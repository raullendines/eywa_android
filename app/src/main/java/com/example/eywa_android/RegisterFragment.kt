package com.example.eywa_android

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController


class RegisterFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)

    }

    override fun onStart() {
        super.onStart()
        val img_on_1 = requireView().findViewById<ImageView>(R.id.show_pass_btn)
        val img_off_1 = requireView().findViewById<ImageView>(R.id.hide_pass_btn)
        val img_on_2 = requireView().findViewById<ImageView>(R.id.show_pass_btn2)
        val img_off_2 = requireView().findViewById<ImageView>(R.id.hide_pass_btn2)
        val password_1 = requireView().findViewById<EditText>(R.id.edit_password)
        val password_2 = requireView().findViewById<EditText>(R.id.repeat_password)
        var btnLogin = requireView().findViewById<TextView>(R.id.btnLoginRegister)

        btnLogin.setOnClickListener(){
            //NAV
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        //Turn on password 1 edittext
        img_on_1.setOnClickListener {
            if (img_on_1.isVisible){
                img_on_1.visibility = View.INVISIBLE
                img_off_1.visibility = View.VISIBLE
                img_on_2.visibility = View.INVISIBLE
                img_off_2.visibility = View.VISIBLE
                password_1.setInputType(InputType.TYPE_CLASS_TEXT);
                password_2.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        }

        //Turn off password 1 edittext
        img_off_1.setOnClickListener {
            if (img_off_1.isVisible){
                img_on_1.visibility = View.VISIBLE
                img_off_1.visibility = View.INVISIBLE
                img_on_2.visibility = View.VISIBLE
                img_off_2.visibility = View.INVISIBLE
                password_1.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                password_2.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
        }

        //Turn on password 2 edittext
        img_on_2.setOnClickListener {
            if (img_on_2.isVisible){
                img_on_1.visibility = View.INVISIBLE
                img_off_1.visibility = View.VISIBLE
                img_on_2.visibility = View.INVISIBLE
                img_off_2.visibility = View.VISIBLE
                password_1.setInputType(InputType.TYPE_CLASS_TEXT);
                password_2.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        }

        //Turn off password 2 edittext
        img_off_2.setOnClickListener {
            if (img_off_2.isVisible){
                img_on_1.visibility = View.VISIBLE
                img_off_1.visibility = View.INVISIBLE
                img_on_2.visibility = View.VISIBLE
                img_off_2.visibility = View.INVISIBLE
                password_1.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                password_2.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)            }
        }
    }
}