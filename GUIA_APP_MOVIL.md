# 📱 GUÍA DE EXPOSICIÓN - APP MÓVIL MOWI MARKET
## Aplicación Android E-commerce con Kotlin y Jetpack Compose

---

## 🎯 INTRODUCCIÓN (1-2 min)

### Presentación del Proyecto

**"MOWI Market es una aplicación móvil de comercio electrónico desarrollada nativamente para Android, utilizando Kotlin y Jetpack Compose con arquitectura MVVM profesional."**

### Características Principales

✅ **Navegación Libre** - Explorar productos sin necesidad de login
✅ **Carrito Inteligente** - Sistema completo de gestión de compras
✅ **Checkout Seguro** - Proceso de pago con validación de tarjeta
✅ **Perfil Personalizado** - Historial de pedidos y datos de usuario
✅ **Diseño Moderno** - Tema naranja vibrante con Material Design 3

---

## 🏗️ ARQUITECTURA DE LA APP (2-3 min)

### Patrón MVVM (Model-View-ViewModel)

```
┌─────────────────────────────────────────┐
│           CAPA DE UI (View)             │
│  HomeScreen.kt, CartScreen.kt, etc.     │
│  - Jetpack Compose                      │
│  - Material Design 3                    │
└──────────────┬──────────────────────────┘
               │ observa
               ▼
┌─────────────────────────────────────────┐
│        VIEWMODELS (ViewModel)           │
│  ProductViewModel.kt, CartViewModel.kt  │
│  - Gestión de estado (StateFlow)       │
│  - Lógica de negocio                    │
└──────────────┬──────────────────────────┘
               │ solicita datos
               ▼
┌─────────────────────────────────────────┐
│         MODELOS Y DATOS (Model)         │
│  Producto.kt, User.kt, Pedido.kt        │
│  RetrofitClient.kt (API)                │
│  - Modelos de datos                     │
│  - Comunicación con servidor            │
└─────────────────────────────────────────┘
```

### Estructura del Proyecto

```
app/src/main/java/com/miempresa/mowimarket/
│
├── data/
│   ├── model/
│   │   ├── Producto.kt          # Modelo de producto
│   │   ├── CartItem.kt          # Item del carrito
│   │   ├── User.kt              # Modelo de usuario
│   │   ├── Pedido.kt            # Modelo de pedido
│   │   └── Carrito.kt           # Modelo de carrito
│   │
│   └── remote/
│       ├── RetrofitClient.kt    # Cliente HTTP (Retrofit)
│       └── ApiService.kt        # Definición de endpoints
│
├── ui/
│   ├── screens/
│   │   ├── user/
│   │   │   ├── HomeScreen.kt          # Pantalla principal
│   │   │   ├── ProductDetailScreen.kt # Detalle de producto
│   │   │   ├── CartScreen.kt          # Carrito de compras
│   │   │   ├── CheckoutScreen.kt      # Proceso de pago
│   │   │   ├── ProfileScreen.kt       # Perfil de usuario
│   │   │   └── OrderDetailScreen.kt   # Detalle de pedido
│   │   │
│   │   └── auth/
│   │       ├── LoginScreen.kt         # Pantalla de login
│   │       └── RegisterScreen.kt      # Registro de usuario
│   │
│   ├── viewmodel/
│   │   ├── ProductViewModel.kt  # ViewModel de productos
│   │   ├── CartViewModel.kt     # ViewModel del carrito
│   │   └── AuthViewModel.kt     # ViewModel de autenticación
│   │
│   └── theme/
│       ├── Color.kt             # Colores del tema naranja
│       ├── Theme.kt             # Configuración del tema
│       └── Type.kt              # Tipografía
│
├── navigation/
│   ├── Routes.kt                # Definición de rutas
│   └── NavGraph.kt              # Grafo de navegación
│
└── MainActivity.kt              # Actividad principal
```

---

## 🎨 TECNOLOGÍAS Y HERRAMIENTAS (2 min)

### Tecnologías Principales

#### 1. **Kotlin** (Lenguaje)
- Lenguaje moderno, conciso y seguro
- Null-safety integrado
- Coroutines para operaciones asíncronas
- Extension functions

