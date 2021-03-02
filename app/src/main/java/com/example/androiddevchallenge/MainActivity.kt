/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.models.Shiba
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    val shibas: List<Shiba> = listOf(
        Shiba("Kotaro", 5, R.drawable.kota, "Tokyo, Japan", 0),
        Shiba("Fukuchan", 1, R.drawable.fuku, "Chiba, Japan", 1),
        Shiba("Mametaro", 2, R.drawable.mame, "Fukuoka, Japan", 2),
        Shiba("Daiちゃん", 5, R.drawable.dai, "Osaka, Japan", 3),
        Shiba("Hanaちゃん", 8, R.drawable.hana, "Gifu, Japan", 4),
        Shiba("Sakura", 16, R.drawable.sakura, "Nagoya, Japan", 5),
        Shiba("Tsumoji", 6, R.drawable.tsumoji, "Moon, Space", 6),
        Shiba("Yuri", 12, R.drawable.yuri, "Nagano, Japan", 7),
        Shiba("Yumopiyo", 9, R.drawable.yumopiyo, "Nagoya, Japan", 8),
        Shiba("Shiroi", 5, R.drawable.kota, "Aomori, Japan", 9),
        Shiba("Melonpan", 1, R.drawable.fuku, "Sapporo, Japan", 10),
        Shiba("Taiyaki", 2, R.drawable.mame, "Kawaguchiko, Japan", 11),
        Shiba("Smol Bean", 5, R.drawable.dai, "Hakone, Japan", 12),
        Shiba("Mofupiyo", 8, R.drawable.hana, "Morioka, Japan", 13),
        Shiba("Penちゃん", 16, R.drawable.sakura, "Akita, Japan", 14),
        Shiba("Wanちゃん", 6, R.drawable.tsumoji, "Nikko, Japan", 15),
        Shiba("Yuki", 12, R.drawable.yuri, "Fukushima, Japan", 16),
        Shiba("Kuu", 9, R.drawable.yumopiyo, "Kobe, Japan", 17)
    )
    val navController = rememberNavController()

    NavHost(navController, startDestination = "list") {
        composable("list") {
            Surface(color = MaterialTheme.colors.background) {
                ShibaList(shibas, navController)
            }
        }
        composable(
            "details/{index}",
            arguments = listOf(
                navArgument("index") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            Surface(color = MaterialTheme.colors.background) {
                backStackEntry.arguments?.getInt("index")?.let { DetailsPage(it, shibas) }
            }
        }
    }
}

@Composable
fun ShibaList(shibas: List<Shiba>, navController: NavController) {
    LazyColumn {
        item {
            Text(text = "Shibadoption", style = typography.h3, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
            Text(text = "わんわん！🐶", style = typography.subtitle1, modifier = Modifier.padding(start = 16.dp, bottom = 16.dp))
        }
        items(shibas) { shiba ->
            ShibaRow(shiba, navController)
        }
    }
}

@Composable
fun ShibaRow(shiba: Shiba, navController: NavController) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("details/${shiba.id}") },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = shiba.img),
            contentDescription = "${shiba.name} shiba's photo",
            modifier = Modifier
                .height(56.dp)
                .width(56.dp)
                .clip(shape = RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(start = 8.dp)) {
            Text(text = shiba.name, style = typography.h6)
            Text(text = "${shiba.age} MONTH${if (shiba.age > 1) "S" else ""}", style = typography.body2)
        }
        IconButton(
            onClick = { navController.navigate("details/${shiba.id}") },
            modifier = Modifier.height(36.dp),
        ) {
            Text(text = ">")
        }
    }
}

@Composable
fun DetailsPage(index: Int, shibas: List<Shiba>) {
    val shiba = shibas[index]
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        val context = LocalContext.current

        Image(
            painter = painterResource(id = shiba.img),
            contentDescription = shiba.name,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(16.dp))
        Text(
            shiba.name,
            style = typography.h6,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = "${shiba.age} months old from ${shiba.location}",
            style = typography.body1
        )
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
                    .putExtra(Intent.EXTRA_EMAIL, arrayOf("hello@izadi.xyz"))
                    .putExtra(Intent.EXTRA_SUBJECT, "i want to shibadopt ${shiba.name}!")
                    .putExtra(Intent.EXTRA_TEXT, "the doggo is so cute 🥺, i want to adopt him, i promise i will take good care uwu.")
                startActivity(context, intent, null)
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Adopt")
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }
}
