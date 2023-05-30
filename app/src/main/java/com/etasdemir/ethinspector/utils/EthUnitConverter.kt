package com.etasdemir.ethinspector.utils

import java.math.BigDecimal

fun String.fromWei(unit: EthUnit = EthUnit.GWEI): BigDecimal {
    return BigDecimal(this).fromWei(unit)
}

fun BigDecimal.fromWei(unit: EthUnit = EthUnit.GWEI): BigDecimal {
    return this.divide(unit.weiFactor)
}

fun String.toWei(unit: EthUnit = EthUnit.GWEI): BigDecimal {
    return BigDecimal(this).toWei(unit)
}

fun BigDecimal.toWei(unit: EthUnit = EthUnit.GWEI): BigDecimal {
    return this.multiply(unit.weiFactor)
}

enum class EthUnit(name: String, factor: Int) {
    WEI("wei", 0),
    KWEI("kwei", 3),
    MWEI("mwei", 6),
    GWEI("gwei", 9),
    SZABO("szabo", 12),
    FINNEY("finney", 15),
    ETHER("ether", 18),
    KETHER("kether", 21),
    METHER("mether", 24),
    GETHER("gether", 27);

    val weiFactor: BigDecimal = BigDecimal.TEN.pow(factor)

    override fun toString(): String {
        return name
    }

    companion object {
        fun fromString(name: String): EthUnit {
            for (unit in values()) {
                if (name.equals(unit.name, ignoreCase = true)) {
                    return unit
                }
            }
            return valueOf(name)
        }
    }
}