package ch.uzh.ifi.seal.bencher.analysis.finder

import ch.uzh.ifi.seal.bencher.Benchmark
import ch.uzh.ifi.seal.bencher.analysis.JarTestHelper
import ch.uzh.ifi.seal.bencher.fileResource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class AsmBenchFinderTest : AbstractAsmBenchFinderTest() {

    private fun jmhBenchs(bf: AsmBenchFinder, bs: Iterable<Benchmark>): jmhBenchs =
            bs.map { b ->
                Triple(setOf(b), bf.setups(b).toSet(), bf.tearDowns(b).toSet())
            }

    @Test
    fun twoBenchs121() {
        val url = JarTestHelper.jar2BenchsJmh121.fileResource()
        Assertions.assertNotNull(url, "Could not get resource")

        val bf = AsmBenchFinder(url.absoluteFile, pkgPrefix)
        val ebs = bf.all()
        if (ebs.isLeft()) {
            Assertions.fail<String>("Could not retrieve benchmarks: ${ebs.left().get()}")
        }
        val bs = ebs.right().get()
        assertTwoBenchs(bs)
        assertBenchsSetupsTearDowns(jmhBenchs(bf, bs))
    }

    @Test
    fun fourBenchs121() {
        val url = JarTestHelper.jar4BenchsJmh121.fileResource()
        Assertions.assertNotNull(url, "Could not get resource")

        val bf = AsmBenchFinder(url.absoluteFile, pkgPrefix)
        val ebs = bf.all()
        if (ebs.isLeft()) {
            Assertions.fail<String>("Could not retrieve benchmarks: ${ebs.left().get()}")
        }
        val bs = ebs.right().get()
        assertTwoBenchs(bs)
        assertBenchsSetupsTearDowns(jmhBenchs(bf, bs))
    }

    @Test
    fun fourBenchs121NoPp() {
        val url = JarTestHelper.jar4BenchsJmh121.fileResource()
        Assertions.assertNotNull(url, "Could not get resource")

        val bf = AsmBenchFinder(url.absoluteFile)
        val ebs = bf.all()
        if (ebs.isLeft()) {
            Assertions.fail<String>("Could not retrieve benchmarks: ${ebs.left().get()}")
        }
        val bs = ebs.right().get()
        assertTwoBenchs(bs)
        assertBenchsSetupsTearDowns(jmhBenchs(bf, bs))
    }

    @Test
    fun twoBenchs110() {
        val url = JarTestHelper.jar2BenchsJmh110.fileResource()
        Assertions.assertNotNull(url, "Could not get resource")

        val bf = AsmBenchFinder(url.absoluteFile, pkgPrefix)
        val ebs = bf.all()
        if (ebs.isLeft()) {
            Assertions.fail<String>("Could not retrieve benchmarks: ${ebs.left().get()}")
        }
        val bs = ebs.right().get()
        assertTwoBenchs(bs)
        assertBenchsSetupsTearDowns(jmhBenchs(bf, bs))
    }

    @Test
    fun fourBenchs110() {
        val url = JarTestHelper.jar4BenchsJmh110.fileResource()
        Assertions.assertNotNull(url, "Could not get resource")

        val bf = AsmBenchFinder(url.absoluteFile, pkgPrefix)
        val ebs = bf.all()
        if (ebs.isLeft()) {
            Assertions.fail<String>("Could not retrieve benchmarks: ${ebs.left().get()}")
        }
        val bs = ebs.right().get()
        assertTwoBenchs(bs)
        assertBenchsSetupsTearDowns(jmhBenchs(bf, bs))
    }

    companion object {
        val pkgPrefix = "org/sample"
    }
}
