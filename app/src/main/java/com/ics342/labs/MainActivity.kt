// Lab 6:
// Practice deserialization from Json with Moshi.
// The data class is defined, JSON data is loaded from the resource file,
// deserialized using Moshi, and displayed using LazyColumn.
// Written by Benjamin Cassidy
// Modified by Yeliz Konduk
// 7/14/2023

package com.ics342.labs

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.ics342.labs.ui.theme.LabsTheme
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val jsonData = loadData(resources)
        val data = dataFromJsonString(jsonData)
        setContent {
            LabsTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LazyColumn{
                        items(data){person ->
                            Column {
                                Text(text = person.id.toString())
                                Text(text = person.giveName)
                                Text(text = person.familyName)
                                Text(text = person.age.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun loadData(resources: Resources): String {
    return resources
        .openRawResource(R.raw.data)
        .bufferedReader()
        .use { it.readText() }
}

private fun dataFromJsonString(json: String): List<Person> {
    val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val listType = Types.newParameterizedType(List::class.java, Person::class.java)
    val jsonAdapter: JsonAdapter<List<Person>> = moshi.adapter(listType)
    return jsonAdapter.fromJson(json) ?: listOf()
}


