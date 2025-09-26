package com.alexis.shopping.domain.repository

fun interface INotificationRepository {
    fun sendNotification(message: String, title: String)
}