# 🎤 GUÍA DE EXPOSICIÓN - MOWI MARKET
## Aplicación E-commerce Android con Backend Django + Spring Boot

---

## 📋 ÍNDICE DE LA PRESENTACIÓN

1. **Introducción** (2 min)
2. **Arquitectura del Sistema** (3 min)
3. **Demostración de la Aplicación** (5 min)
4. **Aspectos Técnicos Destacados** (3 min)
5. **Conclusiones y Preguntas** (2 min)

**Tiempo Total:** ~15 minutos

---

## 1. INTRODUCCIÓN (2 minutos)

### ¿Qué es MOWI Market?

**"MOWI Market es una aplicación de comercio electrónico móvil completa, desarrollada en Android con Kotlin y Jetpack Compose, que permite a los usuarios explorar productos, realizar compras y gestionar sus pedidos de manera intuitiva y profesional."**

### Objetivo del Proyecto

- Crear una experiencia de compra moderna y atractiva para usuarios jóvenes
- Implementar arquitectura profesional con separación de responsabilidades
- Integrar múltiples tecnologías (Android, Django, Spring Boot, MySQL)
- Diseño responsive con Material Design 3 y tema personalizado

### Alcance

- ✅ Navegación sin login (explorar productos libremente)
- ✅ Sistema de carrito de compras completo
- ✅ Proceso de checkout con múltiples métodos de pago
- ✅ Perfil de usuario con historial de pedidos
- ✅ Diseño profesional con tema naranja vibrante

---

## 2. ARQUITECTURA DEL SISTEMA (3 minutos)

### Diagrama de Arquitectura

```
┌─────────────────────────────────────────────────┐
│           APLICACIÓN ANDROID (Kotlin)           │
│    - Jetpack Compose (UI)                       │
│    - MVVM Architecture                           │
│    - Navigation Compose                          │
│    - Retrofit (HTTP Client)                      │
│    - Coil (Image Loading)                        │
│    - DataStore (Local Storage)                   │
└─────────────────┬───────────────────────────────┘
                  │
        ┌─────────┴──────────┐
        │                    │
        ▼                    ▼
┌───────────────┐    ┌──────────────┐
│  Django API   │    │ Spring Boot  │
│  (Port 8000)  │    │  API (8080)  │
│  - Admin      │    │  - Clientes  │
│  - Auth JWT   │    │  - Productos │
└───────┬───────┘    └──────┬───────┘
        │                   │
        └─────────┬─────────┘
                  ▼
        ┌──────────────────┐
        │  MySQL Database  │
        │   (mowi_store)   │
        └──────────────────┘
```

### Stack Tecnológico

#### Frontend (Android)
- **Lenguaje:** Kotlin
- **UI Framework:** Jetpack Compose (Material Design 3)
- **Arquitectura:** MVVM (Model-View-ViewModel)
- **Navegación:** Navigation Compose
- **HTTP Client:** Retrofit 2.9.0 + OkHttp
- **Imágenes:** Coil 2.5.0
- **Persistencia Local:** DataStore

#### Backend
- **Django API (Python):** Administración, autenticación JWT
- **Spring Boot (Java 17):** API de productos y clientes
- **Base de Datos:** MySQL/MariaDB 10.4.32

### Patrones de Diseño Implementados

1. **MVVM (Model-View-ViewModel)**
   - Separación clara entre UI y lógica de negocio
   - ViewModels para gestión de estado
   - StateFlow para actualizaciones reactivas

2. **Repository Pattern**
   - Capa de abstracción para acceso a datos
   - Retrofit para comunicación con APIs

3. **Single Source of Truth**
   - CartViewModel compartido entre todas las pantallas
   - Estado centralizado del carrito

---

## 3. DEMOSTRACIÓN DE LA APLICACIÓN (5 minutos)

### 🎯 Flujo de Demostración

#### A. Inicio sin Login (30 seg)
**Mostrar:**
- Carrusel MOWI con auto-scroll
- Catálogo de productos con tema naranja
- Barra de búsqueda funcional
- Menú hamburguesa con categorías

**Hablar sobre:**
- "Como pueden ver, la aplicación permite navegar libremente sin necesidad de crear cuenta"
- "El diseño utiliza un tema naranja vibrante pensado para atraer a usuarios jóvenes"
- "El carrusel muestra promociones y cambia automáticamente cada 3 segundos"

#### B. Búsqueda y Filtrado (30 seg)
**Demostrar:**
1. Abrir menú hamburguesa
2. Seleccionar una categoría (ej: "Electrónica")
3. Usar barra de búsqueda (ej: "Smartphone")

**Hablar sobre:**
- "Los usuarios pueden filtrar productos por categoría o buscar específicamente"
- "La interfaz responde de forma instantánea gracias al uso de StateFlow en el ViewModel"

