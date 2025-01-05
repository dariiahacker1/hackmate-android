package com.teslasoft.hackerapp.data

import com.teslasoft.hackerapp.R

data class Tool (
    val iconResId:Int = 0,
    val name:String = ""
)


object ToolsList{

    val Tools = listOf(
        Tool(
            iconResId = R.drawable.baseline_newspaper_24,
            name = "Security News"
        ),
        Tool(
            iconResId = R.drawable.baseline_key_24,
            name = "Password Strength Analyzer/Strong Password Generator"
        ),
        Tool(
            iconResId = R.drawable.baseline_library_books_24,
            name = "Cybersecurity Terms Dictionary"
        ),
        Tool(
            iconResId = R.drawable.baseline_lock_outline_24,
            name = "AES-256 Encryption"
        ),
        Tool(
            iconResId = R.drawable.baseline_network_check_24,
            name = "Network Scanner"
        ),
        Tool(
            iconResId = R.drawable.baseline_qr_code_2_24,
            name = "QR Code Generator"
        )

    )

}