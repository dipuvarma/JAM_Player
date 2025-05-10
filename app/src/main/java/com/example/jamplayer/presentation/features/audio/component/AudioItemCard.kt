package com.example.jamplayer.presentation.features.audio.component

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jamplayer.R
import com.example.jamplayer.presentation.common.model.AudioItemUi


@Composable
fun AudioItemCard(
    audioItemUi: AudioItemUi,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onClick(audioItemUi.data) },
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AudioThumbnailCard(audioItemUi.thumbnail?.toUri() ?: "".toUri())
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = audioItemUi.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Duration: ${audioItemUi.duration}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "Format: Mp3",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun AudioThumbnailCard(
    uri: Uri,
) {
    val context = LocalContext.current

    // Load bitmap only once per composition
    val albumArtBitmap = remember(uri) {
        try {
            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(context, uri)
            val art = retriever.embeddedPicture
            retriever.release()

            art?.let {
                BitmapFactory.decodeByteArray(it, 0, it.size)
            }
        } catch (e: Exception) {
            null
        }
    }

    Box(
        modifier = Modifier
            .size(80.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
    ) {
        GlideImage(
            model = albumArtBitmap ?: R.drawable.ic_launcher_background,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize()
        )
    }
}


