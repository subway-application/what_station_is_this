package com.example.subway.bookmark

// 즐겨찾기 관리 클래스
object FavoriteRoutesManager {
    private val favoriteRoutes = mutableListOf<Route>()

    fun addFavoriteRoute(route: Route) {
        favoriteRoutes.add(route)
    }

    fun getFavoriteRoutes(): List<Route> {
        return favoriteRoutes.toList()
    }
}