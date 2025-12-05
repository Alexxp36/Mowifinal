package com.miempresa.mowimarket.ui.screens.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.miempresa.mowimarket.data.model.Categoria
import com.miempresa.mowimarket.data.model.Producto
import com.miempresa.mowimarket.navigation.Routes
import com.miempresa.mowimarket.ui.viewmodel.CartViewModel
import com.miempresa.mowimarket.ui.viewmodel.ProductViewModel
import com.miempresa.mowimarket.ui.viewmodel.ProductsUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    isAuthenticated: Boolean,
    onProfileClick: () -> Unit,
    viewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    // Cargar productos al iniciar
    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    val productsState by viewModel.productsState.collectAsState()
    val cartItemCount by cartViewModel.itemCount.collectAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<Categoria?>(null) }

    // Obtener categorías únicas de los productos
    val categories = remember(productsState) {
        if (productsState is ProductsUiState.Success) {
            (productsState as ProductsUiState.Success).products
                .mapNotNull { it.categoria }
                .distinctBy { it.id }
        } else {
            emptyList()
        }
    }

    // Filtrar productos por búsqueda y categoría
    val filteredProducts = remember(productsState, searchQuery, selectedCategory) {
        if (productsState is ProductsUiState.Success) {
            var products = (productsState as ProductsUiState.Success).products

            // Filtrar por búsqueda
            if (searchQuery.isNotBlank()) {
                products = products.filter {
                    it.nombre.contains(searchQuery, ignoreCase = true) ||
                    it.descripcion.contains(searchQuery, ignoreCase = true)
                }
            }

            // Filtrar por categoría
            selectedCategory?.let { cat ->
                products = products.filter { it.categoria?.id == cat.id }
            }

            products
        } else {
            emptyList()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { category ->
                        selectedCategory = category
                        scope.launch { drawerState.close() }
                    },
                    onClearFilter = {
                        selectedCategory = null
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                Column {
                    TopAppBar(
                        title = {
                            Text(
                                "MOWI Market",
                                fontWeight = FontWeight.Bold
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menú")
                            }
                        },
                        actions = {
                            BadgedBox(
                                badge = {
                                    if (cartItemCount > 0) {
                                        Badge { Text(cartItemCount.toString()) }
                                    }
                                }
                            ) {
                                IconButton(onClick = { navController.navigate(Routes.Cart.route) }) {
                                    Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                                }
                            }
                            IconButton(onClick = onProfileClick) {
                                Icon(
                                    if (isAuthenticated) Icons.Default.AccountCircle else Icons.Default.Person,
                                    contentDescription = "Perfil"
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )

                    // Barra de búsqueda
                    Surface(
                        tonalElevation = 3.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            placeholder = { Text("Buscar productos...") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(Icons.Default.Close, contentDescription = "Limpiar")
                                    }
                                }
                            },
                            singleLine = true,
                            shape = MaterialTheme.shapes.medium
                        )
                    }

                    // Chip de categoría seleccionada
                    selectedCategory?.let { category ->
                        Surface(
                            tonalElevation = 1.dp,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                FilterChip(
                                    selected = true,
                                    onClick = { selectedCategory = null },
                                    label = { Text(category.nombre) },
                                    trailingIcon = {
                                        Icon(Icons.Default.Close, contentDescription = "Quitar filtro")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        ) { padding ->
        when (val state = productsState) {
            is ProductsUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is ProductsUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Warning,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(state.message)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadProducts() }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
            is ProductsUiState.Success -> {
                if (filteredProducts.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "No se encontraron productos",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        items(filteredProducts) { producto ->
                            ProductCard(
                                producto = producto,
                                onClick = {
                                    navController.navigate(Routes.ProductDetail.createRoute(producto.id))
                                }
                            )
                        }
                    }
                }
            }
        }
        }
    }
}

@Composable
fun DrawerContent(
    categories: List<Categoria>,
    selectedCategory: Categoria?,
    onCategorySelected: (Categoria) -> Unit,
    onClearFilter: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Categorías",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Divider()

        Spacer(modifier = Modifier.height(8.dp))

        // Opción de ver todas las categorías
        NavigationDrawerItem(
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Todas las Categorías") },
            selected = selectedCategory == null,
            onClick = onClearFilter,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de categorías
        LazyColumn {
            items(categories) { category ->
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Star, contentDescription = null) },
                    label = { Text(category.nombre) },
                    selected = selectedCategory?.id == category.id,
                    onClick = { onCategorySelected(category) },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ProductCard(
    producto: Producto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Imagen del producto
            AsyncImage(
                model = producto.imagen,
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                // Nombre
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Precio
                Text(
                    text = producto.precioFormateado(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                // Stock
                if (producto.stock > 0) {
                    Text(
                        text = "Stock: ${producto.stock}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Text(
                        text = "Agotado",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
