package ru.sikuda.mobile.proverbs.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ProverbDao {
         /**
         * Observes list of proverbs.
         *
         * @return all tasks.
         */
        @Query("SELECT * FROM catalogProverbs")
        fun observeAll(): Flow<List<ProverbEntity>>

        /**
         * Observes a single task.
         *
         * @param uid the proverb id.
         * @return the task with taskId.
         */
        @Query("SELECT * FROM catalogProverbs WHERE uid = :uid")
        fun observeById(uid: Int): Flow<ProverbEntity>

        /**
         * Select all proverbs from the catalog table.
         *
         * @return all tasks.
         */
        @Query("SELECT * FROM catalogProverbs")
        fun getAll(): Flow<List<ProverbEntity>>

        /**
         * Select a task by id.
         *
         * @param uid the proverb id.
         * @return the proverb with id.
         */
        @Query("SELECT * FROM catalogProverbs WHERE uid = :uid")
        suspend fun getById(uid: Int): ProverbEntity?

        /**
         * Insert or update a task in the database. If a task already exists, replace it.
         *
         * @param proverb the task to be inserted or updated.
         */
        @Upsert
        suspend fun upsert(proverb: ProverbEntity)

        /**
         * Insert or update proverbs in the database. If a task already exists, replace it.
         *
         * @param proverbs the tasks to be inserted or updated.
         */
        @Upsert
        suspend fun upsertAll(proverbs: List<ProverbEntity>)

         /**
         * Delete a task by id.
         *
         * @return the number of tasks deleted. This should always be 1.
         */
        @Query("DELETE FROM catalogProverbs WHERE uid = :uid")
        suspend fun deleteById(uid: Int): Int

        /**
         * Delete all tasks.
         */
        @Query("DELETE FROM catalogProverbs")
        suspend fun deleteAll()

        @Query("SELECT COUNT(*) FROM catalogProverbs")
        suspend fun size(): Int
}