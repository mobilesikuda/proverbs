package ru.sikuda.mobile.proverbs.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.sikuda.mobile.proverbs.data.ProverbDao
import ru.sikuda.mobile.proverbs.data.ProverbEntity


//val urlStringForLoading = "http://django.t9163188.beget.tech/catalogs/api?format=json"

@Serializable
data class ProverbAPI(val id: Int, val name: String, val title: String)

suspend fun loadAllData(proverbDao: ProverbDao) {

    withContext(Dispatchers.IO) {

        if (proverbDao.size() == 0) {

            var listProverbs = mutableListOf<ProverbEntity>()
            try {
                proverbDao.deleteAll()

                val okHttpClient = OkHttpClient()
                val request = Request.Builder().url("https://sikuda.ru/spark/catalogs.json").build()

                okHttpClient.newCall(request).execute()
                    .use { response ->
                        println("GET Request Status Code: " + response.message)
                        //println("GET Request Body: " + response.body?.string())
                        val str:String = response.body?.string()?.trimIndent() ?: "[]"
                        val listLoaded: List<ProverbAPI> = Json.decodeFromString(str)
                        listLoaded.forEach {
                            listProverbs.add(
                                ProverbEntity(
                                    it.name,
                                    it.title,
                                    it.id
                                )
                            )
                        }
                    }

                //val jsonString = String(Res.readBytes("files/data.json"))
                //val listLoaded =Json.decodeFromString<List<ProverbAPI>>(jsonString)

            } catch (e :Exception) {
                listProverbs = listOf(
                    ProverbEntity(
                        "А воз и ныне там.",
                        "(Цитата из басни И. А. Крылова. Смысл поговорки в том, " +
                                "что не смотря на все разговоры и обещания по какому-либо делу, ничего кроме болтовни не сделано.)",
                        1
                    ),
                    ProverbEntity(
                        e.hashCode().toString(),
                        e.toString(),
                        1
                    )
                ) as MutableList<ProverbEntity>
            }
            proverbDao.upsertAll(listProverbs)
        }
    }
}
