package com.ioffeivan.feature.auth.presentation.email_verification.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ioffeivan.otp.core.model.OtpCell

@Composable
internal fun OtpItem(
    otpCell: OtpCell,
    modifier: Modifier = Modifier,
) {
    val borderColor =
        if (otpCell.isFocused) MaterialTheme.colorScheme.primary else Color.DarkGray
    val borderWidth = if (otpCell.isFocused) 2.dp else 1.dp

    if (otpCell.position != 1) {
        Spacer(modifier = Modifier.width(6.dp))
    }

    Box(
        modifier =
            modifier
                .height(64.dp)
                .border(
                    width = borderWidth,
                    color = borderColor,
                    shape = RoundedCornerShape(16.dp),
                ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = otpCell.value,
            style =
                MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                ),
        )
    }
}
