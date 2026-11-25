package com.axiangyh;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(value = 1)
public class VectorizationBenchmark {

    @Param({"1000000"})
    private int size;

    private float[] a;
    private float[] b;
    private float[] result;

    @Setup(Level.Trial)
    public void setup() {
        a = new float[size];
        b = new float[size];
        result = new float[size];

        Random rand = new Random(42);
        for (int i = 0; i < size; i++) {
            a[i] = rand.nextFloat();
            b[i] = rand.nextFloat();
        }
    }

    @Benchmark
    public float[] impl1() {
        for (int i = 0; i < a.length; i++) {
            result[i] = (a[i] > 0.5f) ? (a[i] * b[i]) : (a[i] + b[i]);
        }
        return result;
    }

    @Benchmark
    public float[] impl2() {
        for (int i = 0; i < a.length; i++) {
            if (a[i] > 0.5f) {
                result[i] = a[i] * b[i];
            } else {
                result[i] = a[i] + b[i];
            }
        }
        return result;
    }

    @Benchmark
    public float[] impl3() {
        for (int i = 0; i < a.length; i++) {
            float f;
            if (a[i] > 0.5f) {
                f = a[i] * b[i];
            } else {
                f = a[i] + b[i];
            }
            result[i] = f;
        }
        return result;
    }
}