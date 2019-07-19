package com.techspark.whateats


import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_guess.*


/**
 *
 *
 */
class GuessFragment : Fragment(), Contract.View {

    lateinit var presenter: Contract.Presenter
    lateinit var player: MediaPlayer

    /**
     * Displays and reads the message to the user
     */
    override fun guess(msg: String) {

        text_msg.text = msg
    }

    override fun onStartGuessing() {

        button_guess.isEnabled = false
        player.start()
    }

    override fun onStopGuessing() {

        button_guess.isEnabled = true
        if(player.isPlaying)
            player.pause()
        player.seekTo(0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        presenter = GuessPresenter(this, context!!)

        player = MediaPlayer.create(context, R.raw.track_guess)


        return inflater.inflate(R.layout.fragment_guess, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_guess.setOnClickListener {
            presenter.guess()
        }
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }

}
