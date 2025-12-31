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
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.List
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
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.petros.efthymiou.dailypulse.articles.presentation.ArticlesState
import com.petros.efthymiou.dailypulse.articles.presentation.EmptyArticlesState
import com.petros.efthymiou.dailypulse.articles.presentation.ErrorArticlesState
import com.petros.efthymiou.dailypulse.articles.presentation.LoadingArticlesState
import com.petros.efthymiou.dailypulse.articles.domain.Article
import com.petros.efthymiou.dailypulse.articles.presentation.ArticlesViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ArticlesScreen(
    onAboutButtonClick: () -> Unit,
    onSourcesButtonClick: () -> Unit,
    articlesViewModel: ArticlesViewModel = getViewModel(),
) {
    val articlesState = articlesViewModel.articlesState.collectAsState()
    val onSwipeRefresh: () -> Unit = { articlesViewModel.getArticles(true) }

    Column {
        AppBar(onAboutButtonClick, onSourcesButtonClick)
        when (articlesState.value) {
            is ErrorArticlesState -> ErrorMessage(articlesState.value.error!!)
            is EmptyArticlesState -> ErrorMessage("Empty Article Found")
            else ->
                ArticlesListView(
                    articlesState = articlesState.value,
                    onSwipeRefresh = onSwipeRefresh,
                )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    onAboutButtonClick: () -> Unit,
    onSourcesButtonClick: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "Articles") },
        actions = {
            IconButton(onClick = onSourcesButtonClick) {
                Icon(
                    imageVector = Icons.Outlined.List,
                    contentDescription = "Sources Button",
                )
            }
            IconButton(onClick = onAboutButtonClick) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "About Device Button",
                )
            }
        }
    )
}

@Composable
fun ArticlesListView(
    articlesState: ArticlesState,
    onSwipeRefresh: () -> Unit = {},
) {
    val swipeRefreshState = SwipeRefreshState(articlesState is LoadingArticlesState)
    SwipeRefresh(state = swipeRefreshState, onRefresh = onSwipeRefresh) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = articlesState.articles) { article ->
                ArticleItemView(article = article)
            }
        }
    }
}

@Composable
fun ArticleItemView(article: Article) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = article.title,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = article.description)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = article.date,
            style = TextStyle(color = Color.Gray),
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
private fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = TextStyle(fontSize = 28.sp, textAlign = TextAlign.Center)
        )
    }
}
