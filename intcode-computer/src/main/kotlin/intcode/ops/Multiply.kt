package intcode.ops

import intcode.Param
import java.math.BigInteger

class Multiply(private val a: Param,
               private val b: Param,
               private val result: Param) : Operation {
    override fun eval(): (BigInteger) -> BigInteger {
        result.write(a.read() * b.read())
        return { it + 4.toBigInteger() }
    }

    override fun toString(): String {
        return "MUL(a=$a, b=$b, result=$result)"
    }


}