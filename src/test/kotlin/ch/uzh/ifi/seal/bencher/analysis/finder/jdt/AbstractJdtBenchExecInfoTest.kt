package ch.uzh.ifi.seal.bencher.analysis.finder.jdt

import ch.uzh.ifi.seal.bencher.Benchmark
import ch.uzh.ifi.seal.bencher.Class
import ch.uzh.ifi.seal.bencher.analysis.finder.AbstractBenchExecInfoTest
import ch.uzh.ifi.seal.bencher.execution.ConfigurationTestHelper
import ch.uzh.ifi.seal.bencher.execution.ExecutionConfiguration
import org.funktionale.option.Option
import java.util.concurrent.TimeUnit

abstract class AbstractJdtBenchExecInfoTest : AbstractBenchExecInfoTest() {

    protected fun assertClassConfigs(cs: Map<Class, ExecutionConfiguration>) {
        assertClassConfig(cs, expClassNestedBenchmark.first, expClassNestedBenchmark.second)
        assertClassConfig(cs, expClassBench1.first, expClassBench1.second)
        assertClassConfig(cs, expClassBench3.first, expClassBench3.second)
        assertClassConfig(cs, expClassBench32.first, expClassBench32.second)
        assertClassConfig(cs, expClassBenchGroup.first, expClassBenchGroup.second)
    }

    protected fun assertBenchConfigs(cs: Map<Benchmark, ExecutionConfiguration>) {
        assertBenchConfig(cs, expBench11.first, expBench11.second)
        assertBenchConfig(cs, expBench12.first, expBench12.second)
        assertBenchConfig(cs, expBench2.first, expBench2.second)
        assertBenchConfig(cs, expBench31.first, expBench31.second)
        assertBenchConfig(cs, expBench321.first, expBench321.second)
        assertBenchConfig(cs, expBenchGroup1.first, expBenchGroup1.second)
        assertBenchConfig(cs, expBenchGroup2.first, expBenchGroup2.second)
    }

    companion object {

        // class configurations
        @JvmStatic
        protected val expClassNestedBenchmark = Pair(
                Class(name = SourceCodeTestHelper.NestedBenchmark.fqn),
                ExecutionConfiguration(
                        forks = 2,
                        warmupForks = 2,
                        measurementIterations = 10,
                        measurementTime = 2000,
                        measurementTimeUnit = Option.Some(TimeUnit.MILLISECONDS),
                        warmupIterations = 10,
                        warmupTime = 2000,
                        warmupTimeUnit = Option.Some(TimeUnit.MILLISECONDS),
                        mode = listOf("AverageTime"),
                        outputTimeUnit = Option.Some(TimeUnit.MILLISECONDS)
                )
        )

        @JvmStatic
        protected val expClassBench1 = Pair(
                Class(name = SourceCodeTestHelper.NestedBenchmark.Bench1.fqn),
                ConfigurationTestHelper.unsetConfig.copy(
                        forks = 10,
                        mode = listOf("SampleTime"),
                        outputTimeUnit = Option.Some(TimeUnit.NANOSECONDS)
                )
        )

        @JvmStatic
        protected val expClassBench3 = Pair(
                Class(name = SourceCodeTestHelper.NestedBenchmark.Bench3.fqn),
                ConfigurationTestHelper.unsetConfig.copy(
                        warmupIterations = 2,
                        warmupTime = 100,
                        warmupTimeUnit = Option.Some(TimeUnit.MILLISECONDS)
                )
        )

        @JvmStatic
        protected val expClassBench32 = Pair(
                Class(name = SourceCodeTestHelper.NestedBenchmark.Bench3.Bench32.fqn),
                ConfigurationTestHelper.unsetConfig.copy()
        )

        @JvmStatic
        protected val expClassBenchGroup = Pair(
                Class(name = SourceCodeTestHelper.BenchsWithGroup.fqn),
                ConfigurationTestHelper.unsetConfig.copy()
        )

        // benchmark configurations
        @JvmStatic
        protected val expBench2 = Pair(
                SourceCodeTestHelper.NestedBenchmark.bench2,
                ConfigurationTestHelper.unsetConfig.copy(
                        forks = 5,
                        measurementIterations = 50,
                        measurementTime = 500,
                        measurementTimeUnit = Option.Some(TimeUnit.MILLISECONDS),
                        mode = listOf("SampleTime")
                )
        )

        @JvmStatic
        protected val expBench11 = Pair(
                SourceCodeTestHelper.NestedBenchmark.Bench1.bench11,
                ConfigurationTestHelper.unsetConfig.copy(
                        warmupIterations = 20,
                        warmupTime = 100,
                        warmupTimeUnit = Option.Some(TimeUnit.MILLISECONDS),
                        measurementIterations = 10
                )
        )

        @JvmStatic
        protected val expBench12 = Pair(
                SourceCodeTestHelper.NestedBenchmark.Bench1.bench12,
                ConfigurationTestHelper.unsetConfig.copy()
        )

        @JvmStatic
        protected val expBench31 = Pair(
                SourceCodeTestHelper.NestedBenchmark.Bench3.bench31,
                ConfigurationTestHelper.unsetConfig.copy(
                        warmupForks = 10,
                        mode = listOf("Throughput", "SampleTime"),
                        outputTimeUnit = Option.Some(TimeUnit.MICROSECONDS)
                )
        )

        @JvmStatic
        protected val expBench321 = Pair(
                SourceCodeTestHelper.NestedBenchmark.Bench3.Bench32.bench321,
                ConfigurationTestHelper.unsetConfig.copy(
                        mode = listOf("SingleShotTime")
                )
        )

        @JvmStatic
        protected val expBenchGroup1 = Pair(
                SourceCodeTestHelper.BenchsWithGroup.bench1,
                // fork annotation is ignored
                ConfigurationTestHelper.unsetConfig
        )

        @JvmStatic
        protected val expBenchGroup2 = Pair(
                SourceCodeTestHelper.BenchsWithGroup.bench1,
                ConfigurationTestHelper.unsetConfig
        )
    }
}
