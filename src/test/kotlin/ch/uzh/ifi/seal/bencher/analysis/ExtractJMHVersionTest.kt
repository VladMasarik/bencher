package ch.uzh.ifi.seal.bencher.analysis

import ch.uzh.ifi.seal.bencher.fileResource
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ExtractJMHVersionTest {

    @Test
    fun twoBenchs121() {
        val url = JarTestHelper.jar2BenchsJmh121.fileResource()

        val v = ExtractJMHVersion(url).getVersion()
        Assertions.assertTrue(v.right().get() == JarTestHelper.jar2BenchsJmh121Version)
    }

    @Test
    fun fourBenchs121() {
        val url = JarTestHelper.jar4BenchsJmh121.fileResource()

        val v = ExtractJMHVersion(url).getVersion()
        Assertions.assertTrue(v.right().get() == JarTestHelper.jar4BenchsJmh121Version)
    }

    @Test
    fun twoBenchs110() {
        val url = JarTestHelper.jar2BenchsJmh110.fileResource()

        val v = ExtractJMHVersion(url).getVersion()
        Assertions.assertTrue(v.right().get() == JarTestHelper.jar2BenchsJmh110Version)
    }

    @Test
    fun fourBenchs110() {
        val url = JarTestHelper.jar4BenchsJmh110.fileResource()

        val v = ExtractJMHVersion(url).getVersion()
        Assertions.assertTrue(v.right().get() == JarTestHelper.jar4BenchsJmh110Version)
    }

    @Test
    fun jmhVersionSpecified() {
        val url = JarTestHelper.jar4BenchsJmh110.fileResource()

        val v = ExtractJMHVersion(url)
        v.getVersion()
        Assertions.assertTrue(v.isVersionSpecified())
    }

    @Test
    fun noJmhVersionSpecified() {
        val url = JarTestHelper.jar4BenchsJmh10.fileResource()

        val v = ExtractJMHVersion(url)
        v.getVersion()
        Assertions.assertTrue(!v.isVersionSpecified())
    }
}