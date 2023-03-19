package lesson1

import kotlinx.coroutines.*
/*
Handle the exception in the child method, because if the child cancels, and if the child was running in the same
scope /job then the parent also gets cancelled and all the child in that parent also get cancelled. Parent doens't even
get chance to handle the child process exception via the try/catch block.
 */
fun main(){
    runBlocking {
        //program will end if the error happens in the same job or same scope where the error
        CoroutineScope(Dispatchers.IO).launch{
            try{
                val def1 = CoroutineScope(Dispatchers.IO).async {
                    callFailingMethod(5)
                }
                def1.await()
            } catch (e: Exception) {
                println("Caught exception message : ${e.message}")// code reaches here and continues without cancelling
                //the current scope, becuase the exception happened in new created coroutine
            }
            try{
                val def1 = async(Dispatchers.IO + Job()) {
                    callFailingMethod(3)
                }
                def1.await()
            } catch (e: Exception) {
                println("Caught exception message : ${e.message}") // code reaches here and continues without cancelling
                //the current scope, becuase the exception happened in new created coroutine, and new job()
            }
            try{
                val def1 = async(Job()) {
                    callFailingMethod(4)
                }
                def1.await()
            } catch (e: Exception) {
                println("Caught exception message : ${e.message}")// code reaches here and continues without cancelling
                //the current scope, becuase the exception happened in another job and not in current job
            }
            try{
                val def1 = async(Dispatchers.IO) {
                    callFailingMethod(2)
                }
                def1.await()//the child has cancelled and which causes the parent to cancel, parent doesn't get chance
                            // to handle the exception via try catch block
            } catch (e: Exception) {
                println("Caught exception message : ${e.message}")// Changing the Dispatcher doesn't change the job, so
                //error happens and program ends because current thread also cancells.
            }
            try{
                val def1 = async {
                    callFailingMethod(1)
                }
                def1.await()
            } catch (e: Exception) {
                println("Caught exception message : ${e.message}")
            }
        }.join()
    }
    println("Execution Over")
}

fun callFailingMethod(i : Int) {
    //try{//uncommenting try catch will work for all cases.
    println("Failing method called : $i")
    throw Exception("Failed method $i")
    //} catch (e: Exception){
   //     println("Exception caught at method level: $i")
   // }
}
//Execution output
/*
    Failing method called : 5
    Caught exception message : Failed method 5
    Failing method called : 3
    Caught exception message : Failed method 3
    Failing method called : 4
    Caught exception message : Failed method 4
    Failing method called : 2
    Caught exception message : Failed method 2
    Caught exception message : Parent job is Cancelling
    Exception in thread "DefaultDispatcher-worker-7" java.lang.Exception: Failed method 2
        at lesson1.ExceptionHandling2Kt.callFailingMethod(ExceptionHandling2.kt:60)
        at lesson1.ExceptionHandling2Kt$main$1$1$def1$4.invokeSuspend(ExceptionHandling2.kt:38)
        at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
        at kotlinx.coroutines.DispatchedTask.run(DispatchedTask.kt:106)
        at kotlinx.coroutines.internal.LimitedDispatcher.run(LimitedDispatcher.kt:42)
        at kotlinx.coroutines.scheduling.TaskImpl.run(Tasks.kt:95)
        at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:570)
        at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.executeTask(CoroutineScheduler.kt:750)
        at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.runWorker(CoroutineScheduler.kt:677)
        at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:664)
        Suppressed: kotlinx.coroutines.DiagnosticCoroutineContextException: [StandaloneCoroutine{Cancelling}@44acf4ba, Dispatchers.IO]
    Execution Over
 */