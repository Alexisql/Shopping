# Shopping
<p>
<img src="https://img.shields.io/badge/Android-%23FFFFFF?logo=android">
<img src="https://img.shields.io/badge/Kotlin-%23FFFFFF?logo=kotlin">
<img src="https://img.shields.io/badge/Jetpack%20Compose-%23FFFFFF?logo=jetpackcompose">
</p>

App móvil moderna para buscar y añadir al carrito de compras, productos Pokemon de forma eficiente, construida con Kotlin y Jetpack Compose. Utiliza Retrofit, Room y Paging 3 para manejar grandes volúmenes de datos y usar la app de forma offline, todo estructurado bajo la arquitectura Clean Architecture y MVVM.

## MVVM
Se implementa MVVM debido a la reactividad de la aplicacion, donde sus datos estan en un cambio constante.

## Retrofit
Se usa retrofit para el consumo de la API POKEAPI

## Room
Se usa Room para el almacenamiento de datos local y hacer uso de la app de forma offline

## Pagging 3
Se usa pagging 3 para la administracion de datos bajo de demanda y RemoteMediator para la sincronizacion de datos entre la API y ROOM

## Notificaciones
Se implementan las notificaciones locales, para avisar al usuario cualquier cambio en su carrito de compras, ya sea cuando se agrega, elimina o si hay algun cambio en los datos del pokemon.

## Características del proyecto:

- Clean Architecture
- Arquitectura **MVVM**.
- Principios **SOLID**
- Corrutinas
- Flows
- StateFlow
- Retrofit
- Room
- Paging 3
- Inyección de dependencias con **Dagger Hilt**
- Jetpack Compose
- Glide
- Navigation

## Observaciones para correr la App
Android Studio Meerkat Feature Drop | 2024.3.2 Patch 1
Version minima del SDK Android 24
Gradle 8.11.1
