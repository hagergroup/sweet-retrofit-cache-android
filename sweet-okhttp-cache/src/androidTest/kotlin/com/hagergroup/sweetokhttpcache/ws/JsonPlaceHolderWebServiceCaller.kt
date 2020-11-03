// MIT License
//
// Copyright (c) 2020 Hager Group
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.hagergroup.sweetokhttpcache.ws

import android.content.Context
import com.hagergroup.sweetokhttpcache.CallException
import com.hagergroup.sweetokhttpcache.bo.PartialPost
import com.hagergroup.sweetokhttpcache.bo.Post
import com.hagergroup.sweetokhttpcache.client.addSweetOkHttpCache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Ludovic Roland
 * @since 2020.09.08
 */
object JsonPlaceHolderWebServiceCaller : KoinComponent {

  private val context: Context by inject()

  private val services: JsonPlaceHolderServices

  init {
    val okHttp = OkHttpClient.Builder().apply {
      readTimeout(10, TimeUnit.SECONDS)
      connectTimeout(10, TimeUnit.SECONDS)
      addSweetOkHttpCache(JsonPlaceHolderServices.CachePolicies.toMap(), context.cacheDir)
      addNetworkInterceptor(httpLoggingInterceptor())
    }.build()

    val retrofitBuilder = Retrofit.Builder().apply {
      baseUrl("https://jsonplaceholder.typicode.com/")
      addConverterFactory(MoshiConverterFactory.create())
      client(okHttp)
    }

    services = retrofitBuilder.build().create(JsonPlaceHolderServices::class.java)
  }

  private fun httpLoggingInterceptor(): Interceptor {
    return HttpLoggingInterceptor().apply {
      level = HttpLoggingInterceptor.Level.BODY
    }
  }

  //region get
  suspend fun getPostWithIdNoCache(postId: Int): Post? {
    val response = services.getPostWithIdNoCache(postId)

    if (response.isSuccessful == false) {
      throw CallException(response.message(), response.code())
    }

    return response.body()
  }

  suspend fun getPostWithIdCachePolicy(cachePolicyName: String, postId: Int): Post? {
    val response = services.getPostWithIdCachePolicy(cachePolicyName, postId)

    if (response.isSuccessful == false) {
      throw CallException(response.message(), response.code())
    }

    return response.body()
  }
  //end region

  //region post
  suspend fun createPost(post: Post): Post? {
    val response = services.createPostNoCache(post)

    if (response.isSuccessful == false) {
      throw CallException(response.message(), response.code())
    }

    return response.body()
  }

  suspend fun createPostCachePolicy(cachePolicyName: String, post: Post): Post? {
    val response = services.createPostCachePolicy(cachePolicyName, post)

    if (response.isSuccessful == false) {
      throw CallException(response.message(), response.code())
    }

    return response.body()
  }
  //endregion

  //region put
  suspend fun updatePostPut(post: Post): Post? {
    requireNotNull(post.id) { "the id of the post cannot be null" }

    val response = services.updatePostPutNoCache(post.id, post)

    if (response.isSuccessful == false) {
      throw CallException(response.message(), response.code())
    }

    return response.body()
  }

  suspend fun updatePostPutCachePolicy(cachePolicyName: String, post: Post): Post? {
    requireNotNull(post.id) { "the id of the post cannot be null" }

    val response = services.updatePostPutCachePolicy(cachePolicyName, post.id, post)

    if (response.isSuccessful == false) {
      throw CallException(response.message(), response.code())
    }

    return response.body()
  }
  //endregion

  //region patch
  suspend fun updatePostPatch(postId: Int, partialPost: PartialPost): Post? {
    val response = services.updatePostPatchNoCache(postId, partialPost)

    if (response.isSuccessful == false) {
      throw CallException(response.message(), response.code())
    }

    return response.body()
  }

  suspend fun updatePostPatchCachePolicy(cachePolicyName: String, postId: Int, partialPost: PartialPost): Post? {
    val response = services.updatePostPatchCachePolicy(cachePolicyName, postId, partialPost)

    if (response.isSuccessful == false) {
      throw CallException(response.message(), response.code())
    }

    return response.body()
  }
  //endregion

  //region delete
  suspend fun deletePostNoCache(postId: Int): Boolean {
    val response = services.deletePostNoCache(postId)

    if (response.isSuccessful == false) {
      throw CallException(response.message(), response.code())
    }

    return true
  }

  suspend fun deletePostCachePolicy(cachePolicyName: String, postId: Int): Boolean {
    val response = services.deletePostCachePolicy(cachePolicyName, postId)

    if (response.isSuccessful == false) {
      throw CallException(response.message(), response.code())
    }

    return true
  }
  //endregion

  //region clean
  suspend fun cleanUpAll(): Boolean =
    services.cleanUpAll().code() == 200

  suspend fun cleanUp(cleanTimeout: Long, cleanTimeUnit: TimeUnit): Boolean
  {
    val newTimeout = TimeUnit.MILLISECONDS.convert(cleanTimeout, cleanTimeUnit)

    return services.cleanUp(newTimeout).code() == 200
  }
  //endregion
}