#### 2. **Jetpack Compose** (UI Framework)
- UI declarativa (describe CÓMO debe verse, no CÓMO construirla)
- Menos código boilerplate que XML
- Recomposición automática cuando cambia el estado
- Material Design 3 integrado

**Ejemplo en `HomeScreen.kt`:**
```kotlin
@Composable
fun HomeScreen() {
    LazyColumn {  // Se recompone automáticamente
        item { MowiCarousel() }
        item { ProductGrid(productos) }
    }
}
```

#### 3. **Architecture Components**

**a) ViewModel** (`ProductViewModel.kt`, `CartViewModel.kt`)
```kotlin
class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addToCart(producto: Producto, cantidad: Int) {
        // Lógica que sobrevive a rotaciones de pantalla
    }
}
```

**b) StateFlow** (Estado reactivo)
- UI se actualiza automáticamente cuando cambia el estado
- Thread-safe
- Integración perfecta con Compose

**c) Navigation Compose** (`NavGraph.kt`)
- Navegación type-safe entre pantallas
- Manejo de backstack automático
- Paso de argumentos tipados

#### 4. **Retrofit + OkHttp** (`RetrofitClient.kt`)
- Cliente HTTP para consumir APIs REST
- Conversión automática JSON ↔ Objetos Kotlin
- Interceptores para autenticación (JWT)

#### 5. **Coil** (Carga de imágenes)
- Carga asíncrona de imágenes desde URLs
- Caché automático
- Integración nativa con Compose

**Usado en `HomeScreen.kt` y otras pantallas:**
```kotlin
AsyncImage(
    model = producto.imagen,
    contentDescription = producto.nombre,
    modifier = Modifier.size(150.dp)
)
```

#### 6. **DataStore** (Persistencia)
- Almacenamiento local de preferencias
- Reemplazo moderno de SharedPreferences
- Usado para guardar tokens JWT

### Librerías del Proyecto (`build.gradle`)

```kotlin
dependencies {
    // Compose
    implementation "androidx.compose.ui:ui:1.5.4"
    implementation "androidx.compose.material3:material3:1.1.2"

    // Navigation
    implementation "androidx.navigation:navigation-compose:2.7.6"

    // ViewModel
    implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0"

    // Retrofit (HTTP)
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-gson:2.9.0"

    // Coil (Imágenes)
    implementation "io.coil-kt:coil-compose:2.5.0"

    // DataStore
    implementation "androidx.datastore:datastore-preferences:1.0.0"
}
```

---

## 🎬 DEMOSTRACIÓN PRÁCTICA (5-6 min)

### 1. PANTALLA DE INICIO - HomeScreen.kt (1 min)

**Componentes principales:**

#### a) Carrusel MOWI (Auto-scroll)
```kotlin
// Archivo: HomeScreen.kt (línea ~356)
@Composable
fun MowiCarousel() {
    val pagerState = rememberPagerState(pageCount = { 3 })

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)  // Cambia cada 3 segundos
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % 3)
        }
    }
    // HorizontalPager con 3 slides
}
```

**Mostrar:**
- Carrusel con 3 slides: Bienvenida, Ofertas, Envío Gratis
- Auto-scroll cada 3 segundos
- Indicadores (puntitos) naranjas

#### b) Barra de Búsqueda
```kotlin
// Archivo: HomeScreen.kt (línea ~146)
OutlinedTextField(
    value = searchQuery,
    onValueChange = { searchQuery = it },
    placeholder = { Text("Buscar productos...") },
    leadingIcon = { Icon(Icons.Default.Search, ...) }
)
```

**Demostrar:**
- Buscar "Smartphone" → Filtra productos en tiempo real

#### c) Menú Hamburguesa (Drawer de Categorías)
```kotlin
// Archivo: HomeScreen.kt (línea ~278)
ModalNavigationDrawer(
    drawerContent = {
        DrawerContent(
            categories = categories,
            onCategorySelected = { /* Filtrar */ }
        )
    }
)
```

