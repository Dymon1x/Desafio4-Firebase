package br.com.digitalhouse.firebaselogin_desafio4.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.digitalhouse.firebaselogin_desafio4.R


class ListHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_home, container, false)



        return view
    }

    companion object {
        fun newInstance() = ListHomeFragment()
    }
}