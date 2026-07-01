package com.rim.droid.presentation.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Science
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rim.droid.R
import com.rim.droid.presentation.theme.rimColors

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit,
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()
    val isSubmitting by viewModel.isSubmitting.collectAsStateWithLifecycle()
    var name by remember { mutableStateOf("") }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) onLoginSuccess()
    }

    val colors = MaterialTheme.rimColors

    Scaffold(
        containerColor = colors.background,
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Column(
                    modifier = Modifier
                        .widthIn(max = 420.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Science,
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(72.dp),
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Rick & Morty",
                        style = MaterialTheme.typography.headlineMedium,
                        color = colors.textPrimary,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.login_subtitle),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.textSecondary,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(stringResource(R.string.login_hint_name)) },
                        singleLine = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = null,
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = { viewModel.onLogin(name) },
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = colors.surface,
                            unfocusedContainerColor = colors.surface,
                            focusedTextColor = colors.textPrimary,
                            unfocusedTextColor = colors.textPrimary,
                            focusedBorderColor = colors.primary,
                            unfocusedBorderColor = colors.textSecondary.copy(alpha = 0.3f),
                            focusedLeadingIconColor = colors.textSecondary,
                            unfocusedLeadingIconColor = colors.textSecondary,
                            focusedLabelColor = colors.textSecondary,
                            unfocusedLabelColor = colors.textSecondary,
                            cursorColor = colors.primary,
                        ),
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { viewModel.onLogin(name) },
                        enabled = !isSubmitting,
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.primary,
                            contentColor = colors.onPrimary,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(22.dp),
                                strokeWidth = 2.dp,
                                color = colors.onPrimary,
                            )
                        } else {
                            Text(
                                text = stringResource(R.string.login_button),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                    }
                }
            }
        }
    }
}
