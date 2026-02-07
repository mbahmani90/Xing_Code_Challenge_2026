package com.cypress.xingcodechallengeapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [XingEntity::class , XingKeyEntity::class],
    version = 1
)
abstract class XingDataBase : RoomDatabase()  {

    abstract val xingDao : XingDao
    abstract val xingKeyDao: XingKeyDao

}