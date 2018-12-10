package ch.uzh.ifi.seal.bencher.analysis.callgraph.sta

import ch.uzh.ifi.seal.bencher.analysis.JarTestHelper
import com.ibm.wala.ipa.callgraph.Entrypoint
import org.junit.jupiter.api.Assertions

object EntrypointTestHelper {

    object BenchParameterized {
        val entrypoints = listOf(
                Pair(CGStartMethod(JarTestHelper.BenchParameterized.bench1), EntrypointMock(JarTestHelper.BenchParameterized.bench1)),
                Pair(CGAdditionalMethod(JarTestHelper.BenchParameterized.setup), EntrypointMock(JarTestHelper.BenchParameterized.setup))
        )
    }

    object BenchNonParameterized {
        val entrypoints = listOf(
                Pair(CGStartMethod(JarTestHelper.BenchNonParameterized.bench2), EntrypointMock(JarTestHelper.BenchNonParameterized.bench2))
        )
    }

    object OtherBench {
        val entrypoints = listOf(
                Pair(CGStartMethod(JarTestHelper.OtherBench.bench3), EntrypointMock(JarTestHelper.OtherBench.bench3)),
                Pair(CGAdditionalMethod(JarTestHelper.OtherBench.setup), EntrypointMock(JarTestHelper.OtherBench.setup)),
                Pair(CGAdditionalMethod(JarTestHelper.OtherBench.tearDown), EntrypointMock(JarTestHelper.OtherBench.tearDown))
        )
    }

    object BenchParameterized2 {
        val entrypoints = listOf(
                Pair(CGStartMethod(JarTestHelper.BenchParameterized2.bench4), EntrypointMock(JarTestHelper.BenchParameterized2.bench4)),
                Pair(CGAdditionalMethod(JarTestHelper.BenchParameterized2.setup), EntrypointMock(JarTestHelper.BenchParameterized2.setup))
        )
    }

    fun validateEntrypoints(eps: List<Pair<CGMethod, Entrypoint>>, expectedEps: List<Pair<CGMethod, Entrypoint>>) {
        val size = expectedEps.size
        val s = eps.size
        Assertions.assertTrue(s == size, "Entrypoint list not of expected ($s) size (was $size)")

        expectedEps.forEach { (m, _) ->
            val c = eps.any { (method, _) ->  m == method}
            Assertions.assertTrue(c, "Entrypoint list does not contain ($m)")
        }
    }

    fun containsEntrypoints(eps: Iterable<Pair<CGMethod, Entrypoint>>, expectedEps: Iterable<Pair<CGMethod, Entrypoint>>): Boolean =
            expectedEps.all { (m, _) -> eps.any { (method, _) ->  m == method} }
}
