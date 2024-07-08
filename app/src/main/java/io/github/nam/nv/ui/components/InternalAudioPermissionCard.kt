package io.github.nam.nv.ui.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.takusan23.zeromirror.R

/**
 * 内部音声
 *
 * @param modifier [Modifier]
 * @param permissionResult trueが流れてきたら権限ゲットだぜ
 */
@Composable
fun InternalAudioPermissionCard(
    modifier: Modifier = Modifier,
    permissionResult: (Boolean) -> Unit,
) {

    /** 権限コールバック */
    val permissionRequest = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGrant ->
        permissionResult(isGrant)
    }

    OutlinedCard(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = stringResource(id = R.string.internal_audio_permission_card_title),
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                modifier = Modifier.padding(5.dp),
                text = stringResource(id = R.string.internal_audio_permission_card_description)
            )
            OutlinedButton(
                modifier = Modifier
                    .align(Alignment.End),
                onClick = { permissionRequest.launch(android.Manifest.permission.RECORD_AUDIO) }
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_outline_settings_24), contentDescription = null)
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(text = stringResource(id = R.string.internal_audio_permission_card_button))
            }
        }
    }
}