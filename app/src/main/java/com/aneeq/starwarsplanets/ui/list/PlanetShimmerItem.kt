package com.aneeq.starwarsplanets.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

@Composable
fun PlanetShimmerItem() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 3.dp,
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .placeholder(
                        visible = true,
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .alignByBaseline()
            ) {
                repeat(3) { i ->
                    Box(
                        modifier = Modifier
                            .height(16.dp)
                            .fillMaxWidth(0.5f + i * 0.25f)
                            .clip(RoundedCornerShape(6.dp))
                            .placeholder(
                                visible = true,
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                highlight = PlaceholderHighlight.shimmer()
                            )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }
    }
}
