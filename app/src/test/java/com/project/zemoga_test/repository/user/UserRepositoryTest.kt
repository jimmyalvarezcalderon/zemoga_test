package com.project.zemoga_test.repository.user

import android.util.Log
import com.project.zemoga_test.repository.post.PostRepositoryTest
import com.project.zemoga_test.repository.post.impl.PostMapperService
import com.project.zemoga_test.repository.post.impl.PostRepository
import com.project.zemoga_test.repository.post.model.Post
import com.project.zemoga_test.repository.user.impl.UserMapperService
import com.project.zemoga_test.repository.user.impl.UserRepository
import com.project.zemoga_test.repository.user.model.User
import com.project.zemoga_test.services.api.RetrofitUtils
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

class UserRepositoryTest {
    lateinit var mockWebServer: MockWebServer

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
    fun `repository returns user objects after backend response`() {
//        Given
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(USER_RESPONSE_AS_STRING)
        mockWebServer.enqueue(expectedResponse)
//        When
        val repository = UserRepository(UserMapperService())
//        Then
        repository.getUser(1)
            .doOnError { Log.e("JIMMY", "Error: $it" ) }
            .test()
            .awaitCount(1)
            .assertValue(
                User(
                    id = 1,
                    name = "Leanne Graham",
                    email = "Sincere@april.biz",
                    phone = "1-770-736-8031 x56442",
                    website = "hildegard.org"
                )
            )
    }

    companion object {
        const val USER_RESPONSE_AS_STRING = "[{\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Leanne Graham\",\n" +
                "    \"username\": \"Bret\",\n" +
                "    \"email\": \"Sincere@april.biz\",\n" +
                "    \"address\": {\n" +
                "      \"street\": \"Kulas Light\",\n" +
                "      \"suite\": \"Apt. 556\",\n" +
                "      \"city\": \"Gwenborough\",\n" +
                "      \"zipcode\": \"92998-3874\",\n" +
                "      \"geo\": {\n" +
                "        \"lat\": \"-37.3159\",\n" +
                "        \"lng\": \"81.1496\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"phone\": \"1-770-736-8031 x56442\",\n" +
                "    \"website\": \"hildegard.org\",\n" +
                "    \"company\": {\n" +
                "      \"name\": \"Romaguera-Crona\",\n" +
                "      \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
                "      \"bs\": \"harness real-time e-markets\"\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Ervin Howell\",\n" +
                "    \"username\": \"Antonette\",\n" +
                "    \"email\": \"Shanna@melissa.tv\",\n" +
                "    \"address\": {\n" +
                "      \"street\": \"Victor Plains\",\n" +
                "      \"suite\": \"Suite 879\",\n" +
                "      \"city\": \"Wisokyburgh\",\n" +
                "      \"zipcode\": \"90566-7771\",\n" +
                "      \"geo\": {\n" +
                "        \"lat\": \"-43.9509\",\n" +
                "        \"lng\": \"-34.4618\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"phone\": \"010-692-6593 x09125\",\n" +
                "    \"website\": \"anastasia.net\",\n" +
                "    \"company\": {\n" +
                "      \"name\": \"Deckow-Crist\",\n" +
                "      \"catchPhrase\": \"Proactive didactic contingency\",\n" +
                "      \"bs\": \"synergize scalable supply-chains\"\n" +
                "    }\n" +
                "  }]"
    }
}