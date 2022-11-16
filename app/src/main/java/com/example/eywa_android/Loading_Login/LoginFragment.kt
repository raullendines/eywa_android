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
import com.example.eywa_android.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {


    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onStart() {
        super.onStart()

        var users = FilesManager.getUsers(this.requireContext())

        binding.btnRegisterLogin.setOnClickListener(){
            //change fragments
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener(){
           loginFun(users)
        }

        //Turn on password
        binding.btnShow.setOnClickListener {
            if (binding.btnShow.isVisible){
                binding.btnShow.visibility = View.INVISIBLE
                binding.btnHide.visibility = View.VISIBLE
                binding.textPassword.setInputType(InputType.TYPE_CLASS_TEXT)
            }
        }

        //Turn off password
        binding.btnHide.setOnClickListener {
            if (binding.btnHide.isVisible){
                binding.btnShow.visibility =View.VISIBLE
                binding.btnHide.visibility =View.INVISIBLE
                binding.textPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
        }


    }

    fun loginFun(users : MutableList<User>){

        var userExists : Boolean = userExist(binding.textUsername.text.toString(),binding.textPassword.text.toString(),users)
        //Omplir amb les dades per contrastar amb el JSON
        if (binding.textUsername.text.isEmpty() || binding.textPassword.text.isEmpty()){
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

        var existUser = false
        var index = 0

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