package com.tanahku.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tanahku.R
import com.tanahku.data.UserPreference
import com.tanahku.ui.ViewModelFactory
import com.tanahku.ui.signin.SignInViewModel
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var signInViewModel: SignInViewModel
    private lateinit var img1: ImageView
    private lateinit var fitur1: LinearLayout
    private lateinit var fitur2: LinearLayout
    private lateinit var fitur3: LinearLayout
    private lateinit var name : TextView

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signInViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
        )[SignInViewModel::class.java]

        name = view.findViewById(R.id.username)



        signInViewModel.getUser().observe(this, { signInResult ->
            val nama = signInResult?.name
            if (nama != null) {
                // Set nilai nama ke dalam TextView
                name.text = nama
            } else {
                // Handle jika nilai nama null, jika diperlukan
            }
        })



        img1 = view.findViewById(R.id.img_home)
        fitur1 = view.findViewById(R.id.linearFitur)
        fitur2 = view.findViewById(R.id.linearFitur2)
        fitur3 = view.findViewById(R.id.linearFitur3)



        img1.setOnClickListener{
            val toast = Toast.makeText(requireActivity(), "Silahkan Klik Tombol Scan", Toast.LENGTH_SHORT)
            toast.show()
        }
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
        return inflater.inflate(R.layout.fragment_home, container, false)


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}