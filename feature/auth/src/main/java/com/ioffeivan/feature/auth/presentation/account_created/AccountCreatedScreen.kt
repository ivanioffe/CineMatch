package com.ioffeivan.feature.auth.presentation.account_created

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ioffeivan.core.designsystem.component.PrimaryButton
import com.ioffeivan.core.designsystem.preview.PreviewContainer
import com.ioffeivan.core.ui.onDebounceClick
import com.ioffeivan.feature.auth.R as authR

@Composable
internal fun AccountCreatedScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
    ) {
        ImageLoginSuccess(
            modifier =
                Modifier
                    .size(175.dp),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(authR.string.account_created_title),
            style =
                MaterialTheme.typography.titleLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.W500,
                    textAlign = TextAlign.Center,
                ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(authR.string.account_created_description),
            style =
                MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center,
                ),
        )

        Spacer(modifier = Modifier.height(36.dp))

        PrimaryButton(
            text = stringResource(authR.string.log_in),
            onClick =
                onDebounceClick(onClick = onLoginClick),
            modifier =
                Modifier
                    .fillMaxWidth(),
        )
    }
}

@Composable
private fun ImageLoginSuccess(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(authR.raw.login_success),
    )

    LottieAnimation(
        composition = composition,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun AccountCreatedScreenPreview() {
    PreviewContainer {
        AccountCreatedScreen(
            onLoginClick = {},
        )
    }
}
