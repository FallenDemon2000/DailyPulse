package com.petros.efthymiou.dailypulse.android.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petros.efthymiou.dailypulse.sources.domain.Source
import com.petros.efthymiou.dailypulse.sources.presentation.EmptySourcesState
import com.petros.efthymiou.dailypulse.sources.presentation.ErrorSourcesState
import com.petros.efthymiou.dailypulse.sources.presentation.SourcesState
import com.petros.efthymiou.dailypulse.sources.presentation.SourcesViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun SourcesScreen(
    onUpButtonClick: () -> Unit,
    sourcesViewModel: SourcesViewModel = getViewModel(),
) {
    val sourcesState = sourcesViewModel.sourcesState.collectAsState()

    Column {
        Toolbar(onUpButtonClick)
        when (sourcesState.value) {
            is ErrorSourcesState -> ErrorMessage(sourcesState.value.error!!, Modifier.fillMaxSize())
            is EmptySourcesState -> ErrorMessage("No Sources Available", Modifier.fillMaxSize())
            else -> SourcesListView(
                sourcesState = sourcesState.value,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    onUpButtonClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "Sources") },
        navigationIcon = {
            IconButton(onClick = onUpButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Up Button",
                )
            }
        },
    )
}

@Composable
private fun SourcesListView(
    sourcesState: SourcesState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(items = sourcesState.sources) { source ->
            SourceItemView(
                source = source,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
            )
        }
    }
}

@Composable
fun SourceItemView(source: Source, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = source.name,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = source.description)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = source.origin,
            style = TextStyle(color = Color.Gray),
            modifier = Modifier.align(Alignment.End),
        )
    }
}

@Composable
private fun ErrorMessage(message: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = message,
            style = TextStyle(fontSize = 28.sp, textAlign = TextAlign.Center),
        )
    }
}