**Mostrar:**
- Click en ☰ → Se abre drawer lateral
- Categorías: Electrónica, Moda, Deportes, etc.
- Click en categoría → Filtra productos

#### d) Grid de Productos
```kotlin
// Archivo: HomeScreen.kt (línea ~472)
@Composable
fun ProductCard(producto: Producto) {
    Card {
        AsyncImage(model = producto.imagen, ...)
        Text(producto.nombre)
        Text(producto.precioFormateado(), color = OrangePrimary)
        // Badge "¡Últimos!" si stock < 10
    }
}
```

**Destacar:**
- Cards con sombras y bordes redondeados
- Badge naranja "¡Últimos!" cuando stock es bajo
- Precios en naranja vibrante (#FF6B35)
- Imágenes cargadas con Coil desde URLs

---

### 2. DETALLE DE PRODUCTO - ProductDetailScreen.kt (1 min)

**Archivo:** `ProductDetailScreen.kt`

#### Componentes:

**a) ViewModel para cargar datos**
```kotlin
// Archivo: ProductViewModel.kt (línea ~52)
fun loadProductDetail(productId: Int) {
    viewModelScope.launch {
        val response = apiService.getProducto(productId)
        _productDetailState.value = ProductDetailUiState.Success(response.body()!!)
    }
}
```

**b) Selector de Cantidad**
```kotlin
// Archivo: ProductDetailScreen.kt (línea ~186)
Row {
    OutlinedButton(onClick = { if (quantity > 1) quantity-- }) {
        Icon(Icons.Default.Clear)  // Botón -
    }
    Text(quantity.toString())
    OutlinedButton(onClick = { if (quantity < stock) quantity++ }) {
        Icon(Icons.Default.Add)    // Botón +
    }
}
```

**Demostrar:**
- Ver imagen grande, descripción completa
- Cambiar cantidad con +/-
- Stock disponible mostrado
- Botón "Agregar al Carrito"

**c) Validación de Login**
```kotlin
// Archivo: ProductDetailScreen.kt (línea ~238)
Button(onClick = {
    if (isAuthenticated) {
        cartViewModel.addToCart(producto, quantity)
        showAddedToCartDialog = true
    } else {
        showLoginDialog = true  // Pide login
    }
})
```

---

### 3. LOGIN - LoginScreen.kt (30 seg)

**Archivo:** `LoginScreen.kt` + `AuthViewModel.kt`

**Demostrar:**
- Email: `cliente@mowi.com`
- Password: `cliente123`
- Click "Iniciar Sesión"

**Proceso de autenticación:**
```kotlin
// Archivo: AuthViewModel.kt
fun login(email: String, password: String) {
    viewModelScope.launch {
        val response = apiService.login(LoginRequest(email, password))
        // Guardar token JWT en DataStore
        saveAuthToken(response.accessToken)
        _isLoggedIn.value = true
    }
}
```

---

### 4. AGREGAR AL CARRITO (30 seg)

**Pop-up después de agregar:**
```kotlin
// Archivo: ProductDetailScreen.kt (línea ~284)
AlertDialog(
    title = { Text("¡Producto Agregado!") },
    confirmButton = {
        Button(onClick = { navController.navigate(Routes.Cart.route) }) {
            Text("Ir a Comprar")
        }
    },
    dismissButton = {
        TextButton(onClick = { showDialog = false }) {
            Text("Seguir Comprando")
        }
    }
)
```

**Demostrar:**
- Agregar producto
- Elegir "Ir a Comprar" → Va a CartScreen

---

### 5. CARRITO - CartScreen.kt (1 min)

**Archivo:** `CartScreen.kt`

#### ViewModel Compartido
```kotlin
// Archivo: NavGraph.kt (línea ~34)
val cartViewModel: CartViewModel = viewModel()  // Una ÚNICA instancia

// Pasada a todas las pantallas:
HomeScreen(cartViewModel = cartViewModel)
ProductDetailScreen(cartViewModel = cartViewModel)
CartScreen(cartViewModel = cartViewModel)
CheckoutScreen(cartViewModel = cartViewModel)
```

