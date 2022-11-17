package com.example.eywa_android

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.activityViewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.eywa_android.Adapters.ProfileImageAdapter
import com.example.eywa_android.ClassObject.User
import com.example.eywa_android.Home.HomeSharedViewModel
import com.example.eywa_android.Management.Bcrypt
import com.example.eywa_android.Management.FilesManager

import com.example.eywa_android.databinding.FragmentEditUserBinding
import com.google.android.material.card.MaterialCardView
import com.google.android.material.transition.MaterialContainerTransform

class EditUserFragment : Fragment() {

    private var _binding : FragmentEditUserBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel : HomeSharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditUserBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        initUser()

        var path = sharedViewModel.displayUser!!.image

        val charactersList = FilesManager.getCharacters(requireContext())
        val imagesList = mutableListOf<String>()
        for (character in charactersList){
            imagesList.add(character.image)
        }
        val imageSelectorAdapter = ProfileImageAdapter(requireContext(), imagesList)

        binding.listProfilePics.layoutManager = GridLayoutManager(requireContext(), 5)

        binding.listProfilePics.adapter = imageSelectorAdapter

        imageSelectorAdapter.setOnClickListener(){

            path = imagesList[binding.listProfilePics.getChildAdapterPosition(it)]
            val imagePath = requireContext().filesDir.path.toString() + "/img/" + path + ".jpeg"
            val bitmap = BitmapFactory.decodeFile(imagePath)
            binding.imageProfile.setImageBitmap(bitmap)
            hideImageSelector()
        }

        binding.imageProfile.setOnClickListener(){

            displayImageSelector()

        }

        binding.btnSubmit.setOnClickListener(){
            saveUserData(path = path)
        }

    }

    private fun initUser(){
        binding.editUser.setText(sharedViewModel.displayUser!!.username)
        binding.editPassword.setText(sharedViewModel.displayUser!!.password)
        val imagePath = requireContext().filesDir.path.toString() + "/img/" + sharedViewModel.displayUser!!.image + ".jpeg"
        val bitmap = BitmapFactory.decodeFile(imagePath)
        binding.imageProfile.setImageBitmap(bitmap)
    }

    private fun saveUserData(path : String){
        //TODO change password

        val users = FilesManager.getUsers(requireContext())
        var userIndex = users.indexOf(sharedViewModel.displayUser!!)

        var index = 0
        var found = false
        do {
            if (users[index].username == binding.editUser.text.toString()){
                found = true
                Toast.makeText(requireContext(), "This username is already set", Toast.LENGTH_LONG).show()
            }
            index++

        } while (index in 0 until users.size && !found)
        if (!found){
            var salt : String = Bcrypt.gensalt()
            var hashedPassword : String = Bcrypt.hashpw(binding.editPassword.text.toString(), salt)
            var newUser : User = User(binding.editUser.text.toString(),hashedPassword,sharedViewModel.displayUser!!.image,
                sharedViewModel.displayUser!!.gender,sharedViewModel.displayUser!!.age,
                sharedViewModel.displayUser!!.quizMatchHistory)
            users[userIndex] = newUser
            FilesManager.saveUser(requireContext(), users)
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_editUserFragment_to_userFragment)
        }

    }

    private fun displayImageSelector(){
        val transition = MaterialContainerTransform()
        val myInterpolator = FastOutSlowInInterpolator()
        transition.scrimColor = Color.TRANSPARENT

        transition.setInterpolator(myInterpolator)

        transition.setDuration(0)

        transition.startView = binding.imageProfile
        transition.endView = binding.endCard
        transition.addTarget(binding.imageProfile)

        TransitionManager.beginDelayedTransition(binding.root, transition as? Transition)

        binding.endCard.visibility = View.VISIBLE

        binding.backgroundOpacity.visibility = View.VISIBLE
        binding.backgroundOpacity.isClickable = true
        binding.backgroundOpacity.visibility = View.VISIBLE
        binding.backgroundOpacity.setOnClickListener(){
            hideImageSelector()
        }

    }

    private fun hideImageSelector(){
        val imagepic = binding.imageProfile
        val transition : MaterialContainerTransform = MaterialContainerTransform()
        val myInterpolator : FastOutSlowInInterpolator = FastOutSlowInInterpolator()
        transition.scrimColor = Color.TRANSPARENT

        transition.setInterpolator(myInterpolator)
        transition.startView = binding.endCard
        transition.endView = binding.imageProfile
        transition.addTarget(binding.endCard)

        TransitionManager.beginDelayedTransition(binding.root, transition as? Transition)

        binding.endCard.visibility = View.INVISIBLE

        binding.imageProfile.visibility = View.VISIBLE
        binding.backgroundOpacity.isClickable = false
        binding.backgroundOpacity.visibility = View.INVISIBLE
    }
}