package com.example.rickandmorty.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickandmorty.CharactersViewModel

@Composable
fun CharacterDetailScreen(
    viewModel: CharactersViewModel,
    onBackListCharacter: () -> Unit
    ) {
    val character by viewModel.currentCharacter.collectAsState()

    character?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    IconButton(onClick = { onBackListCharacter() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color(0xFF5D00FF)
                        )
                    }
                    Text(
                        text = "Назад к списку",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF5D00FF)
                    )
                }

                // Название
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Изображение
                AsyncImage(
                    model = it.image,
                    contentDescription = it.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(2.dp, Color(0xFF5D00FF), RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(24.dp))

//                CharacterInfoItem(label = "ID", value = it.id.toString())
                CharacterInfoItem(label = "Статус", value = it.status)
                CharacterInfoItem(label = "Вид", value = it.species)
                CharacterInfoItem(label = "Тип", value = if (it.type.isNotBlank()) it.type else "—")
                CharacterInfoItem(label = "Пол", value = it.gender)
                CharacterInfoItem(label = "Происхождение", value = it.origin.name)
                CharacterInfoItem(label = "Местоположение", value = it.location.name)
//                CharacterInfoItem(label = "Ссылка", value = it.url)
                CharacterInfoItem(label = "Создан", value = it.created)

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Эпизоды (${it.episode.size}):",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF5D00FF),
                    modifier = Modifier.padding(top = 8.dp)
                )

                it.episode.forEachIndexed { index, epUrl ->
                    Text(
                        text = "${index + 1}. $epUrl",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                    )
                }
            }
        }
    } ?: run {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Загрузка персонажа...", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
