package com.example.charlaayacucho.feature.favorites.ui

import android.os.Bundle
import android.telecom.Call.Details
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.charlaayacucho.feature.favorites.viewmodel.FavoriteViewModel
import com.google.accompanist.themeadapter.material3.Mdc3Theme
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage


class FavoritesFragment : Fragment() {
    private val viewModel: FavoriteViewModel by viewModels { FavoriteViewModel.Factory }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val id = arguments?.getString("id")
        return ComposeView(requireContext()).apply {
            setContent {
                Mdc3Theme {
                    setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                    //viewModel.fetchMovies(id!!)
                    DetailTwo(viewModel)
                }

            }
        }
    }
}

@Preview
@Composable
fun DetailTwo(viewModel: FavoriteViewModel = viewModel()) {
    val value by viewModel.movie.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState()
    val movies by viewModel.movies.observeAsState()
    if (isLoading == true) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    LazyVerticalGrid(contentPadding = PaddingValues(16.dp),
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            items(movies.orEmpty()) {
                Item(image = it.image)
            }
        })
}


@Composable
fun Item(image: String) {
    Box(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .height(200.dp),
            contentScale = ContentScale.Crop,
        )
    }

}

//@Preview
@Composable
fun Detail(viewModel: FavoriteViewModel = viewModel()) {
    val value by viewModel.movie.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState()
    val movies by viewModel.movies.observeAsState()
    Column(
        Modifier
            .width(200.dp)
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading == true) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(25.dp)
                        .width(25.dp)
                )
            }
            AsyncImage(
                model = value?.image,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
            )
        }
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value?.title.orEmpty(),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}