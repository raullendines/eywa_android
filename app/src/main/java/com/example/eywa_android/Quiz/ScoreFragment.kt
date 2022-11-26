package com.example.eywa_android.Quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.eywa_android.Adapters.AchievementAdapter
import com.example.eywa_android.ClassObject.QuizAchievement
import com.example.eywa_android.R
import com.example.eywa_android.databinding.FragmentCharacterBinding
import com.example.eywa_android.databinding.FragmentScoreBinding
import kotlinx.android.synthetic.main.fragment_user.*


class ScoreFragment : Fragment() {

    private var _binding : FragmentScoreBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel : QuizSharedViewModel by activityViewModels()




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
        _binding = FragmentScoreBinding.inflate(inflater, container, false)



        return binding.root
    }


    override fun onStart() {
        super.onStart()

        if (sharedViewModel.hasAchievementUnlocked){
            val achievementAdapter = AchievementAdapter(requireContext(), sharedViewModel.achievementList)
            binding.listAchievements.layoutManager = GridLayoutManager(requireContext(), 4)
            binding.listAchievements.adapter = achievementAdapter
        }
        else {
            println("No has desbloqueado nada")
        }
    }
}