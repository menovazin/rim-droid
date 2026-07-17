package com.rim.droid.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.SubcomposeAsyncImage
import com.rim.droid.presentation.theme.rimColors
import com.smarttoolfactory.zoom.enhancedZoom
import com.smarttoolfactory.zoom.rememberEnhancedZoomState
import kotlinx.coroutines.launch

private const val MIN_ZOOM = 1f
private const val MAX_ZOOM = 3f
private const val ZOOM_ACTIVE_THRESHOLD = 1.01f

/**
 * Coil-backed network image with transient pinch-to-zoom (Compose-Zoom).
 *
 * At rest applies [clipShape]; while visually zoomed, does not clip so content can draw over siblings.
 * On gesture end animates back to identity via [com.smarttoolfactory.zoom.EnhancedZoomState.onDoubleTap]
 * (library reset API — not a user double-tap). Double-tap does not cycle zoom levels.
 *
 * [onZoomActiveChange] notifies parents (e.g. list cards) so they can raise [Modifier.zIndex] above
 * LazyGrid neighbors while a zoom gesture is active or scale is elevated.
 *
 * [onClick] is handled inside the zoom gesture layer (parent [Modifier.clickable] does not receive
 * taps on the image because [enhancedZoom] owns pointer input).
 */
@Composable
fun ZoomableNetworkImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    clipShape: Shape = RoundedCornerShape(12.dp),
    onClick: (() -> Unit)? = null,
    onZoomActiveChange: ((Boolean) -> Unit)? = null,
) {
    val rimColors = MaterialTheme.rimColors
    val scope = rememberCoroutineScope()
    var layoutSize by remember { mutableStateOf(IntSize.Zero) }
    // Elevated for draw order while gesture runs or scale > 1 (including reset animation).
    var isElevated by remember { mutableStateOf(false) }
    // Rest clip only when scale is at identity (not merely between pointer events).
    var isVisuallyZoomed by remember { mutableStateOf(false) }

    fun setElevated(active: Boolean) {
        if (isElevated != active) {
            isElevated = active
            onZoomActiveChange?.invoke(active)
        }
    }

    val imageSize = if (layoutSize.width > 0 && layoutSize.height > 0) {
        layoutSize
    } else {
        IntSize(1, 1)
    }

    val enhancedZoomState = rememberEnhancedZoomState(
        imageSize = imageSize,
        minZoom = MIN_ZOOM,
        maxZoom = MAX_ZOOM,
        fling = false,
        moveToBounds = false,
        limitPan = false,
        rotatable = false,
        key1 = imageSize.width,
        key2 = imageSize.height,
    )

    Box(
        modifier = modifier
            .onSizeChanged { layoutSize = it }
            .zIndex(if (isElevated) 1f else 0f)
            .then(if (isVisuallyZoomed) Modifier else Modifier.clip(clipShape))
            .enhancedZoom(
                clip = false,
                enhancedZoomState = enhancedZoomState,
                // Pan/consume gated to 2+ pointers in forked enhancedZoom; keep enabled true for multi-touch pan.
                enabled = { _, _, _ -> true },
                zoomOnDoubleTap = { MIN_ZOOM },
                onGestureStart = {
                    isVisuallyZoomed = true
                    setElevated(true)
                },
                onGesture = { data ->
                    isVisuallyZoomed = true
                    setElevated(true)
                },
                onGestureEnd = {
                    // Explicit identity reset; moveToBounds is not a full product reset.
                    scope.launch {
                        enhancedZoomState.onDoubleTap(zoom = MIN_ZOOM) {
                            isVisuallyZoomed = false
                            setElevated(false)
                        }
                    }
                },
                onTap = onClick,
            ),
    ) {
        SubcomposeAsyncImage(
            model = model,
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = Modifier.fillMaxSize(),
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(rimColors.surface),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = rimColors.primary,
                    )
                }
            },
            error = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(rimColors.surface),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.BrokenImage,
                        contentDescription = null,
                        tint = rimColors.textSecondary,
                    )
                }
            },
        )
    }
}
