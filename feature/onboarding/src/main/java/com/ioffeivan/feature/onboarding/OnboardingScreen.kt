package com.ioffeivan.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ioffeivan.core.designsystem.component.PrimaryButton
import com.ioffeivan.core.designsystem.component.PrimaryOutlinedButton
import com.ioffeivan.core.designsystem.theme.Black50
import com.ioffeivan.core.designsystem.theme.CineMatchTheme
import com.ioffeivan.core.ui.R
import com.ioffeivan.feature.onboarding.R as onboardingR

@Composable
internal fun OnboardingRoute(
    onLoginButtonClick: () -> Unit,
    onSignupButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    OnboardingScreen(
        onLoginButtonClick = onLoginButtonClick,
        onSignupButtonClick = onSignupButtonClick,
        modifier = modifier,
    )
}

@Composable
internal fun OnboardingScreen(
    onLoginButtonClick: () -> Unit,
    onSignupButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(onboardingR.drawable.onboarding),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier =
                Modifier
                    .fillMaxSize(),
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .background(
                        color = Black50,
                        shape =
                            RoundedCornerShape(
                                topStart = 10.dp,
                                topEnd = 10.dp,
                            ),
                    )
                    .padding(
                        horizontal = 16.dp,
                        vertical = 24.dp,
                    ),
        ) {
            Text(
                text = stringResource(onboardingR.string.onboarding_title),
                textAlign = TextAlign.Center,
                style =
                    MaterialTheme.typography.titleMedium
                        .copy(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                        ),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(onboardingR.string.onboarding_description),
                textAlign = TextAlign.Center,
                style =
                    MaterialTheme.typography.bodyMedium
                        .copy(
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                        ),
            )

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryButton(
                text = stringResource(R.string.login),
                onClick = onLoginButtonClick,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .testTag("loginButton"),
            )

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryOutlinedButton(
                text = stringResource(R.string.signup),
                onClick = onSignupButtonClick,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .testTag("signupButton"),
            )
        }
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    CineMatchTheme {
        OnboardingScreen(
            onLoginButtonClick = {},
            onSignupButtonClick = {},
        )
    }
}
