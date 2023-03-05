package lesson1

import kotlinx.coroutines.*
/**
    creating your own scope and doing same as Helloworld
 */
suspend fun main(){
    val scope = CoroutineScope(Dispatchers.Default)
    val job = scope.launch {
        delay(1000)
        println("World!!")
        println("inside one : ${Thread.currentThread().name}")
        coroutineScope {
            println("inside two : ${Thread.currentThread().name}")
        }
    }
    println("inside three : ${Thread.currentThread().name}")
    println("hello, ")
    //below two method calls are suspending in nature, so we need to add suspend keyword to the main function
    //a suspending function can be only called within another suspending function or a coroutine scope.
    //suspending function can wait for indeterminate amount of time. so the coroutine gets pushed to the background
    // and later gets the cpu clocks, therefore suspending functions are different from sequential functions.
    job.join() //wait for the job to finish
    job.cancelAndJoin() /* cancel the job, otherwise the scope created will stay active, causing the main fun to never
    finish*/
}
//the above code runs in about 1 sec.
/*
hello,
World!!

Process finished with exit code 0
 */