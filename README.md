# Pedalean — App Android de alquiler de bicicletas

Aplicación Android nativa para alquilar bicicletas compartidas: autenticación, catálogo
de bicicletas, inicio y fin de alquiler, historial y perfil de usuario. Escrita en
**Kotlin** con **Jetpack Compose** y organizada según **Clean Architecture**.

> Proyecto de *Aplicacions per a Dispositius Mòbils* (ASM40) — CFGS / Grau, DEIM.

## Arquitectura

El código está separado en tres capas, cada una con una responsabilidad y sin que las
internas conozcan a las externas:

```text
presentation/   Compose + ViewModels     ← lo que ve el usuario
      ↓
domain/         Casos de uso + modelos   ← reglas de negocio, sin dependencias de Android
      ↓
data/           Repositorios             ← decide de dónde vienen los datos
                ├── api/        Retrofit  (remoto)
                └── database/   Room      (local)
```

**`domain/`** define las interfaces de repositorio (`IBikeRepository`, `IRentRepository`,
`IUserRepository`) y los casos de uso, uno por acción: `LoginUseCase`,
`GetAllBikesUseCase`, `GetBikeByUuidUseCase`, `StartRentUseCase`, `StopRentUseCase`,
`GetUserRentsUseCase`, `GetActiveUserUseCase`, `UpdatUserCase`. Al no depender del
framework, esta capa es la que concentra la lógica y la que se puede testear aislada.

**`data/`** implementa esas interfaces combinando dos orígenes: un *remote data source*
sobre Retrofit y un *local data source* sobre Room, con modelos propios a cada lado
(`BikeApiModel` / `BikeDTO`) que se mapean al modelo de dominio. Así un cambio en el
JSON del servidor no se propaga a la interfaz.

**`presentation/`** agrupa por pantalla, cada una con su Activity, su Composable y su
ViewModel: `splash`, `login`, `bikelist` (listado y detalle) y `profile`. La navegación
entre pantallas se centraliza en `NavGraph.kt`.

## Autenticación con refresco de token

El punto más interesante de la capa de red es el manejo de sesión, en
`TokenAuthenticator.kt`. Es un `Authenticator` de OkHttp que se activa cuando una llamada
devuelve **401**:

1. Recupera el *refresh token* de `TokenStorage`.
2. Pide un *access token* nuevo a `endpoints/v2/token/refresh/`.
3. Lo guarda y **reintenta automáticamente la petición original**.

Lleva un contador de reintentos para cortar el bucle si el refresco también falla, de
modo que la sesión se renueva de forma transparente sin que el usuario vuelva a hacer
login ni la app entre en un ciclo infinito de reintentos.

## API consumida

| Método | Endpoint | Uso |
|---|---|---|
| `POST` | `endpoints/v2/token/` | Login: obtener access + refresh token |
| `POST` | `endpoints/v2/token/refresh/` | Renovar el access token |
| `GET` | `endpoints/v2/user` | Datos del usuario autenticado |
| `GET` | `endpoints/v2/bike` | Catálogo de bicicletas |
| `GET` | `endpoints/v2/rent` | Historial de alquileres |
| `POST` | `endpoints/v2/rent/start` | Iniciar un alquiler |
| `POST` | `endpoints/v2/rent/stop` | Finalizar un alquiler |

Todas las llamadas envían una cabecera `server-token` además del `Authorization: Bearer`.

## Cómo compilar

Requiere **Android Studio** (Ladybug o posterior) y JDK 17.

```bash
./gradlew assembleDebug
```

El fichero `local.properties` (ruta del SDK) no está versionado; Android Studio lo genera
al abrir el proyecto. Las credenciales del servidor se configuran en
`asm_local.properties`.

## Stack

Kotlin · Jetpack Compose · Material 3 · Navigation Compose · Retrofit · OkHttp · Gson · Room · Coroutines · Gradle KTS
