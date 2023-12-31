package br.com.usinasantafe.pcpk.common.utils

data class ResultUpdateDatabase(
    val count: Int,
    val describe: String,
    val size: Int,
    var percentage: Int = ((count * 100) / size)
)
