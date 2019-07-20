package com.techspark.whateats


import android.media.MediaPlayer
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout.LayoutParams
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_guess.*
import kotlin.random.Random


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

    override fun useFormula(msg: String) {

        val rand = Random(System.currentTimeMillis())

        val formulaTextView = TextView(context).apply {
            animate()
                .alpha(0f)
                .scaleXBy(2.5f)
                .scaleYBy(2.5f)
                .translationX(((rand.nextFloat()*2)-1)*300f)
                .translationY(((rand.nextFloat()*2)-1)*300f)
                .setDuration(1000)

        }
        formulaTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20f)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT).apply { gravity=Gravity.CENTER
            }
        formulaTextView.layoutParams = params
        formulaTextView.text = msg
        layout_msg.addView(formulaTextView)

    }

    override fun onStartGuessing() {

        text_msg.text =""
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
