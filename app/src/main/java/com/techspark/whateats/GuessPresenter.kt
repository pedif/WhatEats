package com.techspark.whateats

import com.techspark.whateats.Contract.View
import android.content.Context
import java.util.*
import android.os.Handler
import kotlin.concurrent.schedule
import kotlin.random.Random

class GuessPresenter(private val view: View, private val context: Context) : Contract.Presenter {

    private val foods: Array<String> = context.resources.getStringArray(R.array.foods)
    private val formulas: Array<String> = context.resources.getStringArray(R.array.formulas)


    private val uiScope = Handler()

    /**
     * Picks a random number and selects the respective index of food array
     */
    override fun guess() {

        val rand = Random(System.currentTimeMillis())
        guess(rand.nextInt(3,5),rand)

    }

    /**
     * Counts the times formulas are displayed to the user before displaying the result
     */
    var count = 0

    /**
     * Recursive method which would display a formula @see{$formulaCount) times then displays the result and returns
     */
     private fun guess(formulaCount: Int,rand: Random){

         if (count > formulaCount) {
             count= 0
             uiScope.post {
                 view.guess(foods[rand.nextInt(0, foods.size)])
             }
             return
         }
         count++

         Timer().schedule(rand.nextLong(900, 1500)) {
             uiScope.post {
                 view.guess(formulas[rand.nextInt(0, formulas.size)])
             }
             guess(formulaCount,rand)
         }
    }

    override fun stop() {
    }


}