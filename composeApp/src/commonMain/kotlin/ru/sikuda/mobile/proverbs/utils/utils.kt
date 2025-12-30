package ru.sikuda.mobile.proverbs.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import proverbs.composeapp.generated.resources.Res
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
                val jsonString = String(Res.readBytes("files/data.json"))
                val listLoaded =Json.decodeFromString<List<ProverbAPI>>(jsonString)
                listLoaded.forEach {
                    listProverbs.add(
                        ProverbEntity(
                            it.name,
                            it.title,
                            it.id
                        )
                    )
                }
            } catch (e :Exception) {
                listProverbs = listOf<ProverbEntity>(
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
