package br.com.digitalhouse.firebaselogin_desafio4

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.digitalhouse.firebaselogin_desafio4.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var bind: FragmentHomeBinding? = null
    private val binding get() = bind!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)

        bind = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root



        return view
    }

}