package com.example.eywa_android.Loading_Login

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.eywa_android.Management.Bcrypt
import com.example.eywa_android.Management.FilesManager
import com.example.eywa_android.R
import com.example.eywa_android.ClassObject.User


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
        var btnRegister = requireView().findViewById<Button>(R.id.BtnRegister)
        var users = FilesManager.getUsers(requireContext())

        btnRegister.setOnClickListener(){
            registerUser(users)
        }

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

    fun registerUser(users : MutableList<User>){
        val username = requireView().findViewById<EditText>(R.id.LblUsernameRegister)
        val password_1 = requireView().findViewById<EditText>(R.id.edit_password)
        val password_2 = requireView().findViewById<EditText>(R.id.repeat_password)
        var registerIntent = false
        if(username.text.isEmpty() || password_1.text.isEmpty() || password_2.text.isEmpty()){
            Toast.makeText(this.activity, "Please fill all camps", Toast.LENGTH_SHORT).show()
        }else{
            var userExist = false
            for (user : User in users) {
                if (user.username.equals(username.text.toString())){
                    Toast.makeText(this.activity, "User already exist", Toast.LENGTH_SHORT).show()
                    userExist = true
                }
            }
            if (!userExist){
                if (password_1.text.toString().equals(password_2.text.toString())){
                    var salt : String = Bcrypt.gensalt()
                    var hashedPassword : String = Bcrypt.hashpw(password_1.text.toString(), salt)
                    var newUser : User = User(username.text.toString(),hashedPassword,"foto.png","male",18)
                    users.add(users.size-1, newUser)
                    FilesManager.saveUser(requireContext(), users)
                    Toast.makeText(this.activity, "User registered", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                }
                else {
                    Toast.makeText(this.activity, "Passwords are different", Toast.LENGTH_SHORT).show()
                }
            }
    }

    }
}