**Destacar:**
- TODAS las pantallas comparten el MISMO CartViewModel
- Solución al problema de sincronización de datos
- Estado centralizado

#### Funcionalidades del Carrito
```kotlin
// Archivo: CartViewModel.kt
class CartViewModel : ViewModel() {
    fun addToCart(producto: Producto, cantidad: Int) { ... }
    fun updateQuantity(productId: Int, cantidad: Int) { ... }
    fun removeFromCart(productId: Int) { ... }
    fun clearCart() { ... }
}
```

**Demostrar:**
- Ver lista de productos agregados
- Cambiar cantidad de un producto
- Eliminar producto (icono 🗑️)
- Ver total actualizado automáticamente
- Badge en TopBar muestra cantidad total de items

---

### 6. CHECKOUT - CheckoutScreen.kt (1 min)

**Archivo:** `CheckoutScreen.kt`

#### Formulario de Entrega
```kotlin
// Línea ~143
OutlinedTextField(
    value = direccion,
    label = { Text("Dirección de Entrega") },
    leadingIcon = { Icon(Icons.Default.LocationOn) }
)
OutlinedTextField(
    value = telefono,
    label = { Text("Teléfono de Contacto") },
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
)
```

#### Pop-up de Tarjeta
```kotlin
// Archivo: CheckoutScreen.kt (línea ~286)
AlertDialog(
    title = { Text("Datos de Tarjeta") },
    text = {
        Column {
            OutlinedTextField(
                value = cardNumber,
                label = { Text("Número de Tarjeta") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            // Titular, Fecha, CVV...
        }
    }
)
```

**Validaciones:**
- Número de tarjeta: 16 dígitos
- CVV: 3 dígitos
- No permite confirmar sin datos completos

**Demostrar:**
- Llenar dirección y teléfono
- Seleccionar "Tarjeta de Crédito"
- Se abre pop-up automáticamente
- Llenar datos de tarjeta
- Guardar → Muestra `•••• •••• •••• 1234`
- Confirmar Pedido → Éxito

---

### 7. PERFIL - ProfileScreen.kt (1 min)

**Archivo:** `ProfileScreen.kt`

#### Header con Gradiente
```kotlin
// Línea ~98
Box(
    modifier = Modifier.background(
        Brush.horizontalGradient(
            colors = listOf(OrangePrimary, OrangeAccent)
        )
    )
) {
    // Avatar con iniciales
    Text(text = "JP",  // Juan Pérez
         fontSize = 36.sp,
         color = Color.White)
}
```

**Mostrar:**
- Avatar circular con iniciales del usuario
- Nombre: Juan Pérez
- Email: cliente@mowi.com
- Miembro desde: Enero 2024

#### Historial de Pedidos
```kotlin
// Archivo: ProfileScreen.kt (línea ~260)
@Composable
fun PedidoCard(pedido: Pedido) {
    Card {
        Text("Pedido #${pedido.id}")
        Text(formatearFecha(pedido.fechaPedido))
        EstadoBadge(estado = pedido.estado)  // Badge colorido
        Text(pedido.totalFormateado())
    }
}
```

**Estados de Pedido (con colores):**
```kotlin
// Archivo: ProfileScreen.kt (línea ~310)
@Composable
fun EstadoBadge(estado: EstadoPedido) {
    when (estado) {
        PENDIENTE   -> Badge(color = Orange, text = "Pendiente")
        EN_PROCESO  -> Badge(color = Blue, text = "En Proceso")
        ENVIADO     -> Badge(color = Teal, text = "Enviado")
        ENTREGADO   -> Badge(color = Green, text = "Entregado")
        CANCELADO   -> Badge(color = Red, text = "Cancelado")
    }
}
```

**Demostrar:**
- Scroll en historial
- 3 pedidos de ejemplo con diferentes estados
- Click en un pedido

---

### 8. DETALLE DE PEDIDO - OrderDetailScreen.kt (45 seg)

**Archivo:** `OrderDetailScreen.kt`

**Mostrar:**
- Estado del pedido con badge
- Fecha y hora
- Método de pago con ícono
- Lista de productos con imágenes
- Resumen con subtotales
- **TOTAL** en naranja grande

