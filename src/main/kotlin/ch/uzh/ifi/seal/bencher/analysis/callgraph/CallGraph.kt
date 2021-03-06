package ch.uzh.ifi.seal.bencher.analysis.callgraph

import ch.uzh.ifi.seal.bencher.Method
import ch.uzh.ifi.seal.bencher.analysis.callgraph.reachability.RF
import ch.uzh.ifi.seal.bencher.analysis.callgraph.reachability.Reachabilities
import ch.uzh.ifi.seal.bencher.analysis.callgraph.reachability.Reachability
import ch.uzh.ifi.seal.bencher.analysis.callgraph.reachability.ReachabilityResult


data class CGResult(
        val calls: Map<Method, Reachabilities>
) : Reachability {

    override fun reachable(from: Method, to: Method): ReachabilityResult {
        val mcs = calls[from] ?: return RF.notReachable(from, to)
        return mcs.reachable(from, to)
    }

    override fun reachabilities(removeDuplicateTos: Boolean): Set<ReachabilityResult> =
            calls.flatMap { it.value.reachabilities(removeDuplicateTos) }.toSet()
}

fun Iterable<CGResult>.merge(): CGResult =
        this.fold(CGResult(mapOf())) { acc, cgr -> merge(acc, cgr) }


fun merge(cgr1: CGResult, cgr2: CGResult): CGResult {
    val c1 = cgr1.calls
    val c2 = cgr2.calls
    val intersectingKeys = c1.keys.intersect(c2.keys)
    if (intersectingKeys.isEmpty()) {
        // disjoint set of benchmarks -> return the union of the map
        return CGResult(
                calls = c1 + c2
        )
    }

    // overlapping benchmark sets
    val newCalls = mutableMapOf<Method, Reachabilities>()
    // bc1 benchmarks that are not in bc2
    newCalls.putAll(c1.filterKeys { intersectingKeys.contains(it) })
    // bc2 benchmarks that are not in bc1
    newCalls.putAll(c2.filterKeys { intersectingKeys.contains(it) })
    // merge of benchmarks that are in both bc1 and bc2
    newCalls.putAll(
            intersectingKeys.map {
                Pair(it, c1.getValue(it).union(c2.getValue(it)))
            }
    )

    return CGResult(
            calls = newCalls
    )
}
