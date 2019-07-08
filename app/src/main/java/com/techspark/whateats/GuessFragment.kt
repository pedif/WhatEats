package com.techspark.whateats


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


/**
 * A simple [Fragment] subclass.
 *
 */
class GuessFragment : Fragment(), Contract.View {

    lateinit var presenter: Contract.Presenter

     override fun guess() {
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        
        presenter = GuessPresenter(this)
        return inflater.inflate(R.layout.fragment_guess, container, false)
    }



}
