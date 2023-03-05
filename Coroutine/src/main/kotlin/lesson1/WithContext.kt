package lesson1

import kotlinx.coroutines.*
/*
withcontext helps us change the thread we are working on, when
a coroutine uses withcontext and runs some block of code in
another thread, then the parent thread waits in a blocking way
for the withcontext's block to execute.
 */
fun main() {
    runBlocking {
        launch(Dispatchers.Default) {
            println("Task started in default thread," +
                    " context: ${coroutineContext}")
            withContext(Dispatchers.IO) {
                println("Task started in IO thead," +
                        " context: ${coroutineContext}")
                delay(1000)
                println("Task completion in IO thread" +
                        " context: ${coroutineContext}")
            }
            println("Task Ended in default thread" +
                    " context: ${coroutineContext}")
        }
    }
}
//output of the above code
/*
    Task started in default thread, context: [StandaloneCoroutine{Active}@6e557dc9, Dispatchers.Default]
    Task started in IO thead, context: [DispatchedCoroutine{Active}@462445e7, Dispatchers.IO]
    Task completion in IO thread context: [DispatchedCoroutine{Active}@462445e7, Dispatchers.IO]
    Task Ended in default thread context: [StandaloneCoroutine{Active}@6e557dc9, Dispatchers.Default]
 */