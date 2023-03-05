package lesson1

import kotlinx.coroutines.*

//Unstructured concurrency, the getCount() starts the counting logic in another thread,
//but fun getCount() returns before the coroutine can get finished.
suspend fun main1(){
    val childJob = CoroutineScope(Dispatchers.IO).launch {
        println("Count value ${getCount()}") //prints 0
    }
    println("main job")
    childJob.join()
    childJob.cancelAndJoin()
}

//this returns a count value of 20, because the getCountUsingStructuredConcurrency() method uses coroutinescope,
//which is just one more stupid complexity added by the kotlin guys.
suspend fun main(){
    val childJob = CoroutineScope(Dispatchers.IO).launch {
        println("Count value ${getCountUsingStructuredConcurrency()}")
    }
    println("main job ends")
    childJob.join()
    childJob.cancelAndJoin()
}

suspend fun getCountUsingStructuredConcurrency(): Int {
    var count = 0
    coroutineScope {
        repeat(20){
            delay(5)
            count++
        }
    }
    return count
}
suspend fun getCount(): Int {
    var count = 0
    CoroutineScope(Dispatchers.IO).launch {
        repeat(50){
            delay(50)
            count++
        }
    }
    return count
}