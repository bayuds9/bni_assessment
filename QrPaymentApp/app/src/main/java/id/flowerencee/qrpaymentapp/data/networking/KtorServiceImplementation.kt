package id.flowerencee.qrpaymentapp.data.networking

import io.ktor.client.HttpClient

class KtorServiceImplementation(
    private val client: HttpClient
) : KtorService {
    override suspend fun getClient(): HttpClient {
        return client
    }
}