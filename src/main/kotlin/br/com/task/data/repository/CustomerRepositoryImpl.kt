package br.com.task.data.repository

import br.com.task.core.domain.data.repository.CustomerRepository
import br.com.task.core.domain.model.Customer
import br.com.task.utils.Constants.CUSTOMERS_COLLECTION
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull

class CustomerRepositoryImpl(
    database: MongoDatabase
) : CustomerRepository {

    private val customersCollection = database.getCollection<Customer>(CUSTOMERS_COLLECTION)

    override suspend fun insert(customer: Customer): Boolean =
        customersCollection.insertOne(customer).wasAcknowledged()

    override suspend fun checkIfCustomerExists(email: String): Boolean {
        val filter = Filters.eq(Customer::email.name, email)
        return (customersCollection.find(filter).firstOrNull() != null)
    }

    override suspend fun getCustomerByEmail(email: String): Customer? {
        return customersCollection.find(Filters.eq(Customer::email.name, email)).firstOrNull()
    }
}