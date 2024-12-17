# Voluntariado Comunitario

Esta aplicación permite a los usuarios encontrar y participar en actividades de voluntariado cercanas. Utiliza Firebase Firestore para almacenar los datos de las actividades y la ubicación del dispositivo para filtrar las actividades cercanas.

## Características

- **Buscar Actividades**: Los usuarios pueden buscar actividades de voluntariado por título.
- **Actividades Cercanas**: Muestra actividades de voluntariado dentro de un radio de 50 km de la ubicación del usuario.
- **Detalles de la Actividad**: Los usuarios pueden ver los detalles de cada actividad de voluntariado.
- **Widget**: Un widget que muestra las próximas actividades de voluntariado.

## Tecnologías Utilizadas

- **Kotlin**: Lenguaje de programación principal.
- **Firebase Firestore**: Base de datos en tiempo real para almacenar y recuperar datos de actividades de voluntariado.
- **Coroutines**: Para realizar operaciones asíncronas.
- **Geocoder**: Para convertir direcciones en coordenadas geográficas.
- **Google Play Services Location**: Para obtener la ubicación del dispositivo.
- **Android Jetpack**: Incluyendo Navigation, ViewModel y LiveData.

## Estructura del Proyecto

### `MainActivity.kt`

La actividad principal que configura la navegación y el botón para mostrar las actividades cercanas.

### `NearbyActivitiesFragment.kt`

Fragmento que muestra las actividades de voluntariado cercanas. Utiliza `FusedLocationProviderClient` para obtener la ubicación del dispositivo y `Geocoder` para convertir las direcciones en coordenadas.

### `SearchFragment.kt`

Fragmento que permite a los usuarios buscar actividades de voluntariado por título. Utiliza un `RecyclerView` para mostrar los resultados de la búsqueda.

### `OpportunitiesAdapter.kt`

Adaptador para el `RecyclerView` que muestra las actividades de voluntariado.

### `FirebaseHelper.kt`

Clase que maneja la interacción con Firebase Firestore para obtener las actividades de voluntariado.

### `VolunteerOpportunitiesWidget.kt`

Proveedor de widget que muestra las próximas actividades de voluntariado en la pantalla de inicio del dispositivo.

## Uso

1. **Buscar Actividades**: Ingresa un término de búsqueda en el campo de texto y presiona el botón de búsqueda.
2. **Ver Actividades Cercanas**: Presiona el botón "Eventos Cercanos" para ver las actividades dentro de un radio de 50 km de tu ubicación actual.
3. **Ver Detalles de la Actividad**: Toca una actividad en la lista para ver sus detalles.

link al repositorio ->
