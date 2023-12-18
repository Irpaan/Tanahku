package com.tanahku.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.tanahku.R
import com.tanahku.data.UserPreference
import com.tanahku.data.dataStore
import com.tanahku.ui.ViewModelFactory
import com.tanahku.ui.signin.SignInViewModel

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
    private lateinit var signout : LinearLayout
    private lateinit var name : TextView
    private lateinit var signInViewModel: SignInViewModel

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fitur1 = view.findViewById(R.id.linearRowarrowright)
        fitur2 = view.findViewById(R.id.linearRowarrowrightOne)
        fitur3 = view.findViewById(R.id.linearRowarrowrightTwo)
        fitur4 = view.findViewById(R.id.linearRowarrowrightThree)
        signout = view.findViewById(R.id.linearRowfirrsignout)
        name = view.findViewById(R.id.username)



        signInViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
        )[SignInViewModel::class.java]

        signInViewModel.getUser().observe(this, { signInResult ->
            val nama = signInResult?.name
            if (nama != null) {
                // Set nilai nama ke dalam TextView
                name.text = nama
            } else {
                // Handle jika nilai nama null, jika diperlukan
            }
        })

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
        signout.setOnClickListener{
            signInViewModel.SignOut()
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