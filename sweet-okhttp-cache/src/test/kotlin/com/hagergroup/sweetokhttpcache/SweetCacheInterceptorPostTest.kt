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

package com.hagergroup.sweetokhttpcache

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hagergroup.sweetokhttpcache.bo.Post
import com.hagergroup.sweetokhttpcache.ws.IJsonPlaceHolderServices
import com.hagergroup.sweetokhttpcache.ws.JsonPlaceHolderWebServiceCaller
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * @author Ludovic Roland
 * @since 2020.09.08
 */
@RunWith(AndroidJUnit4::class)
@Config(application = Application::class)
class SweetCacheInterceptorPostTest {

  @Test
  fun createPostNoCacheTest() = runBlocking {
    val originalPost = Post(userId = 1, title = "title1", body = "body1")
    val responsePost = JsonPlaceHolderWebServiceCaller.createPost(originalPost)

    Assert.assertNotNull(responsePost)
    Assert.assertEquals(responsePost?.id, 101)
    Assert.assertEquals(responsePost?.userId, originalPost.userId)
    Assert.assertEquals(responsePost?.title, originalPost.title)
    Assert.assertEquals(responsePost?.body, originalPost.body)
  }

  @Test
  fun createPostNetworkFirstTest() = runBlocking {
    val originalPost = Post(userId = 2, title = "title2", body = "body2")
    val responsePost = JsonPlaceHolderWebServiceCaller.createPostCachePolicy(IJsonPlaceHolderServices.CachePolicies.NetworkFirst.name, originalPost)

    Assert.assertNotNull(responsePost)
    Assert.assertEquals(responsePost?.id, 101)
    Assert.assertEquals(responsePost?.userId, originalPost.userId)
    Assert.assertEquals(responsePost?.title, originalPost.title)
    Assert.assertEquals(responsePost?.body, originalPost.body)
  }

  @Test
  fun createPostNetworkOnlyTest() = runBlocking {
    val originalPost = Post(userId = 3, title = "title3", body = "body3")
    val responsePost = JsonPlaceHolderWebServiceCaller.createPostCachePolicy(IJsonPlaceHolderServices.CachePolicies.NetworkOnly.name, originalPost)

    Assert.assertNotNull(responsePost)
    Assert.assertEquals(responsePost?.id, 101)
    Assert.assertEquals(responsePost?.userId, originalPost.userId)
    Assert.assertEquals(responsePost?.title, originalPost.title)
    Assert.assertEquals(responsePost?.body, originalPost.body)
  }

  @Test
  fun createPostCacheOnlyTest() = runBlocking {
    val originalPost = Post(userId = 4, title = "title4", body = "body4")

    val responsePost = JsonPlaceHolderWebServiceCaller.createPostCachePolicy(IJsonPlaceHolderServices.CachePolicies.NetworkOnly.name, originalPost)

    Assert.assertNotNull(responsePost)
    Assert.assertEquals(responsePost?.id, 101)
    Assert.assertEquals(responsePost?.userId, originalPost.userId)
    Assert.assertEquals(responsePost?.title, originalPost.title)
    Assert.assertEquals(responsePost?.body, originalPost.body)

    val otherPost = JsonPlaceHolderWebServiceCaller.createPostCachePolicy(IJsonPlaceHolderServices.CachePolicies.CacheOnly.name, originalPost)

    Assert.assertNotNull(otherPost)
    Assert.assertEquals(otherPost?.id, 101)
    Assert.assertEquals(otherPost?.userId, originalPost.userId)
    Assert.assertEquals(otherPost?.title, originalPost.title)
    Assert.assertEquals(otherPost?.body, originalPost.body)
  }

  @Test
  fun createPostCacheFirstTest() = runBlocking {
    val originalPost = Post(userId = 5, title = "title5", body = "body5")
    val responsePost = JsonPlaceHolderWebServiceCaller.createPostCachePolicy(IJsonPlaceHolderServices.CachePolicies.CacheFirst5Minutes.name, originalPost)

    Assert.assertNotNull(responsePost)
    Assert.assertEquals(responsePost?.id, 101)
    Assert.assertEquals(responsePost?.userId, originalPost.userId)
    Assert.assertEquals(responsePost?.title, originalPost.title)
    Assert.assertEquals(responsePost?.body, originalPost.body)
  }

  @Test
  fun createPostCacheOnlyErrorTest(): Unit = runBlocking {
    try {
      val originalPost = Post(userId = 6, title = "title6", body = "body6")
      JsonPlaceHolderWebServiceCaller.createPostCachePolicy(IJsonPlaceHolderServices.CachePolicies.CacheOnly.name, originalPost)

      Assert.assertTrue(false)
    } catch (exception: Exception) {
      Assert.assertTrue(exception is CallException)
    }
  }

}
