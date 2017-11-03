package org.FlightSimulation;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Kyle Zeller
 * This class provides the components of a plane to be created.
 */
public class Plane {
    //string of the plane type
    private String size;
    //amount representing the gas amount
    private double gasAmount;
    //constant for the decreasing rate
    private double decreaseRate;
    //constant for the decreasing rate of large planes
    private final double largeRate = 0.5;
    //constant for the decreasing rate of medium planes
    private final double mediumRate = 0.25;
    //constant for the decreasing rate of small planes
    private final double smallRate = 0.125;
    //constant for the max gas tank size
    private final int maxTankSize = 2000000;
    //constant for the max variance in gas
    private final int maxTankVar = 500;
    //amount representing the total flight time
    private int totalFlightTime;
    //amount representing the thread index
    private int flightNumber;

    /**
     * Creates a new plane with the plane type and thread number
     * @param choice is the plane type
     * @param threadNumber is the thread number
     */
    Plane(int choice, int threadNumber) {
        if (choice==0) {
            size = "small";
            decreaseRate = smallRate;
            totalFlightTime = ThreadLocalRandom.current().nextInt(maxTankSize);
        } else if (choice==1) {
            size = "medium";
            decreaseRate = mediumRate;
            totalFlightTime = ThreadLocalRandom.current().nextInt(maxTankSize);
        } else {
            size = "large";
            decreaseRate = largeRate;
            totalFlightTime = ThreadLocalRandom.current().nextInt(maxTankSize);
        }
        gasAmount = ThreadLocalRandom.current().nextDouble(maxTankVar) + (maxTankSize - maxTankVar);
        flightNumber = threadNumber;
    }

    /**
     * Calculates if there is no more gas
     * @return if there is no more gas
     */
    public boolean hasCrashed() {
        return gasAmount<=0;
    }

    /**
     * Calculates the size of the plane
     * @return the size of the plane
     */
    public String getSize() {
        return size;
    }

    /**
     * Calculates the total flight time
     * @return the total flight time
     */
    public int getTotalFlightTime() {
        return totalFlightTime;
    }

    /**
     * Calculates the gas remaining in the plane
     * @return the gas remaining in the plane
     */
    public double getGasAmount() {
        return gasAmount;
    }

    /**
     * Calculates the thread index number
     * @return the thread index number
     */
    public int getFlightNumber() {
        return flightNumber;
    }

    /**
     * Increases the flight time of the plane by 1
     */
    public void incTotalFlightTime() {
        this.totalFlightTime = ++totalFlightTime;
    }

    /**
     * Subtracts the decrease rate of the respective plane type from the gas in the plane
     */
    public void updateGasTank() {
        gasAmount -= decreaseRate;
    }
}
