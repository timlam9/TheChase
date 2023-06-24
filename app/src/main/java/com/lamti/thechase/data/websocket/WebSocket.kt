package com.lamti.thechase.data.websocket

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.lamti.thechase.data.ClientBuilder
import com.lamti.thechase.data.models.ChaseSoundEvent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.header
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class WebSocket(
    private val client: HttpClient = ClientBuilder.defaultHttpClient,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val gson: Gson = Gson()
) {
    private var webSocketSession: DefaultClientWebSocketSession? = null
    private lateinit var socketConnectJob: CompletableJob

    private val _socketSoundsEventChannel: Channel<ChaseSoundEvent> = Channel()
    val socketSoundEvents: Flow<ChaseSoundEvent>
        get() = _socketSoundsEventChannel.receiveAsFlow().flowOn(dispatcher)

    fun openConnection(token: String, email: String) {
        socketConnectJob = Job()

        CoroutineScope(dispatcher + socketConnectJob).launch {
            supervisorScope {
                try {
                    client.webSocket(
                        method = HttpMethod.Get,
                        host = "192.168.1.101",
                        port = 8080,
                        path = "/thechase",
                        request = { header("Authorization", "Bearer $token") }
                    ) {
                        Log.d("SOCKET", "Connection opened.")
                        webSocketSession = this
                        sendMessage(SocketMessage.OutBound.Connect(email = email))

                        val messagesReceivedRoutine = launch {
                            observeSocketMessages()
                        }
                        messagesReceivedRoutine.join()
                    }
                } catch (e: Exception) {
                    Log.d("LOGIN", "Web socket error: ${e.localizedMessage}")
                }
            }
        }
    }

    suspend fun sendMessage(baseModel: SocketMessage.OutBound) {
        val message = gson.toJson(baseModel)
        webSocketSession?.send(Frame.Text(message))
    }

    fun closeWebSocket() {
        Log.d("SOCKET", "Connection closed.")
        client.close()
    }

    private suspend fun DefaultClientWebSocketSession.observeSocketMessages() {
        incoming.consumeEach { frame ->
            if (frame is Frame.Text) {
                val receivedText = frame.readText()

                observeSoundSocketEvents(receivedText)
                Log.d("SOCKET", "Frame text received: $receivedText")
            } else {
                Log.d("SOCKET", "- - - - - - - - - - > Unknown Message received: $frame")
            }
        }
    }

    private fun observeSoundSocketEvents(receivedText: String) {
        try {
            val jsonObject = JsonParser.parseString(receivedText).asJsonObject

            val type = when (jsonObject.get("type").asString) {
                "event" -> SocketMessage.InBound.Event::class.java
                else -> SocketMessage.InBound::class.java
            }

            val payload: SocketMessage.InBound = gson.fromJson(receivedText, type)
            println("- - - - - - - - - - > Message received: $payload")

            when (payload) {
                is SocketMessage.InBound.Event -> sendSocketSoundEvent(payload.event)
                is SocketMessage.InBound.SocketError -> TODO()
            }
        } catch (e: Exception) {
            println("- - - - - - - - - - > Message decode exception: ${e.localizedMessage}")
        }
    }

    private fun sendSocketSoundEvent(event: ChaseSoundEvent) {
        CoroutineScope(dispatcher).launch {
            _socketSoundsEventChannel.send(event)
        }
    }
}
