package org.FlightSimulation;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Kyle Zeller
 * This class serves as the thread safe data structure for me to store planes in for my mutlithreaded application.
 */
public class ConcurrentArrayList<K> {
    //a generic type arraylist
    private ArrayList<K> concurrentArrayList;
    //a read write lock for providing thread safety to the data structure
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    /**
     * Creates a thread safe arraylist
     */
    public ConcurrentArrayList() {
        concurrentArrayList = new ArrayList<>();
    }
    /**
     * Creates a thread safe arraylist of an initial size
     * @param size is the initial size of the arraylist
     */
    ConcurrentArrayList(int size) {
        concurrentArrayList = new ArrayList<>(size);
    }

    /**
     * Adds a generic object to the arraylist
     * @param object is the generic object your adding to the arraylist
     */
    public void add(K object) {
        writeLock.lock();
        try {
            concurrentArrayList.add(object);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Adds a generic object to the arraylist at an index
     * @param index is the index to add your object at on the arraylist
     * @param object is the generic object your adding to the arraylist
     */
    public void set(int index, K object) {
        writeLock.lock();
        try {
            concurrentArrayList.set(index, object);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Remove your object at on the arraylist at an index
     * @param index is the index to remove your object at on the arraylist
     */
    public void remove(int index) {
        writeLock.lock();
        try {
            concurrentArrayList.remove(index);
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Returns the object at the index
     * * @param index is the index of your object at on the arraylist
     * @return the object at the index
     */
    public K get(int index) {
        readLock.lock();
        try {
            return concurrentArrayList.get(index);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * Calculates the size of the arraylist
     * @return the size of the arraylist
     */
    public int size() {
        readLock.lock();
        try {
            return concurrentArrayList.size();
        } finally {
            readLock.unlock();
        }
    }
}
