package com.app.kotlinbasicslearn.fragmentcycle

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.kotlinbasicslearn.R
import com.app.kotlinbasicslearn.databinding.FragmentKotlinChatBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [KotlinChatFrg.newInstance] factory method to
 * create an instance of this fragment.
 */
class KotlinChatFrg : Fragment() {

    interface OnResultPass {
        fun onResultPass(data: String)
    }

    private lateinit var resultPasser: OnResultPass

    override fun onAttach(context: Context) {
        super.onAttach(context)
        resultPasser = context as OnResultPass
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentKotlinChatBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKotlinChatBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txvUser.text = arguments?.getString("user")
        binding.txvUserAge.text = arguments?.getInt("age").toString()
        binding.txvIsStd.text = arguments?.getBoolean("isStudent").toString()

        binding.textView.setOnClickListener {
            resultPasser.onResultPass("Hello from Chat Fragment")
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment KotlinChatFrg.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            KotlinChatFrg().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}