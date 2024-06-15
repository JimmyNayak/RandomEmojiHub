package com.jn.randomemojihub.widget

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jn.randomemojihub.R
import com.jn.randomemojihub.viewmodel.RandomEmojiHubViewModel

/**
 * Fil description: Random emoji compose view
 *
 * Created on 15-06-2024.
 */

@Preview(name = "RandomEmojiHubView")
@Composable
fun RandomEmojiHubView() {

    val randomEmojiHubViewModel: RandomEmojiHubViewModel = viewModel()
    val randomEmoji = randomEmojiHubViewModel.randomEmoji.collectAsState().value
    val isLoading = randomEmojiHubViewModel.isLoading.collectAsState().value
    val error = randomEmojiHubViewModel.errorMessage.collectAsState().value

    LaunchedEffect(key1 = true) {
        randomEmojiHubViewModel.getRandomEmoji()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        } else if (error > 0) {
            Toast.makeText(LocalContext.current, stringResource(id = error), Toast.LENGTH_SHORT).show()
        } else {

            Text(
                HtmlCompat.fromHtml(
                    randomEmoji.htmlCode?.first().toString(), HtmlCompat.FROM_HTML_MODE_LEGACY
                ).toString(),
                fontSize = 250.sp, modifier = Modifier.padding(8.dp),
            )

            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)) {
                    append(randomEmoji.name)
                }

            }, modifier = Modifier.padding(8.dp))


            Button(modifier = Modifier.padding(18.dp), onClick = { randomEmojiHubViewModel.getRandomEmoji() }) {
                Text(stringResource(id = R.string.btn_refresh))
            }
        }
    }
}