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
import com.example.eywa_android.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

        private var _binding : FragmentRegisterBinding? = null
        private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root

    //        private var _binding : FragmentLoginBinding? = null
//        private val binding get() = _binding!!
//        _binding = FragmentLoginBinding.inflate(inflater, container, false)
//
//        return binding.root

    }

    override fun onStart() {
        super.onStart()

        var users = FilesManager.getUsers(requireContext())

        binding.BtnRegister.setOnClickListener(){
            registerUser(users)
        }

        binding.btnLoginRegister.setOnClickListener(){
            //NAV
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        //Turn on password 1 edittext
        binding.showPassBtn.setOnClickListener {
            if (binding.showPassBtn.isVisible){
                binding.showPassBtn.visibility = View.INVISIBLE
                binding.hidePassBtn.visibility = View.VISIBLE
                binding.showPassBtn2.visibility = View.INVISIBLE
                binding.hidePassBtn2.visibility = View.VISIBLE
                binding.editPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.repeatPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        }

        //Turn off password 1 edittext
        binding.hidePassBtn.setOnClickListener {
            if (binding.hidePassBtn.isVisible){
                binding.showPassBtn.visibility = View.VISIBLE
                binding.hidePassBtn.visibility = View.INVISIBLE
                binding.showPassBtn2.visibility = View.VISIBLE
                binding.hidePassBtn2.visibility = View.INVISIBLE
                binding.editPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                binding.repeatPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            }
        }

        //Turn on password 2 edittext
        binding.showPassBtn2.setOnClickListener {
            if (binding.showPassBtn2.isVisible){
                binding.showPassBtn.visibility = View.INVISIBLE
                binding.hidePassBtn.visibility = View.VISIBLE
                binding.showPassBtn2.visibility = View.INVISIBLE
                binding.hidePassBtn2.visibility = View.VISIBLE
                binding.editPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                binding.repeatPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            }
        }

        //Turn off password 2 edittext
        binding.hidePassBtn2.setOnClickListener {
            if (binding.hidePassBtn2.isVisible){
                binding.showPassBtn.visibility = View.VISIBLE
                binding.hidePassBtn.visibility = View.INVISIBLE
                binding.showPassBtn2.visibility = View.VISIBLE
                binding.hidePassBtn2.visibility = View.INVISIBLE
                binding.editPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
                binding.repeatPassword.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)            }
        }
    }

    fun registerUser(users : MutableList<User>){

        if(binding.LblUsernameRegister.text.isEmpty() || binding.editPassword.text.isEmpty() || binding.repeatPassword.text.isEmpty()){
            Toast.makeText(this.activity, "Please fill all camps", Toast.LENGTH_SHORT).show()
        }else{
            var userExist = false
            for (user : User in users) {
                if (user.username.equals(binding.LblUsernameRegister.text.toString())){
                    Toast.makeText(this.activity, "User already exist", Toast.LENGTH_SHORT).show()
                    userExist = true
                }
            }
            if (!userExist){
                if (binding.editPassword.text.toString().equals(binding.repeatPassword.text.toString())){
                    var salt : String = Bcrypt.gensalt()
                    var hashedPassword : String = Bcrypt.hashpw(binding.editPassword.text.toString(), salt)
                    var newUser : User = User(binding.LblUsernameRegister.text.toString(),hashedPassword,"foto.png","male",18)
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