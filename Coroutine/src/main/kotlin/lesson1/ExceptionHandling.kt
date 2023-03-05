package lesson1

import kotlinx.coroutines.*

/*
When an exception occurs in a coroutine, the current thread wont know about it
unless the current thread interacts with the erroring coroutine. For example, the
errow shows up when we do job.join(), defferedResult.await()
 */

fun main() {
    runBlocking() {
        val errorHandler = CoroutineExceptionHandler {
                coroutineContext, throwable ->
            println("Error handled by coroutine handler : ${throwable.message}")
        }
        val launchJob = CoroutineScope (Dispatchers.IO)
                .launch(errorHandler){
                println("This is new coroutine, error about to happen")
                delay(2000)
                throw Exception("Coroutine error")
            }
        launchJob.join()//if this is not called, then exception occurs , but the
                        //thread running this application does not know about it
                        //and hence, it does not show in the logs, we have join
                        //the thread in which the error occured with the current
                        //running thread to see the error.


        try{
            val defferedResult = CoroutineScope(Dispatchers.IO)
                .async {
                    println("async block about to throw error")
                    delay(2000)
                    throw Exception("Deffered error")
                }
            defferedResult.await()//we see the error when we interact
                                //with the erroring coroutine
        } catch (e : Exception) {
            println("Error handled with try catch : ${e.message}")
        }
    }
}
//output by above code
/*
    This is new coroutine, error about to happen
    Error handled by coroutine handler : Coroutine error
    async block about to throw error
    Error handled with try catch : Deffered error
 */