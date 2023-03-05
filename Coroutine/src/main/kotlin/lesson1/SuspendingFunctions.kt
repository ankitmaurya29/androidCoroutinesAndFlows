package lesson1

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
Suspending functions are functions which can be only called from coroutines,
These functions can have delays, and waiting code, so by being suspending, the
framework can manage how the functions will be assigned the CPU time for execution.
Suspending functions have access to local variables. Multiple coroutines can update
the same local variable, without a need to extra code for synchronization.
 */
var count = 0
suspend fun main(){

    val firstJob = GlobalScope.launch {
        repeat(5){
            delay(5)
            count++; //count is local variable , but still accessible from the
                    //coroutine
        }
    }
    val secondJob = GlobalScope.launch {
        incrementValue() //suspend function , can be only invoked from coroutine
    }

    firstJob.join()
    secondJob.join()
    println("Final value of count is : $count")
}

suspend fun incrementValue(){
    repeat(5){
        delay(6)
        count++ //count variable is accessible
    }
}