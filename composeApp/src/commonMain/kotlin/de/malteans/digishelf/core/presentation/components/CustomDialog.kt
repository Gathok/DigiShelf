package de.malteans.digishelf.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDialog(
    onDismissRequest: () -> Unit,
    title: @Composable () -> Unit,
    leftIcon: @Composable RowScope.() -> Unit = {},
    rightIcon: @Composable RowScope.() -> Unit = {},
    properties: DialogProperties = DialogProperties(),
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        Box (
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 0.dp,
                        bottom = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
            ) {
                Row {
                    CenterAlignedTopAppBar(
                        title = title,
                        navigationIcon = { Row { leftIcon() } },
                        actions = rightIcon
                    )
                }
                content()
            }
        }
    }
}