```kotlin
// Archivo: OrderDetailScreen.kt (línea ~233)
Text(
    text = pedido.totalFormateado(),  // "S/ 299.98"
    fontSize = 24.sp,
    fontWeight = FontWeight.ExtraBold,
    color = OrangePrimary  // #FF6B35
)
```

---

## 🎨 DISEÑO Y TEMA (2 min)

### Tema Personalizado - Color.kt & Theme.kt

**Archivo:** `ui/theme/Color.kt`

```kotlin
// Paleta de colores naranja MOWI
val OrangePrimary = Color(0xFFFF6B35)  // Naranja vibrante
val OrangeLight = Color(0xFFFF8C61)    // Naranja claro
val OrangeDark = Color(0xFFE85A2C)     // Naranja oscuro
val OrangeAccent = Color(0xFFFFB84D)   // Amarillo-naranja
```

**Archivo:** `ui/theme/Theme.kt`

```kotlin
private val LightColorScheme = lightColorScheme(
    primary = OrangePrimary,           // Color principal
    primaryContainer = OrangeLight,     // Containers
    secondary = OrangeAccent,           // Acentos
    background = BackgroundLight,       // Fondo cálido
    surface = SurfaceWhite,             // Cards
    // ... más colores
)
```

**Aplicación del tema:**
```kotlin
// Archivo: Theme.kt (línea ~80)
MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
)
```

### Material Design 3

**Componentes usados:**
- **Cards** con `elevation` y `RoundedCornerShape`
- **TopAppBar** con colores personalizados
- **NavigationDrawer** para categorías
- **AlertDialog** para pop-ups
- **OutlinedTextField** para formularios
- **Badge** para contador del carrito
- **FilterChip** para categoría activa

**Ejemplo de Card profesional:**
```kotlin
// Archivo: HomeScreen.kt (línea ~477)
Card(
    modifier = Modifier.shadow(6.dp, RoundedCornerShape(16.dp)),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface
    )
) { /* Contenido */ }
```

---

## 💡 ASPECTOS TÉCNICOS DESTACADOS (3 min)

### 1. Arquitectura MVVM

**Beneficios:**
✅ **Separación de responsabilidades** - UI no conoce la fuente de datos
✅ **Testeable** - ViewModel se puede probar sin UI
✅ **Sobrevive cambios de configuración** - Rotación de pantalla
✅ **Reactivo** - UI se actualiza automáticamente con StateFlow

**Ejemplo completo:**
```kotlin
// ========== MODEL ==========
// Archivo: data/model/Producto.kt
data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val imagen: String
)

// ========== VIEWMODEL ==========
// Archivo: ui/viewmodel/ProductViewModel.kt
class ProductViewModel : ViewModel() {
    private val _productsState = MutableStateFlow<ProductsUiState>(Loading)
    val productsState: StateFlow<ProductsUiState> = _productsState.asStateFlow()

    fun loadProducts() {
        viewModelScope.launch {
            val productos = apiService.getProductos()
            _productsState.value = Success(productos)
        }
    }
}

// ========== VIEW ==========
// Archivo: ui/screens/user/HomeScreen.kt
@Composable
fun HomeScreen(viewModel: ProductViewModel = viewModel()) {
    val productsState by viewModel.productsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProducts()  // Cargar al iniciar
    }

    when (productsState) {
        is Loading -> CircularProgressIndicator()
        is Success -> ProductGrid(productsState.products)
        is Error -> ErrorMessage()
    }
}
```

### 2. Navigation Type-Safe

**Archivo:** `navigation/Routes.kt`

```kotlin
sealed class Routes(val route: String) {
    object Home : Routes("home")

    object ProductDetail : Routes("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
}
```

**Uso:**
```kotlin
// ❌ FORMA INSEGURA (puede tener errores)
navController.navigate("product_detail/" + producto.id)

// ✅ FORMA SEGURA (type-safe)
navController.navigate(Routes.ProductDetail.createRoute(producto.id))
```

### 3. Estado Compartido (Shared ViewModel)

**Problema:** Si cada pantalla crea su propio `CartViewModel`, tienen estados diferentes.

