package lesson1

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
jobs follow standard concept of UNIX based job/process hierarchy
when the parent job is cancelled, then all the child jobs are also
cancelled.
 */

fun main(){
    runBlocking {
        var job2 : Job? = null
        val job1 = launch{
            println("job1 starting")
            job2 = launch{
                println("job2 starting")
                delay(3000)
                println("job2 completed")
            }
            val job3 = launch{
                println("job3 starting")
                delay(3000)
                println("job3 completed")
            }
            job2?.invokeOnCompletion {
                println("job2 is either cancelled or completed")
            }
            job3.invokeOnCompletion {
                println("job3 is either cancelled or completed")
            }
            delay(3000)
            println("job1 completed")
        }
        job1.invokeOnCompletion {
            println("job1 is either cancelled or completed")
        }

        delay(1000)
        println("cancelling job2")
        job2?.cancel()
    }
}
//output of the above code
/*
    job1 starting
    job2 starting
    job3 starting
    cancelling job2
    job2 is either cancelled or completed
    job1 completed
    job3 completed
    job3 is either cancelled or completed
    job1 is either cancelled or completed
 */