#### C. Detalle de Producto (45 seg)
**Mostrar:**
1. Click en un producto
2. Ver imagen, descripción, precio, stock
3. Selector de cantidad (+/-)
4. Intentar agregar sin login → muestra diálogo

**Hablar sobre:**
- "El detalle del producto muestra toda la información relevante"
- "Los usuarios pueden seleccionar la cantidad deseada"
- "Si no han iniciado sesión, se les solicita al intentar agregar al carrito"

#### D. Login y Agregar al Carrito (1 min)
**Demostrar:**
1. Click "Iniciar Sesión"
2. Login con: `cliente@mowi.com` / `cliente123`
3. Volver al producto
4. Agregar al carrito (mostrar pop-up)
5. Elegir "Ir a Comprar"

**Hablar sobre:**
- "El sistema de autenticación usa JWT tokens para seguridad"
- "El pop-up permite al usuario decidir si seguir comprando o proceder al pago"
- "El badge en el carrito muestra la cantidad de items agregados"

#### E. Carrito de Compras (45 seg)
**Mostrar:**
1. Lista de productos agregados
2. Modificar cantidad de un producto
3. Eliminar un producto
4. Ver total actualizado
5. Click en "Pagar"

**Hablar sobre:**
- "El carrito permite gestionar los productos antes de comprar"
- "El total se actualiza automáticamente al cambiar cantidades"
- "Todas las pantallas comparten el mismo estado del carrito gracias a un ViewModel centralizado"

#### F. Checkout y Pago (1 min)
**Demostrar:**
1. Llenar dirección y teléfono
2. Seleccionar "Tarjeta de Crédito"
3. Mostrar pop-up de datos de tarjeta
4. Llenar: número, titular, fecha, CVV
5. Confirmar pedido
6. Mostrar diálogo de éxito

**Hablar sobre:**
- "El checkout incluye validaciones de formulario"
- "El pop-up de tarjeta simula un proceso de pago seguro"
- "Soporta múltiples métodos de pago: tarjeta, efectivo contra entrega y transferencia"

#### G. Perfil y Pedidos (45 seg)
**Mostrar:**
1. Click en ícono de usuario
2. Ver perfil con avatar de iniciales
3. Scroll al historial de pedidos
4. Click en un pedido
5. Ver detalles completos: productos, total, estado

**Hablar sobre:**
- "El perfil muestra información del usuario de forma visualmente atractiva"
- "El historial de pedidos usa badges de colores para indicar el estado"
- "Los detalles del pedido muestran toda la información de la compra"

---

## 4. ASPECTOS TÉCNICOS DESTACADOS (3 minutos)

### A. Arquitectura MVVM

**Explicar:**
```kotlin
// ViewModel gestiona el estado
class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addToCart(producto: Producto, cantidad: Int) {
        // Lógica de negocio
    }
}

// UI observa y reacciona
@Composable
fun CartScreen(cartViewModel: CartViewModel) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    // UI se actualiza automáticamente
}
```

**Beneficios:**
- Separación de responsabilidades
- Testing más fácil
- UI reactiva
- Sobrevive a cambios de configuración

### B. Navegación Type-Safe

**Mostrar:**
```kotlin
sealed class Routes(val route: String) {
    object Home : Routes("home")
    object ProductDetail : Routes("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
}

// Navegación segura
navController.navigate(Routes.ProductDetail.createRoute(producto.id))
```

### C. Gestión de Estado Compartido

**Explicar:**
- CartViewModel creado una sola vez en NavGraph
- Pasado como parámetro a todas las pantallas
- Todas las pantallas ven el mismo estado
- Soluciona el problema de datos duplicados

### D. Integración con APIs REST

**Mostrar:**
```kotlin
// Retrofit configurado con interceptores
object RetrofitClient {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        }
        .build()
}

// Llamadas tipo-seguras
interface ApiService {
    @GET("productos")
    suspend fun getProductos(): Response<List<Producto>>
}
```

### E. Diseño Material Design 3

**Destacar:**
- Tema personalizado con colores naranjas
- Color scheme completo (primary, secondary, surface, etc.)
- Componentes modernos (Cards, TopAppBar, NavigationDrawer)
- Gradientes y sombras para profundidad
- Animaciones fluidas (carrusel auto-scroll)

---

## 5. CONCLUSIONES Y PREGUNTAS (2 minutos)

### Logros del Proyecto

✅ **Funcionalidad Completa:**
- Navegación pública de productos
- Sistema de carrito robusto
- Checkout con múltiples métodos de pago
- Gestión de perfil y pedidos

✅ **Arquitectura Profesional:**
- MVVM con separación de responsabilidades
- Backend dual (Django + Spring Boot)
- Base de datos relacional (MySQL)

✅ **UI/UX Moderna:**
- Material Design 3
- Tema personalizado naranja vibrante
- Diseño responsivo y fluido
- Experiencia optimizada para jóvenes

