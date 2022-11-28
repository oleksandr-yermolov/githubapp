package com.oyermolov.githubapp.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.oyermolov.githubapp.data.database.entities.DatabaseRepositoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class LocalDataSourceTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var localDataSource: GithubLocalDataSource

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        localDataSource = GithubLocalDataSourceImpl(database.repositoriesDao())
    }

    @Test
    fun insertRepoAndGetByOwnerName() = runBlocking {
        // GIVEN
        val entity = DatabaseRepositoryEntity(22L, "TestLocalDataSource", "Description", "User")
        localDataSource.saveRepos(listOf(entity))

        // WHEN
        val loaded = localDataSource.getRepos(entity.ownerName).first()

        // THEN
        assertThat(loaded as? DatabaseRepositoryEntity, notNullValue())
        assertThat(loaded.id, `is`(entity.id))
        assertThat(loaded.name, `is`(entity.name))
        assertThat(loaded.description, `is`(entity.description))
        assertThat(loaded.ownerName, `is`(entity.ownerName))
    }

    @After
    fun closeDb() {
        database.close()
    }
}