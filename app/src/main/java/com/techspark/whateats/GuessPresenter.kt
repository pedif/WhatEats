package com.techspark.whateats

import com.techspark.whateats.Contract.View
import android.content.Context
import java.util.*
import android.os.Handler
import kotlin.concurrent.schedule
import kotlin.random.Random

/**
 * This class implements the presenter part of the contract
 */
class GuessPresenter(private val view: View, context: Context) : Contract.Presenter {

    /**
     * Contains a list of all the foods which are going to be guessed from
     */
    private val foods: Array<String> = context.resources.getStringArray(R.array.foods)

    /**
     * Contains a list of all the formulas which are going to be guessed from
     */
    private val formulas: Array<String> = context.resources.getStringArray(R.array.formulas)

    /**
     * Handler responsible for relaying messages to the view from a background thread
     */
    private val uiScope = Handler()

    /**
     * Flag to demonstrate whether the view has finished
     */
    private var hasStopped = false

    /**
     * Picks a random number and selects the respective index of food array
     */
    override fun guess() {

        view.onStartGuessing()
        val rand = Random(System.currentTimeMillis())
        guess(15, rand)
    }

    /**
     * Counts the times formulas are displayed to the user before displaying the result
     */
    var count = 0

    /**
     * Recursive method which would display a formula formulaCount times then displays the result and returns
     * @param formulaCount times a random formula needs to be shown
     * @param rand Random generator to be used for random guessing
     */
    private fun guess(formulaCount: Int, rand: Random) {

        if (count > formulaCount) {
            count = 0
            Timer().schedule(1000) {
                if(hasStopped)
                    return@schedule
                uiScope.post {
                    view.guess(foods[rand.nextInt(0, foods.size)])
                    view.onStopGuessing()
                }
            }
            return
        }
        count++

        Timer().schedule(300) {
            if(hasStopped)
                return@schedule
            uiScope.post {
                view.useFormula(formulas[rand.nextInt(0, formulas.size)])
            }
            guess(formulaCount, rand)
        }
    }

    /**
     * Sets the @see #hasStopped flag to end all threads
     */
    override fun stop() {
        hasStopped=true
    }


}