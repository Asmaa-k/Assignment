package com.asmaa.khb.filterapp.realmTest

import com.asmaa.khb.filterapp.models.RealmCategoryObject
import com.asmaa.khb.filterapp.models.CategoryObject
import com.google.gson.Gson
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class RealmTest {

    private lateinit var realm: Realm
    lateinit var categoryObject: CategoryObject

    // This will run before each test method
    @Before
    fun prepareRealmConfig() {
        val config = RealmConfiguration.Builder(setOf(RealmCategoryObject::class))
            .name("test.realm")
            .inMemory()
            .build()
        realm = Realm.open(config)
    }

    @Before
    fun parseJsonString() {
        val gson = Gson()
        val jsonString =
            "{\"label_en\":\"Cars and Bikes\",\"id\":1775,\"reporting_name\":\"Cars and Bikes\"}"

        //extracting object from file
        categoryObject = gson.fromJson(jsonString, CategoryObject::class.java)
    }

    @After
    fun tearDown() {
        realm.close()
    }

    @Test
    fun saveRealmObjectTest() = runBlocking {
        //map from api to realm
        val sampleObject =
            RealmCategoryObject(
                id = categoryObject.id,
                label = categoryObject.label,
                reportingName = categoryObject.reportingName
            )

        realm.write {
            copyToRealm(sampleObject)
        }

        val result = realm.query<RealmCategoryObject>("id == $0", sampleObject.id).first().find()


        assertNotNull(result, "Saved object should not be null")
        assertEquals(sampleObject.id, result.id, "id should match")
        assertEquals(sampleObject.label, result.label, "label should match")
        assertEquals(sampleObject.reportingName, result.reportingName, "reportingName should match")
    }


    @org.junit.Test
    fun testReadingJsonFromAssetsFolder() {

        val jsonString = "{\"label_en\":\"Cars and Bikes\",\"id\":1775,\"reporting_name\":\"Cars and Bikes\"}"

        val gson = Gson()
        // Parse JSON into Category object
        val category = gson.fromJson(jsonString, CategoryObject::class.java)

        // Assert values to make sure the object was parsed correctly
        Assert.assertEquals(1775, category.id)
        Assert.assertEquals("Cars and Bikes", category.label)
        Assert.assertEquals("Cars and Bikes", category.reportingName)
    }
}
