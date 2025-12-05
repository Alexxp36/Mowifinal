package com.miempresa.mowimarket.ui.screens.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.miempresa.mowimarket.data.model.DetallePedido
import com.miempresa.mowimarket.data.model.EstadoPedido
import com.miempresa.mowimarket.data.model.MetodoPago
import com.miempresa.mowimarket.data.model.Pedido
import com.miempresa.mowimarket.data.model.Producto
import com.miempresa.mowimarket.ui.theme.OrangePrimary
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    orderId: Int,
    navController: NavController
) {
    // Datos de ejemplo (en una implementación real vendrían del ViewModel)
    val pedido = remember {
        Pedido(
            id = orderId,
            usuarioId = 1,
            total = 299.98,
            estado = EstadoPedido.EN_PROCESO,
            metodoPago = MetodoPago.TARJETA,
            detalles = listOf(
                DetallePedido(
                    id = 1,
                    pedidoId = orderId,
                    producto = Producto(
                        id = 1,
                        nombre = "Smartphone Galaxy Pro X",
                        descripcion = "Smartphone de última generación",
                        categoria = null,
                        precio = 899.99,
                        stock = 45,
                        vendidos = 127,
                        imagen = "https://images.unsplash.com/photo-1511707171634-5f897ff02aa9",
                        activo = true
                    ),
                    productoId = 1,
                    cantidad = 2,
                    precioUnitario = 899.99,
                    subtotal = 1799.98
                ),
                DetallePedido(
                    id = 2,
                    pedidoId = orderId,
                    producto = Producto(
                        id = 3,
                        nombre = "Auriculares SoundMax Pro",
                        descripcion = "Auriculares con cancelación de ruido",
                        categoria = null,
                        precio = 249.99,
                        stock = 78,
                        vendidos = 234,
                        imagen = "https://images.unsplash.com/photo-1505740420928-5e560c06d30e",
                        activo = true
                    ),
                    productoId = 3,
                    cantidad = 1,
                    precioUnitario = 249.99,
                    subtotal = 249.99
                )
            ),
            fechaPedido = "2024-11-20T15:45:00",
            fechaActualizacion = "2024-11-21T09:00:00"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pedido #${orderId}", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Estado del pedido
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Estado del Pedido",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            EstadoBadge(estado = pedido.estado)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.DateRange,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = formatearFecha(pedido.fechaPedido),
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            // Información de pago
            item {
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Información de Pago",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                when (pedido.metodoPago) {
                                    MetodoPago.TARJETA -> Icons.Default.AccountBox
                                    MetodoPago.YAPE -> Icons.Default.Phone
                                    MetodoPago.TRANSFERENCIA -> Icons.Default.AccountBalance
                                },
                                contentDescription = null,
                                tint = OrangePrimary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = when (pedido.metodoPago) {
                                    MetodoPago.TARJETA -> "Tarjeta de Crédito/Débito"
                                    MetodoPago.YAPE -> "Yape"
                                    MetodoPago.TRANSFERENCIA -> "Transferencia Bancaria"
                                },
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            // Productos del pedido
            item {
                Text(
                    text = "Productos (${pedido.detalles.size})",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(pedido.detalles) { detalle ->
                DetalleProductoCard(detalle = detalle)
            }

            // Resumen del pedido
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Resumen del Pedido",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        pedido.detalles.forEach { detalle ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "${detalle.cantidad}x ${detalle.producto.nombre}",
                                    modifier = Modifier.weight(1f),
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "S/ %.2f".format(detalle.subtotal),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Divider()

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Total",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = pedido.totalFormateado(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = OrangePrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetalleProductoCard(detalle: DetallePedido) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = detalle.producto.imagen,
                contentDescription = detalle.producto.nombre,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = detalle.producto.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Cantidad: ${detalle.cantidad}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "S/ %.2f c/u".format(detalle.precioUnitario),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "S/ %.2f".format(detalle.subtotal),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = OrangePrimary
                    )
                }
            }
        }
    }
}

private fun formatearFecha(fechaISO: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("es", "ES"))
        val date = inputFormat.parse(fechaISO)
        date?.let { outputFormat.format(it) } ?: fechaISO
    } catch (e: Exception) {
        fechaISO
    }
}
