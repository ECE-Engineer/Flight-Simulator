# Flight-Simulator
This project simulates a control tower trying to land planes in a real-time setting. Planes run in parallel and are admitted to the runway one at a time based on their priority (remaining gas &amp; total flight time).
Additionally this project utilizes JMH to run benchmarking on the flight simulator's use of the JDK's copyonwritearraylist, and again for the flight simulator's use of the thread-safe concurrent arraylist that I made.

##Samples From Project

###Benchmark Run On The Server Wolf Using 32 Threads

####Throughput

![Visual](./Visuals/wolf/W32T.jpeg)
####Average Time

![Visual](./Visuals/wolf/W32A.jpeg)
####Single Shot

![Visual](./Visuals/wolf/W32S.jpeg)

###Benchmark Run On The Server Wolf Using 16 Threads

####Throughput

![Visual](./Visuals/wolf/W16T.jpeg)
####Average Time

![Visual](./Visuals/wolf/W16A.jpeg)
####Single Shot

![Visual](./Visuals/wolf/W16S.jpeg)

###Benchmark Run On The Server Rho Using 32 Threads

####Throughput

![Visual](./Visuals/rho/R32T.jpeg)
####Average Time

![Visual](./Visuals/rho/R32A.jpeg)
####Single Shot

![Visual](./Visuals/rho/R32S.jpeg)

###Benchmark Run On The Server Rho Using 16 Threads

####Throughput

![Visual](./Visuals/rho/R16T.jpeg)
####Average Time

![Visual](./Visuals/rho/R16A.jpeg)
####Single Shot

![Visual](./Visuals/rho/R16S.jpeg)

## My Website
[My Website]
(http://cs.oswego.edu/~kzeller)