package lesson1

import kotlinx.coroutines.*

/*
Just like every coroutine has a scope , which is used to manage the coroutine.
Coroutine also have some data associated with each one of them.
Most common ones are :
1. Dispatcher : which thread the coroutine will run on
    a. Dispatcher.IO
    b. Dispatcher.MAIN
    c. Dispatcher.default
    d. etc, etc
2. Job -> A handle on the coroutine, which will allow us to wait, terminate the job
 */

fun main(){
    runBlocking {
        launch(CoroutineName("myCoroutine")){
            println("this is run rom ${coroutineContext[CoroutineName]}")
            println("launch context : ${this.coroutineContext.job}")
        }
        //switching the context of the coroutine, using this we can switch the thread
        // to be launch at
        withContext(Dispatchers.IO){
            println("with context : ${this.coroutineContext.job}")
        }
        val job = Job()
        CoroutineScope(Dispatchers.IO + job).launch {
            println("specifying the thread and the job to launch the coroutine")
        }
    }
}