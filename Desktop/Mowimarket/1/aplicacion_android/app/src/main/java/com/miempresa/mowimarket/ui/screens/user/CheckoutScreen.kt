package com.miempresa.mowimarket.ui.screens.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.miempresa.mowimarket.navigation.Routes
import com.miempresa.mowimarket.ui.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    navController: NavController,
    cartViewModel: CartViewModel = viewModel()
) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val cartTotal by cartViewModel.cartTotal.collectAsState()

    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var notasAdicionales by remember { mutableStateOf("") }
    var metodoPago by remember { mutableStateOf("tarjeta") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finalizar Compra") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Resumen del pedido
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Resumen del Pedido",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            cartItems.forEach { item ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "${item.cantidad}x ${item.producto.nombre}",
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.weight(1f)
                                    )
                                    Text(
                                        text = item.subtotalFormateado(),
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                            }

                            Divider(modifier = Modifier.padding(vertical = 8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Total",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = cartViewModel.getTotalFormateado(),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }

                // Información de entrega
                item {
                    Text(
                        text = "Información de Entrega",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        label = { Text("Dirección de Entrega") },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false,
                        maxLines = 3
                    )
                }

                item {
                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { telefono = it },
                        label = { Text("Teléfono de Contacto") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                item {
                    OutlinedTextField(
                        value = notasAdicionales,
                        onValueChange = { notasAdicionales = it },
                        label = { Text("Notas Adicionales (Opcional)") },
                        leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = false,
                        maxLines = 3
                    )
                }

                // Método de pago
                item {
                    Text(
                        text = "Método de Pago",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            RadioButton(
                                selected = metodoPago == "tarjeta",
                                onClick = { metodoPago = "tarjeta" }
                            )
                            Icon(Icons.Default.AccountBox, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Tarjeta de Crédito/Débito")
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            RadioButton(
                                selected = metodoPago == "efectivo",
                                onClick = { metodoPago = "efectivo" }
                            )
                            Icon(Icons.Default.Call, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Pago contra entrega (Efectivo)")
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            RadioButton(
                                selected = metodoPago == "transferencia",
                                onClick = { metodoPago = "transferencia" }
                            )
                            Icon(Icons.Default.AccountBalance, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Transferencia Bancaria")
                        }
                    }
                }
            }

            // Botón de confirmar pedido
            Surface(
                tonalElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            // Aquí iría la lógica para procesar el pedido
                            showSuccessDialog = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        enabled = direccion.isNotBlank() && telefono.isNotBlank()
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Confirmar Pedido")
                    }
                }
            }
        }
    }

    // Dialog de éxito
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            icon = {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(64.dp)
                )
            },
            title = { Text("¡Pedido Confirmado!") },
            text = {
                Column {
                    Text("Tu pedido ha sido procesado exitosamente.")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Total: ${cartViewModel.getTotalFormateado()}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Método de pago: ${metodoPago.uppercase()}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Recibirás una confirmación por correo.")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        cartViewModel.clearCart()
                        navController.navigate(Routes.Home.route) {
                            popUpTo(Routes.Home.route) { inclusive = false }
                        }
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }
}
