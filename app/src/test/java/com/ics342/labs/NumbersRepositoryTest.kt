package com.ics342.labs

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.util.UUID
import kotlin.random.Random
import io.mockk.just
import io.mockk.runs

internal class NumbersRepositoryTest {
    // Test case 1
    @Test
    fun `If database does not have a number fetch it from the Api`() {
        // Setup
        // Mocking the Database and Api interfaces
        val database = mockk<Database>()
        val api = mockk<Api>()
        // Creating a Number object with a random UUID and integer
        val number = Number(UUID.randomUUID().toString(), Random.nextInt())
        val id = number.id

        // Defining behavior for the mocked database: return null when getNumber is called
        every { database.getNumber(id) } returns null
        // Defining behavior for the mocked API: return the created number when getNumber is called
        every { api.getNumber(id) } returns number

        // Act
        // Creating the repository object using the mocked database and API
        val repository = NumbersRepository(database, api)
        val result = repository.getNumber(id)

        // Assert
        // Asserting that the result is not null and equals the created number
        assertNotNull(result)
        assertEquals(result, number)

        // Verifying that the getNumber methods were called on the mocked objects
        verify { database.getNumber(id) }
        verify { api.getNumber(id) }
    }
    // Test case 2
    @Test
    fun ifDatabaseIsEmptyShouldFetchNumbersFromApi() {
        // TODO: implement this test
        // Mocking the Database and Api interfaces again
        val database = mockk<Database>()
        // Creates a mock object for the Api
        val api = mockk<Api>()
        // Creating another Number object with a random UUID and integer
        val number = Number(UUID.randomUUID().toString(), Random.nextInt())
        // Extracts the ID from the number object
        val id = number.id

        // Defining behavior for the mocked database: return an empty list
        // when getAllNumbers is called
        every { database.getAllNumbers() } returns listOf()
        // Defining behavior for the mocked API: return a list with the created number
        // when getNumbers is called
        every { api.getNumbers() } returns listOf(number)
        // Defining behavior for the mocked database: do nothing when storeNumbers is called
        every {database.storeNumbers(listOf(number))} just runs

        // Creating the repository object using the mocked database and API again
        val repository = NumbersRepository(database, api)
        val result = repository.fetchNumbers()

        // Asserting that the result is not null and equals a list with the created number
        assertNotNull(result)
        assertEquals(listOf(number), repository.fetchNumbers())

        // Verifying that the getAllNumbers and
        // getNumbers methods were called on the mocked objects
        verify { database.getAllNumbers() }
        verify { api.getNumbers() }

    }
}
