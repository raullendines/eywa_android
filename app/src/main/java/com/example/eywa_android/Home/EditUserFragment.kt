package com.example.eywa_android.Home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.transition.Transition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.eywa_android.Adapters.ProfileImageAdapter
import com.example.eywa_android.ClassObject.User
import com.example.eywa_android.Utility.FilesManager
import com.example.eywa_android.Utility.Bcrypt

import com.example.eywa_android.databinding.FragmentEditUserBinding
import com.google.android.material.transition.MaterialContainerTransform
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream

class EditUserFragment : Fragment() {

    private var _binding : FragmentEditUserBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel : HomeSharedViewModel by activityViewModels()

    private val pickImage = 100
    private var imageUri : Uri? = null

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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        initUser()



        var path = sharedViewModel.displayUser!!.image

        if (imageUri != null){
            val customImagePath = createFileFromContentUri(imageUri!!)
            val pathWithoutFormat = customImagePath.dropLast(4)
            path = pathWithoutFormat

        }

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

        binding.buttonGallery.setOnClickListener(){
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        binding.btnSubmit.setOnClickListener(){
            saveUserData(path = path)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage){
            imageUri = data?.data
            //binding.imageProfile.setImageURI(imageUri)
        }
    }

    private fun initUser(){
        binding.editUser.setText(sharedViewModel.displayUser!!.username)
        val imagePath = requireContext().filesDir.path.toString() + "/img/" + sharedViewModel.displayUser!!.image + ".jpeg"
        val bitmap = BitmapFactory.decodeFile(imagePath)
        binding.imageProfile.setImageBitmap(bitmap)
    }

    private fun saveUserData(path : String){
        //TODO change password

        val users = FilesManager.getUsers(requireContext())
        var testIndex = 0
        var userIndex = -1
        var found = false
        do{
            if (sharedViewModel.displayUser!!.username == users[testIndex].username){
                found = true
                userIndex = testIndex
            }
            testIndex++
        } while (!found && testIndex in 0 until users.size)



        var index = 0
        found = false
        do {
            if (index != userIndex && users[index].username == binding.editUser.text.toString()){
                found = true
                Toast.makeText(requireContext(), "This username is already set", Toast.LENGTH_LONG).show()
            }
            index++

        } while (index in 0 until users.size && !found)
        if (!found){
            var newPassword = sharedViewModel.displayUser!!.password
            if (!binding.editPassword.text.isNullOrEmpty()){
                var salt : String = Bcrypt.gensalt()
                newPassword = Bcrypt.hashpw(binding.editPassword.text.toString(), salt)
            }

            var newUser : User = User(
                id = sharedViewModel.displayUser!!.id,
                username = binding.editUser.text.toString(),
                //sharedViewModel.displayUser!!.password,
                password = newPassword,
                image = path,
                quizAchievementList = sharedViewModel.displayUser!!.quizAchievementList,
                sharedViewModel.displayUser!!.dateOfRegister)
            users[userIndex] = newUser
            FilesManager.saveUser(requireContext(), users)
            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_LONG).show()
            sharedViewModel.setUserToDisplay(newUser)
            findNavController().popBackStack()
            //findNavController().navigate(R.id.action_editUserFragment_to_userFragment)
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





    //Image URI handler
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createFileFromContentUri(fileUri : Uri) : String {

        var fileName : String = ""

        fileUri.let { returnUri ->
            requireActivity().contentResolver.query(returnUri,null,null,null)
        }?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            fileName = cursor.getString(nameIndex)
        }

    //  For extract file mimeType
        val fileType: String? = fileUri.let { returnUri ->
            requireActivity().contentResolver.getType(returnUri)
        }

        val iStream : InputStream =
            requireActivity().contentResolver.openInputStream(fileUri)!!
        val outputDir = File(requireContext().filesDir.path.toString() + "/img/")
        val outputFile : File = File(outputDir,fileName)
        copyStreamToFile(iStream, outputFile)
        iStream.close()


        val imagePath =requireContext().filesDir.path.toString() + "/img/" + fileName
        val bitmap = BitmapFactory.decodeFile(imagePath)
        binding.imageProfile.setImageBitmap(bitmap)

        return  fileName
    }

    private fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }
}