package ch.uzh.ifi.seal.bencher.analysis.sta

import ch.uzh.ifi.seal.bencher.analysis.JarHelper
import ch.uzh.ifi.seal.bencher.analysis.callgraph.CGResult
import ch.uzh.ifi.seal.bencher.analysis.callgraph.WalaCGResult
import ch.uzh.ifi.seal.bencher.analysis.callgraph.sta.*
import ch.uzh.ifi.seal.bencher.analysis.finder.JarBenchFinder
import ch.uzh.ifi.seal.bencher.fileResource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class WalaSCGLibOnlyTest : WalaSCGTest() {

    override val cg: CGResult
        get() = WalaSCGLibOnlyTest.cg

    @Test
    fun libOnlyCalls() {
        val justLibCalls = cg.benchCalls.values.flatten().fold(true) { acc, mc ->
            acc && mc.method.clazz.startsWith(pkgPrefix)
        }

        Assertions.assertTrue(justLibCalls, "Non-lib calls in CG")
    }

    companion object {
        val h = WalaSCGTestHelper
        lateinit var cg: WalaCGResult

        val pkgPrefix = "org.sample"

        @JvmStatic
        @BeforeAll
        fun setup() {
            val jar = JarHelper.jar3BenchsJmh121.fileResource()
            val jarPath = jar.absolutePath

            cg = h.assertCGResult(
                    WalaSCG(
                            jar = jarPath,
                            entrypoints = CGEntrypoints(
                                    mf = JarBenchFinder(jarPath),
                                    me = BenchmarkWithSetupTearDownEntrypoints(),
                                    ea = SingleCGEntrypoints()
                            ),
                            algo = WalaRTA(),
                            inclusions = IncludeOnly(setOf(pkgPrefix))
                    )
            )
        }
    }
}
