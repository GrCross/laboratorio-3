# laboratorio-3

## Part I - Introduction to threads in JAVA

1.  In agreement with the lectures, complete the classes `CountThread`, so that they define the life cycle of a thread that prints the numbers between A and B on the screen.
    
2.  Complete the `main` method of the `CountMainThreads` class so that:
    
    1.  Create 3 threads of type `CountThread`, assigning the first interval [0..99], the second [99..199], and the third [200..299].
        
    2.  Start the three threads with `start()`. Run and check the output on the screen.
        
    3.  Change the beginning with `start()` to `run()`. How does the output change? Why?
	    >**R://** If the program call the method `start()` a new Thread is created and the code inside the method `run()` is executed while if the program only call the method `run()` no new thread is created and only execute the code in the method
	    
## Part III - Discussion

The strategy of parallelism previously implemented is inefficient in certain cases, since the search is still carried out even when the `N` threads (as a whole) have already found the minimum number of occurrences required to report to the server as malicious. How could the implementation be modified to minimize the number of queries in these cases? What new element would this bring to the problem?

>**R://** The problem lies in the fact that is not necessary to explore all the others parts of the blackLists if one of the threads has already found 5 occurrences

## Part IV - Performance Evaluation
From the above, implement the following sequence of experiments to perform the validation of dispersed IP addresses (for example 202.24.34.55), taking the execution times of them (be sure to do them on the same machine):

1.  A single thread.
    ![](img/1threat.PNG)
2.  As many threads as processing cores (have the program determine this using the Runtime API
    ![](img/2Threat.PNG)
3.  As many threads as twice the number of processing cores.
    ![](img/3Threat.PNG)
4.  50 threads
    ![](img/4Threat.PNG)
5.  100 threads
	![](img/5Threat.PNG)

-   According to Amdahls law, where `S(n)` is the theoretical improvement of performance, **P** the parallel fraction of the algorithm, and **n** the number of threads, the greater **n**, the better this improvement should be. Why is the best performance not achieved with the 500 threads? How is this performance compared when using 200 ?.
**R://** using the Amdahls law if we compare the S(200) and S(500) the diference is really smalls, but if we compare for example S(20) and S(100) the diference is the difference is more significant.
    

![](https://blobscdn.gitbook.com/v0/b/gitbook-28427.appspot.com/o/assets%2F-LWJN2LirJZqzEmpZ3Gn%2F-LXX-N0xe_iYMHKeMI_F%2F-LXX0JMxJUi0CH7YwNxg%2Fahmdahls.png?alt=media&token=341a4fdd-bb18-4d57-8a63-7d6456c56267)

-   How does the solution behave using as many processing threads as cores compared to the result of using twice as much?
**R://** using twice cores is better than only use the number of cores in the computer, because the threadas could use overlapping 
    
-   According to the above, if for this problem instead of 100 threads in a single CPU could be used 1 thread in each of 100 hypothetical machines, Amdahls law would apply better ?. If **x** threads are used instead of **100/x** distributed machines (where **x** is the number of cores of these machines), would it be improved? Explain your answer.

**R://**  Yes, it will be improved because the time expending doing overlapping doesnt exist, there is not necesary to wait for the peripheral