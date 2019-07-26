package com.techspark.whateats


import android.animation.Animator
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
import com.crashlytics.android.Crashlytics
import kotlinx.android.synthetic.main.fragment_guess.*
import kotlin.random.Random


/**
 *This call implements the view part of the contract
 * @see Contract
 *
 */
class GuessFragment : Fragment(), Contract.View {


    /**
     * Holds the presenter of the contract
     * @see Contract
     */
    lateinit var presenter: Contract.Presenter
    lateinit var player: MediaPlayer
    var isPlayerReady = false

    /**
     * Displays the message to the user
     */
    override fun guess(msg: String) {

        text_msg.text = msg
    }

    /**
     * Displays an animating formula to the user every time called
     */
    override fun useFormula(msg: String) {

        val rand = Random(System.currentTimeMillis())
        /*
            Chaining View creation with its animation and removing the
            view from the parent when the animation is done
         */
        val formulaTextView = TextView(context).apply {
            animate()
                .alpha(0f)
                .scaleXBy(2.5f)
                .scaleYBy(2.5f)
                .translationX(((rand.nextFloat() * 2) - 1) * 300f)
                .translationY(((rand.nextFloat() * 2) - 1) * 300f)
                .setDuration(1000)
                .setListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        if (activity != null && !activity?.isFinishing!!)
                            layout_msg.removeView(this@apply)
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                    }

                    override fun onAnimationStart(animation: Animator?) {
                    }

                })

        }

        formulaTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.CENTER
        }
        formulaTextView.layoutParams = params
        formulaTextView.text = msg
        layout_msg.addView(formulaTextView)

    }

    /**
     * This method would be called from {@link presenter} when guessing is started
     * @see GuessPresenter
     */
    override fun onStartGuessing() {

        text_msg.text = ""
        button_guess.isEnabled = false
        try {
            if (isPlayerReady)
                player.start()
        } catch (e: Exception) {
            Crashlytics.logException(e)
        }
    }

    /**
     * This method would be called from {@link presenter} when guessing is done
     * @see GuessPresenter
     */
    override fun onStopGuessing() {

        button_guess.isEnabled = true
        try {
            if (player.isPlaying)
                player.pause()
            player.seekTo(0)
        } catch (e: Exception) {
            Crashlytics.logException(e)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        presenter = GuessPresenter(this, context!!)

        player = MediaPlayer.create(context, R.raw.track_guess).apply {
            setOnPreparedListener { isPlayerReady = true }
            setOnErrorListener { mp, what, extra ->
                isPlayerReady = false
                return@setOnErrorListener true
            }
        }



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

        //Release the player and stop the presenter
        player.release()
        presenter.stop()
    }

}
