package ch.uzh.ifi.seal.bencher

data class JMHVersion(
        val major: Int,
        val minor: Int
) : Comparable<JMHVersion> {

    override fun compareTo(other: JMHVersion): Int =
            if (this == other) {
                0
            } else if (this.major < other.major) {
                -1
            } else if (this.major == other.major && this.minor < other.minor) {
                -1
            } else {
                1
            }
}