**Solución:**
```kotlin
// Archivo: navigation/NavGraph.kt (línea ~34)
@Composable
fun NavGraph(...) {
    val cartViewModel: CartViewModel = viewModel()  // UNA sola instancia

    NavHost(...) {
        composable(Routes.Home.route) {
            HomeScreen(cartViewModel = cartViewModel)  // Misma instancia
        }
        composable(Routes.Cart.route) {
            CartScreen(cartViewModel = cartViewModel)  // Misma instancia
        }
        composable(Routes.Checkout.route) {
            CheckoutScreen(cartViewModel = cartViewModel)  // Misma instancia
        }
    }
}
```

**Resultado:** Todas las pantallas ven y modifican el mismo carrito.

### 4. Comunicación con API REST

**Archivo:** `data/remote/RetrofitClient.kt`

```kotlin
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"  // Emulador

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        }
        .build()

    val apiService: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}
```

**Archivo:** `data/remote/ApiService.kt`

```kotlin
interface ApiService {
    @GET("api/productos")
    suspend fun getProductos(): Response<List<Producto>>

    @GET("api/productos/{id}")
    suspend fun getProducto(@Path("id") id: Int): Response<Producto>

    @POST("api/login/")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
}
```

### 5. Carga de Imágenes con Coil

**Ventajas sobre otras librerías:**
- Nativo para Compose
- Ligero y rápido
- Caché automático (memoria + disco)
- Soporte para placeholders y error states

**Uso en toda la app:**
```kotlin
// Archivo: HomeScreen.kt, CartScreen.kt, etc.
AsyncImage(
    model = producto.imagen,  // URL de la imagen
    contentDescription = producto.nombre,
    modifier = Modifier.size(150.dp).clip(RoundedCornerShape(12.dp)),
    contentScale = ContentScale.Crop,
    placeholder = painterResource(R.drawable.placeholder),
    error = painterResource(R.drawable.error)
)
```

---

## 🎯 PUNTOS CLAVE PARA MENCIONAR

### Durante la Demo

1. **Al mostrar HomeScreen:**
   > "El archivo `HomeScreen.kt` contiene el carrusel MOWI que cambia automáticamente cada 3 segundos usando `LaunchedEffect` y `HorizontalPager`"

2. **Al filtrar productos:**
   > "El filtrado es reactivo gracias a `remember` y `derivedStateOf`, que recalcula la lista filtrada automáticamente cuando cambia la búsqueda o categoría"

3. **Al agregar al carrito:**
   > "El `CartViewModel.kt` gestiona todo el estado del carrito. Uso `StateFlow` para que la UI se actualice automáticamente cuando se agregan o modifican items"

4. **Al mostrar el badge del carrito:**
   > "El badge muestra el contador gracias al `CartViewModel` compartido definido en `NavGraph.kt`, que es la misma instancia para todas las pantallas"

5. **Al abrir checkout:**
   > "El pop-up de tarjeta en `CheckoutScreen.kt` tiene validaciones: solo acepta 16 dígitos para el número de tarjeta, 3 para CVV, y no permite confirmar sin datos completos"

6. **Al ver el perfil:**
   > "El avatar en `ProfileScreen.kt` genera las iniciales automáticamente del nombre del usuario usando `split()` y `firstOrNull()`. El gradiente usa `Brush.horizontalGradient` con los colores naranja de nuestro tema"

7. **Al ver pedidos:**
   > "Los badges de estado en `EstadoBadge` composable usan diferentes colores según el `EstadoPedido` enum definido en `Pedido.kt`"

---

## 📚 ARCHIVOS IMPORTANTES A MENCIONAR

### Core Files (Mencionar siempre)

| Archivo | Propósito | Línea Clave |
|---------|-----------|-------------|
| `MainActivity.kt` | Punto de entrada de la app | - |
| `NavGraph.kt` | Define toda la navegación | 34 (CartViewModel compartido) |
| `Routes.kt` | Rutas type-safe | - |

### ViewModels (Lógica de negocio)

