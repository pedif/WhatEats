package com.techspark.whateats

class Contract {

    interface Presenter{
        fun guess()
        fun stop()
    }

    interface View{
        fun guess(msg: String)
        fun onStartGuessing()
        fun onStopGuessing()
    }
}