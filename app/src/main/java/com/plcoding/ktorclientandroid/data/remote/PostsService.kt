package com.plcoding.ktorclientandroid.data.remote

import com.plcoding.ktorclientandroid.data.remote.dto.PostRequest
import com.plcoding.ktorclientandroid.data.remote.dto.PostResponse
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

interface PostsService {

    suspend fun getPosts(): List<PostResponse>

    // postRequest - the data we serialize to JSON and send to the server
    suspend fun createPost(postRequest: PostRequest): PostResponse?

    companion object {
        fun create(): PostsService {
            return PostsServiceImpl(
                // Android - engine that will drive this client (different engines for different platforms)
                client = HttpClient(Android) {
                    // Specify features
                    install(Logging) {
                        // configure the login feature
                        // All show everything in logger
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        // we need to serialize and deserialize our data classes
                        // specify which serializer to use
                        serializer = KotlinxSerializer()
                    }
                }
            )
        }
    }
}