package com.example.mvvmtemplate.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.example.mvvmtemplate.data.model.Product
import com.example.mvvmtemplate.presentation.viewmodel.HomeViewModel
import com.example.mvvmtemplate.ui.theme.PurpleGrey40
import com.example.mvvmtemplate.ui.theme.nudeColr
import com.example.mvvmtemplate.ui.theme.powderPinkColor

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        HomeScreenContent()
    }
}

@Composable
fun HomeScreenContent(viewModel: HomeViewModel = hiltViewModel()) {
    val productsResponse = viewModel.productsResponse.collectAsState()
    val products = productsResponse.value?.products ?: emptyList()

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize().padding(8.dp).background(color = MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(products.size) { index ->
            ProductCard(product = products[index])
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    val navigator = LocalNavigator.currentOrThrow
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                navigator.push(ProductDetailsScreen(product.id))
            }
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = powderPinkColor // Use nude color for the card background
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.thumbnail),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.title,
                fontWeight = FontWeight.Bold,
                color = PurpleGrey40 // Use powder pink color for the text
            )
            Text(
                text = product.description,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
            Text(
                text = "$${product.price}",
                color = nudeColr // Use powder pink color for the text
            )
            Text(
                text = "Rating: ${product.rating}",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}


