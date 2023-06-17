package com.lamti.thechase.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.submitForm
import io.ktor.http.parameters

const val url = "http://192.168.1.101:8080"
const val version = "/v1"

const val usersEndpoint = "/users"
const val loginEndpoint = "/login"

class Repository(private val client: HttpClient = ClientBuilder.defaultHttpClient) {

    private var token = ""

    suspend fun login(email: String, password: String): String {
        val url = "$url$version$usersEndpoint$loginEndpoint"
        var apiToken = ""

        try {
            apiToken = client.submitForm(
                url = url,
                formParameters = parameters {
                    append("email", email)
                    append("password", password)
                }
            ).body()

            token = apiToken
        } catch (e: Exception) {
            Log.d("LOGIN", "Error: ${e.localizedMessage}")
        }

        return apiToken
    }
}
