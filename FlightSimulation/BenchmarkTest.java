package org.FlightSimulation;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class BenchmarkTest {
    private static final int THREAD_COUNT_LOAD_1 = 32;
    private static final int THREAD_COUNT_LOAD_2 = 16;

    @State(Scope.Thread)
    public static class MyState {
        ControlTower controlTower1;
        ControlTower controlTower2;

        @Setup(Level.Iteration)
        public void init() {
            controlTower1 = new ControlTower(THREAD_COUNT_LOAD_1);
            controlTower2 = new ControlTower(THREAD_COUNT_LOAD_2);
        }
    }

    /**
     * Calculate the throughput of the simulation on LOAD_1 using the JDK data structure
     * @param state
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.Throughput)
    public void runVersion1Throughput(MyState state) throws InterruptedException {
        state.controlTower1.run(false);
    }

    /**
     * Calculate the average time of the simulation on LOAD_1 using the JDK data structure
     * @param state
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.AverageTime) 
    public void runVersion1AverageTime(MyState state) throws InterruptedException {
        state.controlTower1.run(false);
    }

    /**
     * Calculate the single shot time of the simulation on LOAD_1 using the JDK data structure
     * @param state
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.SingleShotTime) 
    public void runVersion1SingleShotTime(MyState state) throws InterruptedException {
        state.controlTower1.run(false);
    }

    /**
     * Calculate the throughput of the simulation on LOAD_1 using the CUSTOM data structure
     * @param state
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.Throughput) 
    public void runVersion2Throughput(MyState state) throws InterruptedException {
        state.controlTower1.run(true);
    }

    /**
     * Calculate the average time of the simulation on LOAD_1 using the CUSTOM data structure
     * @param state
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.AverageTime) 
    public void runVersion2AverageTime(MyState state) throws InterruptedException {
        state.controlTower1.run(true);
    }

    /**
     * Calculate the average time of the simulation on LOAD_1 using the CUSTOM data structure
     * @param state
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.SingleShotTime) 
    public void runVersion2SingleShotTime(MyState state) throws InterruptedException {
        state.controlTower1.run(true);
    }

    /**
     * Calculate the throughput of the simulation on LOAD_2 using the JDK data structure
     * @param state
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.Throughput) 
    public void runVersion3Throughput(MyState state) throws InterruptedException {
        state.controlTower2.run(false);
    }

    /**
     * Calculate the average time of the simulation on LOAD_2 using the JDK data structure
     * @param state
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.AverageTime) 
    public void runVersion3AverageTime(MyState state) throws InterruptedException {
        state.controlTower2.run(false);
    }

    /**
     * Calculate the single shot time of the simulation on LOAD_2 using the JDK data structure
     * @param state
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.SingleShotTime) 
    public void runVersion3SingleShotTime(MyState state) throws InterruptedException {
        state.controlTower2.run(false);
    }

    /**
     * Calculate the throughput of the simulation on LOAD_2 using the CUSTOM data structure
     * @param state
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.Throughput) 
    public void runVersion4Throughput(MyState state) throws InterruptedException {
        state.controlTower2.run(true);
    }

    /**
     * Calculate the average time of the simulation on LOAD_2 using the CUSTOM data structure
     * @param state
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.AverageTime) 
    public void runVersion4AverageTime(MyState state) throws InterruptedException {
        state.controlTower2.run(true);
    }

    /**
     * Calculate the average time of the simulation on LOAD_2 using the CUSTOM data structure
     * @param state
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.SingleShotTime) 
    public void runVersion4SingleShotTime(MyState state) throws InterruptedException {
        state.controlTower2.run(true);
    }





    /**
     * Calculate the average time of the add method using the JDK data structure
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.AverageTime) 
    public void runJDKStructureAdd() throws InterruptedException {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add(2);
        copyOnWriteArrayList.add(4);
        copyOnWriteArrayList.add(8);
        copyOnWriteArrayList.add(16);
        copyOnWriteArrayList.add(32);
        copyOnWriteArrayList.add(64);
    }

    /**
     * Calculate the average time of the set method using the JDK data structure
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.AverageTime) 
    public void runJDKStructureSet() throws InterruptedException {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add(2);
        copyOnWriteArrayList.add(4);
        copyOnWriteArrayList.add(8);
        copyOnWriteArrayList.set(1, 16);
        copyOnWriteArrayList.set(0,32);
        copyOnWriteArrayList.set(4, 64);
    }

    /**
     * Calculate the average time of the remove method using the JDK data structure
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.AverageTime) 
    public void runJDKStructureRemove() throws InterruptedException {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add(2);
        copyOnWriteArrayList.add(4);
        copyOnWriteArrayList.add(8);
        copyOnWriteArrayList.remove(2);
        copyOnWriteArrayList.remove(1);
        copyOnWriteArrayList.remove(0);
    }

    /**
     * Calculate the average time of the get method using the JDK data structure
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.AverageTime) 
    public void runJDKStructureGet() throws InterruptedException {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add(2);
        copyOnWriteArrayList.add(4);
        copyOnWriteArrayList.add(8);
        copyOnWriteArrayList.get(2);
        copyOnWriteArrayList.get(1);
        copyOnWriteArrayList.get(0);
    }

    /**
     * Calculate the average time of the size method using the JDK data structure
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.AverageTime) 
    public void runJDKStructureSize() throws InterruptedException {
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        copyOnWriteArrayList.add(2);
        copyOnWriteArrayList.size();
        copyOnWriteArrayList.add(4);
        copyOnWriteArrayList.size();
        copyOnWriteArrayList.add(8);
        copyOnWriteArrayList.size();
    }







    /**
     * Calculate the average time of the add method using the CUSTOM data structure
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.AverageTime) 
    public void runCUSTOMStructureAdd() throws InterruptedException {
        ConcurrentArrayList<Integer> concurrentArrayList = new ConcurrentArrayList<>();
        concurrentArrayList.add(2);
        concurrentArrayList.add(4);
        concurrentArrayList.add(8);
        concurrentArrayList.add(16);
        concurrentArrayList.add(32);
        concurrentArrayList.add(64);
    }

    /**
     * Calculate the average time of the set method using the CUSTOM data structure
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.AverageTime) 
    public void runCUSTOMStructureSet() throws InterruptedException {
        ConcurrentArrayList<Integer> concurrentArrayList = new ConcurrentArrayList<>();
        concurrentArrayList.add(2);
        concurrentArrayList.add(4);
        concurrentArrayList.add(8);
        concurrentArrayList.set(1, 16);
        concurrentArrayList.set(0,32);
        concurrentArrayList.set(4, 64);
    }

    /**
     * Calculate the average time of the remove method using the CUSTOM data structure
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.AverageTime) 
    public void runCUSTOMStructureRemove() throws InterruptedException {
        ConcurrentArrayList<Integer> concurrentArrayList = new ConcurrentArrayList<>();
        concurrentArrayList.add(2);
        concurrentArrayList.add(4);
        concurrentArrayList.add(8);
        concurrentArrayList.remove(2);
        concurrentArrayList.remove(1);
        concurrentArrayList.remove(0);
    }

    /**
     * Calculate the average time of the get method using the CUSTOM data structure
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.AverageTime) 
    public void runCUSTOMStructureGet() throws InterruptedException {
        ConcurrentArrayList<Integer> concurrentArrayList = new ConcurrentArrayList<>();
        concurrentArrayList.add(2);
        concurrentArrayList.add(4);
        concurrentArrayList.add(8);
        concurrentArrayList.get(2);
        concurrentArrayList.get(1);
        concurrentArrayList.get(0);
    }

    /**
     * Calculate the average time of the size method using the CUSTOM data structure
     * @throws InterruptedException
     */
    @Benchmark @BenchmarkMode(Mode.AverageTime) 
    public void runCUSTOMStructureSize() throws InterruptedException {
        ConcurrentArrayList<Integer> concurrentArrayList = new ConcurrentArrayList<>();
        concurrentArrayList.add(2);
        concurrentArrayList.size();
        concurrentArrayList.add(4);
        concurrentArrayList.size();
        concurrentArrayList.add(8);
        concurrentArrayList.size();
    }






    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .forks(10)
                .warmupIterations(20)
                .measurementIterations(20)
                .resultFormat(ResultFormatType.CSV)
                .result("output.csv")
                .build();
        new Runner(opt).run();
    }
}
