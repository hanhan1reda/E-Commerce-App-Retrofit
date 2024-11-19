package com.example.mvvmtemplate.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.rememberAsyncImagePainter
import com.example.mvvmtemplate.data.model.Product
import com.example.mvvmtemplate.data.model.Review
import com.example.mvvmtemplate.presentation.viewmodel.HomeViewModel

class ProductDetailsScreen(val id: Int) : Screen {
    @Composable
    override fun Content() {
        val viewModel: HomeViewModel = hiltViewModel()
        viewModel.getProductById(id)
        val product = viewModel.product.collectAsState()
        ProductDetailsScreenContent(product = product.value)
    }
}

@Composable
fun ProductDetailsScreenContent(product: Product?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .background(Color(0xFFF5F5F5)) // Soft Light Gray background
    ) {
        // Product Image inside a Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(8.dp), // Adjust elevation for the card
            colors = CardDefaults.cardColors(containerColor = Color.White), // White background for the image card
            shape = MaterialTheme.shapes.medium // Rounded corners for the card
        ) {
            Image(
                painter = rememberAsyncImagePainter(product?.images?.firstOrNull()),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        // Product Title
        Text(
            text = "${product?.title}",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF7D5260), // Pink40 color for the title
            style = MaterialTheme.typography.headlineMedium, // Larger and more stylish font
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Product Price and Rating
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "$${product?.price}",
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF7D5260), // Pink40 color for the price
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Rating: ${product?.rating}",
                color = Color(0xFF7D5260), // Pink40 color for the rating
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        // Product Description
        Text(
            text = "${product?.description}",
            color = Color(0xFF625b71), // Silvery Gray for description
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Product Availability
        Text(
            text = "Availability: ${product?.availabilityStatus}",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF7D5260), // Pink40 color for availability
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Warranty and Shipping Information
        Text(
            text = "Warranty: ${product?.warrantyInformation}",
            color = Color(0xFF625b71), // Silvery Gray for warranty
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Shipping: ${product?.shippingInformation}",
            color = Color(0xFF625b71), // Silvery Gray for shipping
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Product Reviews
        if (product?.reviews?.isNotEmpty() == true) {
            Text(
                text = "Customer Reviews",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7D5260), // Pink40 color for reviews title
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            product.reviews.forEach { review ->
                ReviewCard(review)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ReviewCard(review: Review) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp), // Increased elevation for a more pronounced card
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF6D8E0)) // Pale Peach background for reviews
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Rating: ${review.rating}",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7D5260), // Pink40 color for rating
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = review.comment,
                color = Color(0xFF625b71), // Silvery Gray for comment
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Reviewed by: ${review.reviewerName}",
                color = Color(0xFF7D5260), // Pink40 color for reviewer name
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
