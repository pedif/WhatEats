package com.techspark.whateats


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.techspark.whateats.databinding.FragmentGuessBinding


/**
 *
 *
 */
class GuessFragment : Fragment(), Contract.View {

    lateinit var presenter: Contract.Presenter

    /**
     * Displays and reads the message to the user
     */
     override fun guess(msg: String) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        presenter = GuessPresenter(this,context!!)

        val binding: FragmentGuessBinding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_guess,container,false)
        binding.lifecycleOwner = this
        binding.presenter= presenter
        return inflater.inflate(R.layout.fragment_guess, container, false)
    }



}
