/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package day6

import java.util.*

val celestialMassIndex: MutableMap<String, Mass> = mutableMapOf()
private fun indexLookup(tag: String): Mass {
    val mass = celestialMassIndex.getOrDefault(tag, Mass(tag))
    celestialMassIndex.putIfAbsent(tag, mass)
    return mass;
}

fun main() {
    OrbitMap.split("\n").forEach {
        val split = it.split(")")
        val center = indexLookup(split[0])
        val orbiter = indexLookup(split[1])
        center.addOrbiter(orbiter)
    }

    val COM = celestialMassIndex["COM"]!!
    markDistance(COM, 0)
    celestialMassIndex.values
            .map { it.distanceToCOM }
            .reduce { acc, n -> acc + n }
            .print()


    val myCenter = celestialMassIndex["YOU"]!!.center!!
    val myCenters = myCenter.centers()

    val santaCenter = celestialMassIndex["SAN"]!!.center!!
    val santaCenters = santaCenter.centers()

    val firstCommon = myCenters.first { santaCenters.contains(it) }

    // 552 incorrect!!!
    val myDistance = myCenter.distanceTo(firstCommon)
    val santaDistance = santaCenter.distanceTo(firstCommon)
    (myDistance + santaDistance).print()
}

private fun Int.print() {
    println("$this")
}

private fun markDistance(mass: Mass, distance: Int) {
    mass.distanceToCOM = distance
    mass.orbiters.forEach {
        markDistance(it, distance + 1)
    }

}


class Mass(val tag: String) {
    var center: Mass? = null
    val orbiters: MutableList<Mass> = mutableListOf()

    var distanceToCOM = 0

    fun addOrbiter(mass: Mass) {
        orbiters.add(mass)
        mass.center = this
    }

    fun centers(): List<Mass> = centers(emptyList())
    private fun centers(list: List<Mass>): List<Mass> {
        return center?.centers(list + center!!) ?: list

    }

    fun distanceTo(target: Mass): Int {
        if (center == null) throw RuntimeException("Not a parent")
        if (center == target) return 1;
        return center!!.distanceTo(target) + 1
    }

    override fun toString(): String {
        return "Mass(tag='$tag', center=$center)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Mass

        if (tag != other.tag) return false

        return true
    }

    override fun hashCode(): Int {
        return tag.hashCode()
    }


}