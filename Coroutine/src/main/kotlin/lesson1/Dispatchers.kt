package lesson1

import kotlinx.coroutines.*

/*
Dispatchers defines the thread that the coroutine works on. We have the following
dispatchers:
  > Dispatchers.Default : For Cpu intensive tasks
  > Dispatchers.IO : For IO intensive tasks
  > Dispatchers.MAIN : For UI related update (specific to android)
  > Dispatchers.Unconfined : It starts of with the dispatchers inherited from the
              coroutine scope. and can switch the thread if the coroutine paused
              in between.
  > newSingleThreadContext("name") : Creates a new coroutine dispatcher on a new
              thread
 */

@OptIn(DelicateCoroutinesApi::class)
fun main() {
    runBlocking {
        launch(Dispatchers.IO) {
            println("IO thread : ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default){
            println("default thread : ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) {
            println("Unconfined started on thread : ${Thread.currentThread().name}")
            delay(1000)
            println("Unconfined switched to thread : ${Thread.currentThread().name}")
        }
        launch(newSingleThreadContext("Customthread")){
            println("Custom thread is running on : ${Thread.currentThread().name}")
        }
    }
}
//output of the above code
/*
    IO thread : DefaultDispatcher-worker-2
    default thread : DefaultDispatcher-worker-2
    Unconfined started on thread : main
    Custom thread is running on : Customthread
    Unconfined switched to thread : kotlinx.coroutines.DefaultExecutor
 */