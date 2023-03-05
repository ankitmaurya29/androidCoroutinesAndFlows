package lesson1

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.sql.Time
import kotlin.random.Random

/*
Async returns a value in the for of "deffered" , this is a promise
to return the result after some indeterminate amount of time. It
return us a job object. We can use the job.await() to block the
current thread so that the results are available at some point of
time.
 */

fun main() {
    runBlocking {
        val startTime = System.currentTimeMillis()
        val first = async {
            fetchValueFromNetwork(3000L)
        }
        val second = async {
            fetchValueFromNetwork(6000L)
        }
        println("fetching values from different networks")
        val resultFirst = first.await()
        println("network one return us the result in" +
                " ${System.currentTimeMillis() - startTime} ms" +
                "value returned : $resultFirst")
        val resultSecond = second.await()
        println("network two return us the result in" +
                " ${System.currentTimeMillis() - startTime} ms" +
                "value returned : $resultSecond")
        println("Final added result of two network is : " +
                "${resultFirst + resultSecond}")
    }
}

suspend fun fetchValueFromNetwork(delay : Long): Int {
    delay(delay)
    return Random.nextInt(100)
}

//Result of above code
/*
    fetching values from different networks
    network one return us the result in 3059 msvalue returned : 48
    network two return us the result in 6023 msvalue returned : 73
    Final added result of two network is : 121
 */