package cat.deim.asm40.pedalean2.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.deim.asm40.pedalean2.R

/**
 * LoginScreen es la función composable que define la UI de la pantalla de login.
 * Sigue el patrón MVVM con StateFlow: observa el estado del ViewModel con collectAsState()
 * y reacciona a los cambios.
 *
 * @param viewModel el ViewModel que gestiona el estado y la lógica de login
 * @param onLoginSuccess callback que la Activity ejecuta cuando el login es exitoso
 */
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    // Observamos los StateFlow del ViewModel con collectAsState()
    // Cada vez que cambie el valor, Compose recompone automáticamente
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    // Reaccionamos a los cambios de estado de la operación de login
    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginUiState.Success -> {
                onLoginSuccess()
            }
            is LoginUiState.Error -> {
                val msg = (uiState as LoginUiState.Error).message
                snackbarHostState.showSnackbar(msg)
                viewModel.resetState()
            }
            else -> { /* Idle o Loading: no hace falta acción */ }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.p2_logo2),
                contentDescription = "Logo Pedalean2",
                modifier = Modifier
                    .height(180.dp)
                    .padding(bottom = 24.dp)
            )

            Text(
                text = stringResource(id = R.string.login_title),
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = { Text(stringResource(id = R.string.login_email_label)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = { Text(stringResource(id = R.string.login_password_label)) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Mostramos un indicador de carga mientras el login está en progreso
            if (uiState is LoginUiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(bottom = 16.dp),
                    color = Color(0xFFFFD700)
                )
            }

            Button(
                onClick = { viewModel.performLogin() },
                // Deshabilitado si los campos están vacíos O si ya hay una petición en curso
                enabled = viewModel.isLoginEnabled() && uiState !is LoginUiState.Loading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFD700),
                    contentColor = Color.Black,
                    disabledContainerColor = Color(0xFFFFD700).copy(alpha = 0.5f),
                    disabledContentColor = Color.Black.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(
                    text = stringResource(id = R.string.login_button_text),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
