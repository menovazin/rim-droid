package com.rim.droid.presentation.theme

import androidx.compose.ui.graphics.Color
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RimDetailGradientTest {

    private val primary = Color(0xFF34E27A)
    private val secondary = Color(0xFF9B53D6)
    private val surface = Color(0xFF16272B)
    private val background = Color(0xFF0E1B1F)

    @Test
    fun `stop at t=0 is opaque and greener than surface for episode accent`() {
        val stop = rimDetailHeroGradientStop(0f, primary, surface, background)

        assertThat(stop.alpha).isEqualTo(1f)
        assertThat(stop.green).isGreaterThan(surface.green)
        assertThat(stop.green).isGreaterThan(background.green)
    }

    @Test
    fun `stop at t=1 path is pure surface when caller uses solid end`() {
        // Helper end stop is surface; stop(1) composites fully opaque surface over bg
        val stop = rimDetailHeroGradientStop(1f, primary, surface, background)

        assertThat(stop.alpha).isEqualTo(1f)
        assertThat(stop.red).isWithin(0.001f).of(surface.red)
        assertThat(stop.green).isWithin(0.001f).of(surface.green)
        assertThat(stop.blue).isWithin(0.001f).of(surface.blue)
    }

    @Test
    fun `stop at t=0 composites accent at 0_15 over background`() {
        val stop = rimDetailHeroGradientStop(0f, primary, surface, background)
        val expected = Color(
            red = primary.red * 0.15f + background.red * 0.85f,
            green = primary.green * 0.15f + background.green * 0.85f,
            blue = primary.blue * 0.15f + background.blue * 0.85f,
            alpha = 1f,
        )

        assertThat(stop.red).isWithin(0.001f).of(expected.red)
        assertThat(stop.green).isWithin(0.001f).of(expected.green)
        assertThat(stop.blue).isWithin(0.001f).of(expected.blue)
        assertThat(stop.alpha).isEqualTo(1f)
    }

    @Test
    fun `location secondary accent produces more blue-purple wash than surface`() {
        val stop = rimDetailHeroGradientStop(0f, secondary, surface, background)

        assertThat(stop.alpha).isEqualTo(1f)
        assertThat(stop.red).isGreaterThan(surface.red)
        assertThat(stop.blue).isGreaterThan(surface.blue)
    }

    @Test
    fun `mid stop green exceeds surface for episode accent`() {
        // Alpha rises with t while RGB lerps toward surface; mid wash stays above surface green
        val mid = rimDetailHeroGradientStop(0.5f, primary, surface, background)

        assertThat(mid.alpha).isEqualTo(1f)
        assertThat(mid.green).isGreaterThan(surface.green)
        // Spec acceptance: mid-left glow stronger than flat surface (G ~0.3+)
        assertThat(mid.green).isGreaterThan(0.28f)
    }
}
