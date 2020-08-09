package com.project.zemoga_test.repository.post

import com.project.zemoga_test.repository.post.impl.PostMapperService
import com.project.zemoga_test.repository.post.impl.PostRepository
import com.project.zemoga_test.repository.post.model.Comment
import com.project.zemoga_test.repository.post.model.Post
import com.project.zemoga_test.services.api.RetrofitUtils
import com.project.zemoga_test.services.database.ZemogaTestDataBase
import io.mockk.mockk
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

class PostRepositoryTest {
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        RetrofitUtils.BASE_URL = mockWebServer.url("/").toString()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `repository returns domain objects after backend response`() {
//        Given
        val database: ZemogaTestDataBase = mockk()
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(POST_RESPONSE_AS_STRING)
        mockWebServer.enqueue(expectedResponse)
//        When
        val repository = PostRepository(PostMapperService(), database)
//        Then
        repository.getAllFromNetwork()
            .test()
            .awaitCount(1)
            .assertValue(
                listOf(
                    Post(
                        userId = 1,
                        id = 1,
                        title = "first",
                        body = "first body",
                        isFavorite = false
                    )
                )
            )
    }

    @Test
    fun `repository returns empty list when there are not post`() {
//        Given
        val database: ZemogaTestDataBase = mockk()
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody("")
        mockWebServer.enqueue(expectedResponse)
//        When
        val repository = PostRepository(PostMapperService(), database)
//        Then
        repository.getAllFromNetwork()
            .test()
            .awaitCount(1)
            .assertNoValues()
    }

    @Test
    fun `get comments by post id`() {
//        Given
        val database: ZemogaTestDataBase = mockk()
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(COMMENTS_AS_STRING)
        mockWebServer.enqueue(expectedResponse)
//        When
        val repository = PostRepository(PostMapperService(), database)
//        Then
        repository.getCommentsByPost(1)
            .test()
            .awaitCount(1)
            .assertValues(
                listOf(
                    Comment("laudantium enim quasi est quidem magnam voluptate ipsam eos\ntempora quo necessitatibus\ndolor quam autem quasi\nreiciendis et nam sapiente accusantium"),
                    Comment("est natus enim nihil est dolore omnis voluptatem numquam\net omnis occaecati quod ullam at\nvoluptatem error expedita pariatur\nnihil sint nostrum voluptatem reiciendis et")
                )
            )
    }

    companion object {
        const val POST_RESPONSE_AS_STRING = "[\n" +
                "  {\n" +
                "    \"userId\": 1,\n" +
                "    \"id\": 1,\n" +
                "    \"title\": \"first\",\n" +
                "    \"body\": \"first body\"\n" +
                "  }]"

        const val COMMENTS_AS_STRING = "[\n" +
                "  {\n" +
                "    \"postId\": 1,\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"id labore ex et quam laborum\",\n" +
                "    \"email\": \"Eliseo@gardner.biz\",\n" +
                "    \"body\": \"laudantium enim quasi est quidem magnam voluptate ipsam eos\\ntempora quo necessitatibus\\ndolor quam autem quasi\\nreiciendis et nam sapiente accusantium\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"postId\": 1,\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"quo vero reiciendis velit similique earum\",\n" +
                "    \"email\": \"Jayne_Kuhic@sydney.com\",\n" +
                "    \"body\": \"est natus enim nihil est dolore omnis voluptatem numquam\\net omnis occaecati quod ullam at\\nvoluptatem error expedita pariatur\\nnihil sint nostrum voluptatem reiciendis et\"\n" +
                "  }]"
    }

}