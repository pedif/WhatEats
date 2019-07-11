package com.techspark.whateats


import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_guess.*
import java.util.*


/**
 *
 *
 */
class GuessFragment : Fragment(), Contract.View {

    lateinit var presenter: Contract.Presenter
    lateinit var speaker: TextToSpeech
    var isSpeakReady = true

    /**
     * Displays and reads the message to the user
     */
    override fun guess(msg: String) {

        text_msg.text = msg

        if(switch_talk.isChecked)
        read(msg)

    }

    private fun read(msg: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            speaker.speak(msg,TextToSpeech.QUEUE_FLUSH,null,UUID.randomUUID().toString())
        }else{
            speaker.speak(msg,TextToSpeech.QUEUE_FLUSH,null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        presenter = GuessPresenter(this, context!!)
        speaker = TextToSpeech(context) {
            if (it == TextToSpeech.ERROR) isSpeakReady = false
        }
        speaker.setSpeechRate(0.7f)

        return inflater.inflate(R.layout.fragment_guess, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image_button_guess.setOnClickListener {
            presenter.guess()
        }
    }


}
