package lesson1

import kotlinx.coroutines.*

/*
every coroutine runs within a scope, there are extension function like async, launch,
which uses the current coroutine scope, which helps the framework in controlling the lifecycle
of the child processes and manage child process cancellation.
 */
fun main() {
    runBlocking {
        GlobalScope.launch {
            println("from global scope , thread name : ${Thread.currentThread().name}")
        }
        coroutineScope {
            println("from coroutine scope , thread name : ${Thread.currentThread().name}")
        }
        launch {
            println("from run blocking scope , thread name : ${Thread.currentThread().name}")
        }
    }
    println("main method is finishing , thread name : ${Thread.currentThread().name}")
}
//output of the above code
/*
    from global scope , thread name : DefaultDispatcher-worker-1
    from coroutine scope , thread name : main
    from run blocking scope , thread name : main
    main method is finishing , thread name : main
 */
