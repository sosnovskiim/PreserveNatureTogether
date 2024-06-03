package com.example.preservenaturetogether.data

data class District(
    val id: Int,
    val name: String,
    val photo: String,
)

data class Category(
    val id: Int,
    val name: String,
) {
    companion object {
        const val HISTORICAL = "Исторические памятники"
        const val NATURAL = "Природные памятники"
        const val RECREATIONAL = "Зоны отдыха"
    }
}

data class EcoCondition(
    val id: Int,
    val name: String,
) {
    companion object {
        const val POOR = "Плохое"
        const val FAIR = "Удовлетворительное"
        const val GOOD = "Хорошее"
    }
}

data class Site(
    val id: Int = 0,
    val districtId: Int = 0,
    val categoryId: Int = 0,
    val ecoConditionId: Int = 0,
    val name: String = "",
    val description: String = "",
    val ecoNotes: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val photo1: String = "",
    val photo2: String = "",
)

data class Role(
    val id: Int = 0,
    val name: String = "",
) {
    companion object {
        const val ADMIN = "Администратор"
        const val EDITOR = "Редактор"
        const val READER = "Читатель"
    }
}

data class User(
    val id: Int = 0,
    val roleId: Int = 0,
    val login: String = "",
    val password: String = "",
)