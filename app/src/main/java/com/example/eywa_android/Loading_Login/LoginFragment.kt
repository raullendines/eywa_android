package com.example.eywa_android.Loading_Login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.example.eywa_android.Home.HomeActivity
import com.example.eywa_android.R
import com.example.eywa_android.ClassObject.User
import com.example.eywa_android.Utility.Bcrypt
import com.example.eywa_android.Utility.FilesManager
import com.example.eywa_android.databinding.FragmentDifficultyBinding
import com.example.eywa_android.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.*


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

    fun animationLogin(animation: LottieAnimationView){
        animation.setAnimation(R.raw.loading_login)
        animation.visibility = View.VISIBLE
        animation.alpha = 1.0f
        animation.playAnimation()

        var layoutLogin = binding.layoutLogin;
        layoutLogin.setBackgroundColor(Color.parseColor("#A6000000"));

        object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                Handler().postDelayed({
                    layoutLogin.setBackgroundColor(Color.parseColor("#00000000"));
                    animation.visibility = View.INVISIBLE
                }, 100)
            }
        }.start()
    }

    fun loginFun(users : MutableList<User>){
        //animationLogin(binding.animationLogin)
        var userExists = userExist(binding.textUsername.text.toString(),binding.textPassword.text.toString(),users)



        //Omplir amb les dades per contrastar amb el JSON
        if (binding.textUsername.text.isEmpty() || binding.textPassword.text.isEmpty()){
            Toast.makeText(this.activity, "Please fill all camps", Toast.LENGTH_SHORT).show()
        }
        else {
            if (userExists != -1){
                val intentHome = Intent(activity, HomeActivity::class.java)
                val userToSend = users[userExists]
                intentHome.putExtra("USER", userToSend)
                startActivity(intentHome)
            }
            else {
                Toast.makeText(this.activity, "User doesn't exist", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun userExist(user : String, password : String, users : MutableList<User>): Int {

        var existUser = false
        var index = 0
        var returnIndex = -1

        do {
            if (users[index].username == user)
            {
                if (Bcrypt.checkpw(password,users[index].password)){
                    existUser = true
                    returnIndex = index
                }
            }
            index++
        }while(!existUser && index < users.size)

        return returnIndex
    }


}