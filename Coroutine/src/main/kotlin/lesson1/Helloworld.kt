package lesson1

import kotlinx.coroutines.*

fun main() {
    //GlobalScope , a coroutine scope, which has application level lifetime. It dies with the application.
    //It stays active till the application is active.
    // one disadvantage of using such a long scope is that the code stays in the memory long after the code is
    //required.
    GlobalScope.launch {
        //a suspending function , which sleeps for specified time, like Thread.sleep
        delay(1000)
        println("World!!")
    }
    println("Hello!")
    Thread.sleep(3000)
}
//This code takes about 3 sec to execute.
/*
output:

Hello!
World!!

Process finished with exit code 0

 */