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

import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.hagergroup.sweetokhttpcache.SweetCacheInterceptor
import com.hagergroup.sweetokhttpcache.bo.PartialPost
import com.hagergroup.sweetokhttpcache.bo.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

/**
 * @author Ludovic Roland
 * @since 2020.09.08
 */
interface JsonPlaceHolderServices {

  enum class CachePolicies(val policy: HttpCachePolicy.Policy) {

    CacheOnly(HttpCachePolicy.CACHE_ONLY),
    CacheFirst5Minutes(HttpCachePolicy.CACHE_FIRST.expireAfter(5, TimeUnit.MINUTES)),
    NetworkOnly(HttpCachePolicy.NETWORK_ONLY),
    NetworkFirst(HttpCachePolicy.NETWORK_FIRST),
    Delete(HttpCachePolicy.DELETE);

    companion object {
      fun toMap(): Map<String, HttpCachePolicy.Policy> =
          values().map { it.name to it.policy }.toMap()
    }

  }

  @GET("posts/{postId}")
  suspend fun getPostWithIdNoCache(@Path("postId") postId: Int): Response<Post?>

  @GET("posts/{postId}")
  suspend fun getPostWithIdCachePolicy(@Header(SweetCacheInterceptor.CACHE_POLICY_ID) cacheId: String, @Path("postId") postId: Int): Response<Post?>

  @POST("posts")
  suspend fun createPostNoCache(@Body post: Post): Response<Post?>

  @POST("posts")
  suspend fun createPostCachePolicy(@Header(SweetCacheInterceptor.CACHE_POLICY_ID) cacheId: String, @Body post: Post): Response<Post?>

  @PUT("posts/{postId}")
  suspend fun updatePostPutNoCache(@Path("postId") postId: Int, @Body post: Post): Response<Post?>

  @PUT("posts/{postId}")
  suspend fun updatePostPutCachePolicy(@Header(SweetCacheInterceptor.CACHE_POLICY_ID) cacheId: String, @Path("postId") postId: Int, @Body post: Post): Response<Post?>

  @PATCH("posts/{postId}")
  suspend fun updatePostPatchNoCache(@Path("postId") postId: Int, @Body partialPost: PartialPost): Response<Post?>

  @PATCH("posts/{postId}")
  suspend fun updatePostPatchCachePolicy(@Header(SweetCacheInterceptor.CACHE_POLICY_ID) cacheId: String, @Path("postId") postId: Int, @Body partialPost: PartialPost): Response<Post?>


//  @DELETE("posts/{postId}")
//  fun deletePost(@Path("postId") postId: Int): Call<ResponseBody>

}