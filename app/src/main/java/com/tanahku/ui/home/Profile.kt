package com.tanahku.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.tanahku.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fitur1 : LinearLayout
    private lateinit var fitur2 : LinearLayout
    private lateinit var fitur3 : LinearLayout
    private lateinit var fitur4 : LinearLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fitur1 = view.findViewById(R.id.linearRowarrowright)
        fitur2 = view.findViewById(R.id.linearRowarrowrightOne)
        fitur3 = view.findViewById(R.id.linearRowarrowrightTwo)
        fitur4 = view.findViewById(R.id.linearRowarrowrightThree)

        fitur1.setOnClickListener{
            val toast = Toast.makeText(requireActivity(), "Coming Soon...", Toast.LENGTH_SHORT)
            toast.show()
        }
        fitur2.setOnClickListener{
            val toast = Toast.makeText(requireActivity(), "Coming Soon...", Toast.LENGTH_SHORT)
            toast.show()
        }
        fitur3.setOnClickListener{
            val toast = Toast.makeText(requireActivity(), "Coming Soon...", Toast.LENGTH_SHORT)
            toast.show()
        }
        fitur4.setOnClickListener{
            val toast = Toast.makeText(requireActivity(), "Coming Soon...", Toast.LENGTH_SHORT)
            toast.show()
        }


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}