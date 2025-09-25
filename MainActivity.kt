package com.example.appuirecreation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.appuirecreation.ui.theme.AppUIRecreationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppUIRecreationTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { CustomBottomNavigation(selectedTab) { selectedTab = it } }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CustomTopAppBar()
            MainContent(
                searchText = searchText,
                onSearchTextChange = { searchText = it }
            )
        }
    }
}

@Composable
fun CustomTopAppBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Hi, Tara!",
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "What are we whipping up?",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}

@Composable
fun MainContent(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Search Bar
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(25.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Search your library...",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Time Card
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Time",
                        fontSize = 16.sp
                    )
                }
            }
            
            // Ingredients Card
            Card(
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ingredients",
                        fontSize = 16.sp
                    )
                }
            }
        }

        // Folders Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Folders (11)",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "New",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
        
        // Recipe Folders Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(4) { index ->
                RecipeFolder(index = index)
            }
        }
    }
}

@Composable
fun RecipeFolder(index: Int) {
    // Simple folder names and counts
    val titles = listOf("Breakfast", "Dinner meals", "Lunch", "Snacks")
    val counts = listOf("8 recipes", "25 recipes", "12 recipes", "15 recipes")
    val images = listOf(R.drawable.pastries, R.drawable.steak, R.drawable.waffles, R.drawable.pastries)
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Round image
        Image(
            painter = painterResource(id = images[index]),
            contentDescription = titles[index],
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Title
        Text(
            text = titles[index],
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        
        // Recipe count
        Text(
            text = counts[index],
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}


@Composable
fun CustomBottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home, "🏠"),
        BottomNavItem("Learn", Icons.Default.Info, "💡"),
        BottomNavItem("Menu", Icons.Default.Menu, "📋"),
        BottomNavItem("Groceries", Icons.Default.ShoppingCart, "🛒"),
        BottomNavItem("Discover", Icons.Default.Search, "🔍")
    )
    
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Text(
                        text = item.emoji,
                        fontSize = 24.sp
                    )
                },
                label = { 
                    Text(item.title) 
                },
                selected = selectedTab == index,
                onClick = { onTabSelected(index) }
            )
        }
    }
}

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val emoji: String
)
