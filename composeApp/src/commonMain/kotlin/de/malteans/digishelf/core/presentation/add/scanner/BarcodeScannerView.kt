package de.malteans.digishelf.core.presentation.add.scanner

import androidx.compose.runtime.Composable

/**
 * A composable that shows a live camera view with an overlay and a flash toggle.
 * When a barcode (ISBN) is scanned successfully, it calls [onBarcodeScanned] with the raw value.
 */
@Composable
expect fun BarcodeScannerView(
    onBack: () -> Unit,
    onBarcodeScanned: (String?) -> Unit
)
