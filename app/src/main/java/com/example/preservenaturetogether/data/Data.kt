package com.example.preservenaturetogether.data

const val HISTORICAL = "historical"
const val NATURAL = "natural"
const val RECREATIONAL = "recreational"

const val POOR = "poor"
const val FAIR = "fair"
const val GOOD = "good"

data class District(
    val id: Int,
    val name: String,
    val photo: String,
)

data class Category(
    val id: Int,
    val name: String,
)

data class EcoCondition(
    val id: Int,
    val name: String,
)

data class Site(
    val id: Int,
    val districtId: Int,
    val categoryId: Int,
    val ecoConditionId: Int,
    val name: String,
    val description: String,
    val suggestion: String,
    val latitude: Float,
    val longitude: Float,
    val photo1: String,
    val photo2: String,
)