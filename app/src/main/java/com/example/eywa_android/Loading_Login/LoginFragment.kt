package com.example.eywa_android.Loading_Login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.eywa_android.Management.Bcrypt
import com.example.eywa_android.Management.FilesManager
import com.example.eywa_android.Home.HomeActivity
import com.example.eywa_android.R
import com.example.eywa_android.ClassObject.User


class LoginFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            
        }

    }

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

        var users = FilesManager.getUsers(this.requireContext())

        btnRegister.setOnClickListener(){
            //change fragments
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        btnLogin.setOnClickListener(){
           loginFun(users)
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

    fun loginFun(users : MutableList<User>){
        var username = requireView().findViewById<EditText>(R.id.textUsername)
        var password = requireView().findViewById<EditText>(R.id.textPassword)
        var userExists : Boolean = userExist(username.text.toString(),password.text.toString(),users)
        //Omplir amb les dades per contrastar amb el JSON
        if (username.text.isEmpty() || password.text.isEmpty()){
            Toast.makeText(this.activity, "Please fill all camps", Toast.LENGTH_SHORT).show()
        }
        else {
            if (userExists){
                val intentHome = Intent(activity, HomeActivity::class.java)
                startActivity(intentHome)
            }
            else {
                Toast.makeText(this.activity, "User doesn't exist", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun userExist(user : String, password : String, users : MutableList<User>): Boolean {

        var existUser : Boolean = false
        var index : Int = 0

        do {
            var hashedPasswordEquals = Bcrypt.checkpw(password, users[index].password)
            if (users[index].username == user && hashedPasswordEquals)
            {
                existUser = true
            }
            index++
        }while(!existUser && index < users.size)
        return existUser
    }


}