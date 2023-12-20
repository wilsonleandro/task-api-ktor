package br.com.task.data.repository

import br.com.task.core.domain.data.repository.CustomerRepository
import br.com.task.core.domain.model.Customer
import br.com.task.utils.Constants.CUSTOMERS_COLLECTION
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class CustomerRepositoryImpl(
    database: CoroutineDatabase
) : CustomerRepository {

    private val customersCollection = database.getCollection<Customer>(CUSTOMERS_COLLECTION)

    override suspend fun insert(customer: Customer): Boolean =
        customersCollection.insertOne(customer).wasAcknowledged()

    override suspend fun checkIfCustomerExists(email: String): Boolean {
        return (customersCollection.findOne(Customer::email eq email) != null)
    }

    override suspend fun getCustomerByEmail(email: String): Customer? {
        return customersCollection.findOne(Customer::email eq email)
    }
}