package org.FlightSimulation;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

/**
 * @author Kyle Zeller
 * This class serves as the control tower that admits incoming planes to land. N threads each creating an instance of
 * one plane are created. Then a controller thread is created which prioritizes the planes to land based on remaining
 * gas and total flight time.
 */
public class ControlTower {
    //number of threads to be run
    private final int coreNum;

    /**
     * Creates a control tower to land planes
     * @param coreNum is the number of threads to run
     */
    public ControlTower(int coreNum) {
        this.coreNum = coreNum;
    }

    /**
     * Runs the flight simulation on either the java or custom implementation of a data structure
     * @param choice picks whether the simulation will run either the java or custom implementation of a data structure
     * @throws InterruptedException
     */
    public void run(boolean choice) throws InterruptedException {
        if (choice) {//run the custom data structure implementation of the simulation
            openPrivateRunway();
        } else {//run the java collection data structure implementation of the simulation
            openRunway();
        }
    }

    /**
     * Runs all N-specified threads to fly planes and the control thread to admit the planes on the runway.
     */
    public void openPrivateRunway() {
        if (coreNum >= 4 && coreNum < 89 && coreNum%2==0) {
            final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
            final Lock readLock = readWriteLock.readLock();
            final Lock writeLock = readWriteLock.writeLock();
            Thread controlTower;
            Thread[] threads = new Thread[coreNum];
            final ConcurrentArrayList<Plane> customPlanes = new ConcurrentArrayList<>();

            writeLock.lock();
            try {
                //change the size of the planes to coreNum
                if (customPlanes.size() != coreNum) {
                    while (customPlanes.size() > coreNum) {
                        customPlanes.remove(customPlanes.size()-1);
                    }
                    while (customPlanes.size() < coreNum) {
                        customPlanes.add(null);
                    }
                }
            } finally {
                writeLock.unlock();
            }

            for (int i = 0; i < coreNum; ++i) {
                //store a pointer to the thread number
                int threadNumber = i;
                threads[threadNumber] = new Thread(() -> {

                    //create a plane and pick the type of plane
                    Plane plane = new Plane(ThreadLocalRandom.current().nextInt(3), threadNumber);
                    writeLock.lock();
                    try {
                        //set the plane to the arraylist at the thread index
                        customPlanes.set(threadNumber, plane);
                    } finally {
                        writeLock.unlock();
                    }

                    outerloop:
                    while(true) try {
                        //check if the runway has admitted the plane to land at the thread index
                        boolean isLanded = true;

                        readLock.lock();
                        try {
                            if (customPlanes.get(threadNumber) != null) {
                                isLanded = false;
                            }
                        } finally {
                            readLock.unlock();
                        }
                        if (isLanded) {
                            break outerloop;
                        }



                        //check if the plane has crashed
                        if (plane.hasCrashed()) {
                            writeLock.lock();
                            try {
                                System.out.println(plane.getSize() + " plane " + plane.getFlightNumber() + " has Crashed!!!");
                                //remove the element on the list at the thread index
                                if (customPlanes.get(threadNumber) != null) {
                                    customPlanes.set(threadNumber, null);
                                }
                            } finally {
                                writeLock.unlock();
                            }
                            break outerloop;
                        }




                        //wait 10 miliseconds
                        sleep(10);
                        //increment the count
                        plane.incTotalFlightTime();
                        //update the gas tank
                        plane.updateGasTank();



                        writeLock.lock();
                        try {
                            //edit the plane on the arraylist at the thread index
                            if (customPlanes.get(threadNumber) != null) {
                                customPlanes.set(threadNumber, plane);
                            }
                        } finally {
                            writeLock.unlock();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            controlTower = new Thread(() -> {
                //make sure all the planes are in the air
                boolean isNullFlag = true;
                while(isNullFlag) {
                    readLock.lock();
                    try {
                        isNullFlag = false;
                        for (int i = 0; i < customPlanes.size(); ++i) {
                            if (customPlanes.get(i) == null) {
                                isNullFlag = true;
                            }
                            break;
                        }
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        readLock.unlock();
                    }
                }

                while(true) try {
                    boolean isPlaneFlag = false;
                    //keep a counter of how many planes are waiting
                    int counter = 0;
                    //keep track of the index of the first waiting plane
                    int firstPlaneIndex = 0;



                    readLock.lock();
                    try {
                        //check to see if there are no more incoming planes
                        for (int i = 0; i < customPlanes.size(); ++i) {
                            if (customPlanes.get(i) != null) {
                                if (counter == 0) {
                                    firstPlaneIndex = i;
                                }
                                isPlaneFlag = true;
                                ++counter;
                            }
                        }
                    } finally {
                        readLock.unlock();
                    }
                    if (!isPlaneFlag) {
                        break;
                    }




                    //if there is only one plane left waiting simply remove it & finish
                    if (counter == 1) {
                        writeLock.lock();
                        try {
                            //remove the thread from the list
                            customPlanes.set(firstPlaneIndex, null);
                        } finally {
                            writeLock.unlock();
                        }
                        break;
                    }




                    readLock.lock();
                    try {
                        int previousIndex = 0;
                        //find the first non-null plane
                        for (int i = 0; i < customPlanes.size(); ++i) {
                            if (customPlanes.get(i) != null) {
                                previousIndex = i;
                                firstPlaneIndex = i;
                            }
                        }

                        //find the plane that needs to land first
                        for (int i = 1; i < customPlanes.size(); ++i) {
                            if (customPlanes.get(i) != null) {
                                if (customPlanes.get(i).getGasAmount() < customPlanes.get(previousIndex).getGasAmount()) {
                                    previousIndex = firstPlaneIndex;
                                    firstPlaneIndex = customPlanes.get(i).getFlightNumber();
                                } else if (customPlanes.get(i).getGasAmount() == customPlanes.get(previousIndex).getGasAmount()) {
                                    if (customPlanes.get(i).getTotalFlightTime() > customPlanes.get(previousIndex).getTotalFlightTime()) {
                                        previousIndex = firstPlaneIndex;
                                        firstPlaneIndex = customPlanes.get(i).getFlightNumber();
                                    }
                                }
                            }
                        }
                    } finally {
                        readLock.unlock();
                    }



                    writeLock.lock();
                    try {
                        //remove the thread from the list
                        customPlanes.set(firstPlaneIndex, null);
                    } finally {
                        writeLock.unlock();
                    }
                    //wait 10 milliseconds
                    sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            //start all the threads
            for (Thread thread : threads) {
                thread.start();
            }

            //open the runway
            controlTower.start();
        }
    }





































    /**
     * Runs all N-specified threads to fly planes and the control thread to admit the planes on the runway.
     * @throws InterruptedException
     */
    public void openRunway() throws InterruptedException {
        if (coreNum >= 4 && coreNum < 89 && coreNum%2==0) {
            final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
            final Lock readLock = readWriteLock.readLock();
            final Lock writeLock = readWriteLock.writeLock();
            Thread controlTower;
            Thread[] threads = new Thread[coreNum];
            final CopyOnWriteArrayList<Plane> planes = new CopyOnWriteArrayList<>();

            writeLock.lock();
            try {
                //change the size of the planes to coreNum
                if (planes.size() != coreNum) {
                    while (planes.size() > coreNum) {
                        planes.remove(planes.size()-1);
                    }
                    while (planes.size() < coreNum) {
                        planes.add(null);
                    }
                }
            } finally {
                writeLock.unlock();
            }

            for (int i = 0; i < coreNum; ++i) {
                //store a pointer to the thread number
                int threadNumber = i;
                threads[threadNumber] = new Thread(() -> {

                    //create a plane and pick the type of plane
                    Plane plane = new Plane(ThreadLocalRandom.current().nextInt(3), threadNumber);
                    writeLock.lock();
                    try {
                        //set the plane to the arraylist at the thread index
                        planes.set(threadNumber, plane);
                    } finally {
                        writeLock.unlock();
                    }

                    outerloop:
                    while(true) try {
                        //check if the runway has admitted the plane to land at the thread index
                        boolean isLanded = true;

                        readLock.lock();
                        try {
                            if (planes.get(threadNumber) != null) {
                                isLanded = false;
                            }
                        } finally {
                            readLock.unlock();
                        }
                        if (isLanded) {
                            break outerloop;
                        }



                            //check if the plane has crashed
                            if (plane.hasCrashed()) {
                                writeLock.lock();
                                try {
                                    System.out.println(plane.getSize() + " plane " + plane.getFlightNumber() + " has Crashed!!!");
                                    //remove the element on the list at the thread index
                                    if (planes.get(threadNumber) != null) {
                                        planes.set(threadNumber, null);
                                    }
                                } finally {
                                    writeLock.unlock();
                                }
                                break outerloop;
                            }




                        //wait 10 miliseconds
                        sleep(10);
                        //increment the count
                        plane.incTotalFlightTime();
                        //update the gas tank
                        plane.updateGasTank();



                        writeLock.lock();
                        try {
                            //edit the plane on the arraylist at the thread index
                            if (planes.get(threadNumber) != null) {
                                planes.set(threadNumber, plane);
                            }
                        } finally {
                            writeLock.unlock();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            controlTower = new Thread(() -> {
                //make sure all the planes are in the air
                boolean isNullFlag = true;
                while(isNullFlag) {
                    readLock.lock();
                    try {
                        isNullFlag = false;
                        for (int i = 0; i < planes.size(); ++i) {
                            if (planes.get(i) == null) {
                                isNullFlag = true;
                            }
                            break;
                        }
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        readLock.unlock();
                    }
                }

                while(true) try {
                    boolean isPlaneFlag = false;
                    //keep a counter of how many planes are waiting
                    int counter = 0;
                    //keep track of the index of the first waiting plane
                    int firstPlaneIndex = 0;



                    readLock.lock();
                    try {
                        //check to see if there are no more incoming planes
                        for (int i = 0; i < planes.size(); ++i) {
                            if (planes.get(i) != null) {
                                if (counter == 0) {
                                    firstPlaneIndex = i;
                                }
                                isPlaneFlag = true;
                                ++counter;
                            }
                        }
                    } finally {
                        readLock.unlock();
                    }
                    if (!isPlaneFlag) {
                        break;
                    }




                        //if there is only one plane left waiting simply remove it & finish
                        if (counter == 1) {
                            writeLock.lock();
                            try {
                                //remove the thread from the list
                                planes.set(firstPlaneIndex, null);
                            } finally {
                                writeLock.unlock();
                            }
                            break;
                        }




                    readLock.lock();
                    try {
                        int previousIndex = 0;
                        //find the first non-null plane
                        for (int i = 0; i < planes.size(); ++i) {
                            if (planes.get(i) != null) {
                                previousIndex = i;
                                firstPlaneIndex = i;
                            }
                        }

                        //find the plane that needs to land first
                        for (int i = 1; i < planes.size(); ++i) {
                            if (planes.get(i) != null) {
                                if (planes.get(i).getGasAmount() < planes.get(previousIndex).getGasAmount()) {
                                    previousIndex = firstPlaneIndex;
                                    firstPlaneIndex = planes.get(i).getFlightNumber();
                                } else if (planes.get(i).getGasAmount() == planes.get(previousIndex).getGasAmount()) {
                                    if (planes.get(i).getTotalFlightTime() > planes.get(previousIndex).getTotalFlightTime()) {
                                        previousIndex = firstPlaneIndex;
                                        firstPlaneIndex = planes.get(i).getFlightNumber();
                                    }
                                }
                            }
                        }
                    } finally {
                        readLock.unlock();
                    }



                    writeLock.lock();
                    try {
                        //remove the thread from the list
                        planes.set(firstPlaneIndex, null);
                    } finally {
                        writeLock.unlock();
                    }
                    //wait 10 milliseconds
                    sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            //start all the threads
            for (Thread thread : threads) {
                thread.start();
            }

            //open the runway
            controlTower.start();
        }
    }
}