### Mejoras Futuras

🔮 **Posibles Expansiones:**
- Integración con pasarelas de pago reales (Stripe, PayPal)
- Sistema de reseñas y calificaciones de productos
- Notificaciones push para estado de pedidos
- Wishlist (lista de deseos)
- Modo oscuro
- Caché local de productos con Room
- Tracking en tiempo real de pedidos

### Lecciones Aprendidas

📚 **Conocimientos Adquiridos:**
- Jetpack Compose para UI declarativa
- Arquitectura MVVM en Android
- Integración de múltiples APIs REST
- Gestión de estado compartido
- Diseño de sistemas distribuidos

---

## 📝 TIPS PARA LA EXPOSICIÓN

### Antes de Exponer

✅ **Preparación:**
1. Tener los servidores corriendo (Django + Spring Boot)
2. App instalada en emulador o dispositivo físico
3. Probar todo el flujo antes de presentar
4. Tener datos de prueba listos (productos, pedidos)
5. Preparar respuestas a preguntas técnicas comunes

### Durante la Exposición

✅ **Mantener:**
- Contacto visual con la audiencia
- Ritmo constante (no ir muy rápido)
- Explicaciones claras y concisas
- Mostrar código solo cuando sea relevante

✅ **Evitar:**
- Leer diapositivas palabra por palabra
- Entrar en detalles técnicos excesivos
- Perder tiempo en bugs o errores
- Hablar muy técnico si la audiencia es general

### Preguntas Frecuentes y Respuestas

**P: ¿Por qué usaste dos backends?**
R: "Django se encarga de la administración y autenticación por su robustez, mientras Spring Boot maneja las operaciones de clientes por su alto rendimiento en APIs REST."

**P: ¿Cómo manejas la seguridad?**
R: "Uso JWT tokens para autenticación, HTTPS para comunicaciones, y validaciones tanto en frontend como backend."

**P: ¿Por qué Compose en lugar de XML?**
R: "Jetpack Compose es el estándar moderno de Android, ofrece UI declarativa, menos código boilerplate y mejor rendimiento."

**P: ¿Cómo escalaría esto para producción?**
R: "Agregaría caché con Redis, CDN para imágenes, balanceador de carga, Docker para deployment, y CI/CD con GitHub Actions."

**P: ¿Funciona offline?**
R: "Actualmente no, pero se podría implementar con Room Database para caché local y sincronización posterior."

---

## 🎬 SCRIPT DE APERTURA SUGERIDO

**"Buenos días/tardes. Hoy les voy a presentar MOWI Market, una aplicación de comercio electrónico móvil que desarrollé utilizando tecnologías modernas de Android.**

**El objetivo principal era crear una experiencia de compra intuitiva y atractiva, especialmente diseñada para usuarios jóvenes, con un diseño vibrante y funcionalidades completas.**

**La aplicación permite navegar productos sin necesidad de registro, agregar items al carrito, realizar pagos con diferentes métodos, y gestionar un perfil con historial de pedidos.**

**Técnicamente, implementé una arquitectura MVVM en Android con Kotlin y Jetpack Compose, conectada a un backend dual usando Django para administración y Spring Boot para la API de clientes, todo respaldado por MySQL.**

**Ahora les mostraré cómo funciona..."**

---

## 🎯 CHECKLIST FINAL

Antes de exponer, verifica:

- [ ] Servidores corriendo (Django + Spring Boot)
- [ ] MySQL corriendo en XAMPP
- [ ] App actualizada con último código
- [ ] Emulador/dispositivo funcionando
- [ ] Conexión a Internet estable
- [ ] Presentación/diapositivas listas (opcional)
- [ ] Conoces bien todo el flujo
- [ ] Tienes backup de datos de prueba
- [ ] Código fuente accesible para mostrar
- [ ] Respuestas preparadas para preguntas comunes

---

## 📚 RECURSOS ADICIONALES

### Para Mostrar el Código (Si te preguntan)

**Archivos importantes a tener a mano:**
- `ProfileScreen.kt` - Perfil con historial
- `CartViewModel.kt` - Gestión de carrito
- `NavGraph.kt` - Navegación
- `Theme.kt` - Tema personalizado
- `RetrofitClient.kt` - Configuración de API

### Datos de Prueba

```
Usuario: cliente@mowi.com
Password: cliente123

Admin: admin@mowi.com
Password: admin123
```

---

## 🎉 ¡MUCHA SUERTE EN TU EXPOSICIÓN!

**Recuerda:**
- Sé confiado y claro
- La práctica hace al maestro
- No temas admitir si no sabes algo
- Muestra pasión por tu proyecto
- ¡Disfruta el momento!

---

**Creado con ❤️ para tu éxito**
**MOWI Market - E-commerce Profesional**
