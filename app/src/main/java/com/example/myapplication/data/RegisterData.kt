package com.example.myapplication.data

data class RegisterData(
    val device_id: Int,
    val sample_time: Long,
    val device_data: deviceData
) {
    data class deviceData(
        val volume: Int,
        val fecha: String,
        val hora: String,
        val id: Int,
    )
}