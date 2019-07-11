package com.techspark.whateats
import com.techspark.whateats.Contract.View
import android.content.Context
import android.widget.Toast
import kotlin.random.Random

class GuessPresenter (private val view: View, private val context: Context) : Contract.Presenter{

    private val foods: Array<String> = context.resources.getStringArray(R.array.foods)

    /**
     * Picks a random number and selects the respective index of food array
     */
    override fun guess() {

        val rand = Random(System.currentTimeMillis()).nextInt(0,foods.size)
        view.guess(foods[rand])
    }
}