| Archivo | Responsabilidad |
|---------|----------------|
| `ProductViewModel.kt` | Cargar lista y detalle de productos |
| `CartViewModel.kt` | Gestionar carrito de compras |
| `AuthViewModel.kt` | Autenticación y sesión |

### Screens (UI)

| Archivo | Pantalla |
|---------|----------|
| `HomeScreen.kt` | Inicio con carrusel y productos |
| `ProductDetailScreen.kt` | Detalle con selector de cantidad |
| `CartScreen.kt` | Carrito con edición |
| `CheckoutScreen.kt` | Checkout con pop-up de tarjeta |
| `ProfileScreen.kt` | Perfil con historial |
| `OrderDetailScreen.kt` | Detalle de pedido |

### Models (Datos)

| Archivo | Propósito |
|---------|-----------|
| `Producto.kt` | Modelo de producto |
| `CartItem.kt` | Item del carrito |
| `User.kt` | Usuario y autenticación |
| `Pedido.kt` | Pedido y estados |

### Theme (Diseño)

| Archivo | Contenido |
|---------|-----------|
| `Color.kt` | Paleta naranja MOWI |
| `Theme.kt` | Configuración Material Design 3 |

### Networking

| Archivo | Función |
|---------|---------|
| `RetrofitClient.kt` | Cliente HTTP |
| `ApiService.kt` | Endpoints |

---

## ✅ CHECKLIST PRE-EXPOSICIÓN

- [ ] Servidores corriendo (backend)
- [ ] App instalada y actualizada
- [ ] Emulador/dispositivo funcionando
- [ ] Conexión a Internet
- [ ] Conoces rutas de archivos clave
- [ ] Puedes navegar rápido al código
- [ ] Credenciales de prueba a mano

---

## 🎤 SCRIPT DE PRESENTACIÓN

### Apertura (30 seg)

> "Buenos días/tardes. Les presento **MOWI Market**, una aplicación móvil de e-commerce desarrollada nativamente para Android."
>
> "Está construida con **Kotlin** y **Jetpack Compose**, siguiendo el patrón de arquitectura **MVVM** para separar la UI de la lógica de negocio."
>
> "La app permite explorar productos sin login, agregarlos al carrito, realizar compras con diferentes métodos de pago, y gestionar un perfil con historial de pedidos."

### Durante la Demo

**Al mostrar carrusel:**
> "En `HomeScreen.kt` implementé un carrusel con auto-scroll usando `HorizontalPager` y `LaunchedEffect`"

**Al filtrar:**
> "El filtrado es reactivo - se actualiza automáticamente gracias a `remember` con las dependencias correctas"

**Al agregar al carrito:**
> "`CartViewModel.kt` usa `StateFlow` para notificar a la UI cuando cambia el carrito"

**Al mostrar compartido:**
> "Una sola instancia de `CartViewModel` se crea en `NavGraph.kt` línea 34 y se pasa a todas las pantallas que lo necesitan"

**Al mostrar tema:**
> "Los colores están definidos en `Color.kt` - naranja vibrante #FF6B35 pensado para atraer a usuarios jóvenes"

### Cierre (30 seg)

> "En resumen, MOWI Market es una app móvil completa que implementa:"
> - ✅ Arquitectura MVVM profesional
> - ✅ UI moderna con Jetpack Compose
> - ✅ Navegación type-safe
> - ✅ Estado compartido eficiente
> - ✅ Diseño Material Design 3 personalizado
>
> "Todo el código está organizado por capas: UI, ViewModels, Models y Networking, facilitando el mantenimiento y escalabilidad."

---

## 🎯 CREDENCIALES DE PRUEBA

```
Email: cliente@mowi.com
Password: cliente123
```

---

## ⏱️ TIMING SUGERIDO

| Sección | Tiempo |
|---------|--------|
| Introducción | 1-2 min |
| Arquitectura | 2-3 min |
| Demo completa | 5-6 min |
| Aspectos técnicos | 2-3 min |
| Preguntas | 2-3 min |
| **TOTAL** | **12-17 min** |

---

**¡ÉXITO EN TU EXPOSICIÓN! 🚀**
**MOWI Market - E-commerce Móvil Profesional**
