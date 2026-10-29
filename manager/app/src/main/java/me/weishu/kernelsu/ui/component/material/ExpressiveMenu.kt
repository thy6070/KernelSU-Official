package me.weishu.kernelsu.ui.component.material

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset

@Composable
fun OffsetAnchoredExpressiveMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    anchorOffset: IntOffset = IntOffset.Zero,
    content: @Composable ColumnScope.() -> Unit,
) {
    Box(
        modifier = Modifier.layout { measurable, constraints ->
            val placeable = measurable.measure(Constraints())
            val width = if (constraints.hasBoundedWidth) constraints.maxWidth else 0
            layout(width, 0) {
                placeable.place(anchorOffset.x, anchorOffset.y)
            }
        }
    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
        ) {
            androidx.compose.foundation.layout.Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                content()
            }
        }
    }
}

fun Modifier.trackPressPosition(onPress: (Offset) -> Unit): Modifier = pointerInput(Unit) {
    awaitEachGesture {
        val down = awaitFirstDown(requireUnconsumed = false)
        onPress(down.position)
    }
}
