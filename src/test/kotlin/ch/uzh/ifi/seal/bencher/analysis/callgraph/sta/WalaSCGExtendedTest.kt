package ch.uzh.ifi.seal.bencher.analysis.callgraph.sta

import ch.uzh.ifi.seal.bencher.Benchmark
import ch.uzh.ifi.seal.bencher.PlainMethod
import ch.uzh.ifi.seal.bencher.analysis.JarTestHelper
import ch.uzh.ifi.seal.bencher.analysis.callgraph.CGResult
import ch.uzh.ifi.seal.bencher.analysis.finder.JarBenchFinder
import ch.uzh.ifi.seal.bencher.fileResource
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class WalaSCGExtendedTest : WalaSCGTest() {

    override val cg: CGResult
        get() = WalaSCGExtendedTest.cg

    override val multiCGEntrypoints = false

    @Test
    fun nonLibCallsBench1() {
        sout(bench1, 2)
    }

    @Test
    fun nonLibCallsBench2() {
        sout(bench2, 2)
    }

    @Test
    fun nonLibCallsBench3() {
        sout(bench3, 2)
    }

    fun sout(bench: Benchmark, level: Int) {
        val sbNew = PlainMethod(clazz = "java.lang.StringBuilder", name = "<init>", params = listOf())
        h.reachable(cg, bench, sbNew, level)
        val sbAppend = PlainMethod(clazz = "java.lang.StringBuilder", name = "append", params = listOf("java.lang.String"))
        h.reachable(cg, bench, sbAppend, level)
        val sbToString = PlainMethod(clazz = "java.lang.StringBuilder", name = "toString", params = listOf())
        h.reachable(cg, bench, sbToString, level)
        val funPrintln = PlainMethod(clazz = "java.io.PrintStream", name = "println", params = listOf("java.lang.String"))
        h.reachable(cg, bench, funPrintln, level)
    }

    companion object {
        lateinit var cg: CGResult

        @JvmStatic
        @BeforeAll
        fun setup() {
            val jar = JarTestHelper.jar4BenchsJmh121.fileResource()

            cg = h.assertCGResult(
                    WalaSCG(
                            entrypoints = CGEntrypoints(
                                    mf = JarBenchFinder(jar),
                                    me = BenchmarkWithSetupTearDownEntrypoints(),
                                    ea = SingleCGEntrypoints()
                            ),
                            algo = WalaRTA()
                    ),
                    jar = jar
            )
        }
    }
}
