package com.example.rickandmorty.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.rickandmorty.CharactersViewModel
import com.example.rickandmorty.dataClasses.CharactersResponse

@Composable
fun CharacterCard(
    viewModel: CharactersViewModel,
    character: CharactersResponse,
    onShowDetailInfo: (Int) -> Unit,
) {
    val errorMessage by viewModel.errorMessage.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(5.dp, RoundedCornerShape(24.dp))
            .border(1.dp, Color(0xFF5D00FF), RoundedCornerShape(24.dp))
            .clickable { onShowDetailInfo(character.id) },
        backgroundColor = Color(0xFFF6F4FC),
        shape = RoundedCornerShape(24.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = character.name, style = MaterialTheme.typography.titleLarge)
            Text(text = "Статус: ${character.status}")
            Text(text = "Вид: ${character.species}")
            Text(text = "Происхождение: ${character.origin.name}")
            Text(text = "Местоположение: ${character.location.name}")

            if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "",
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }

    }
}
