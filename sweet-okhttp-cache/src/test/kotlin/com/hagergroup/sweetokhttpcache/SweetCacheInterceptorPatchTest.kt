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
import com.hagergroup.sweetokhttpcache.bo.PartialPost
import com.hagergroup.sweetokhttpcache.ws.JsonPlaceHolderServices
import com.hagergroup.sweetokhttpcache.ws.JsonPlaceHolderWebServiceCaller
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * @author Ludovic Roland
 * @since 2020.09.09
 */
@RunWith(AndroidJUnit4::class)
@Config(application = Application::class)
class SweetCacheInterceptorPatchTest {

  @Test
  fun updatePostNoCacheTest() = runBlocking {
    val partialPost = PartialPost("title1")
    val responsePost = JsonPlaceHolderWebServiceCaller.updatePostPatch(1, partialPost)

    Assert.assertNotNull(responsePost)
    Assert.assertEquals(responsePost?.id, 1)
    Assert.assertEquals(responsePost?.userId, 1)
    Assert.assertEquals(responsePost?.title, partialPost.title)
  }

  @Test
  fun updatePostNetworkFirstTest() = runBlocking {
    val partialPost = PartialPost("title2")
    val responsePost = JsonPlaceHolderWebServiceCaller.updatePostPatchCachePolicy(JsonPlaceHolderServices.CachePolicies.NetworkFirst.name, 2, partialPost)

    Assert.assertNotNull(responsePost)
    Assert.assertEquals(responsePost?.id, 2)
    Assert.assertEquals(responsePost?.userId, 1)
    Assert.assertEquals(responsePost?.title, partialPost.title)
  }

  @Test
  fun updatePostNetworkOnlyTest() = runBlocking {
    val partialPost = PartialPost("title3")
    val responsePost = JsonPlaceHolderWebServiceCaller.updatePostPatchCachePolicy(JsonPlaceHolderServices.CachePolicies.NetworkOnly.name, 3, partialPost)

    Assert.assertNotNull(responsePost)
    Assert.assertEquals(responsePost?.id, 3)
    Assert.assertEquals(responsePost?.userId, 1)
    Assert.assertEquals(responsePost?.title, partialPost.title)
  }

  @Test
  fun updatePostCacheOnlyTest() = runBlocking {
    val partialPost = PartialPost("title4")

    val responsePost = JsonPlaceHolderWebServiceCaller.updatePostPatchCachePolicy(JsonPlaceHolderServices.CachePolicies.NetworkOnly.name, 4, partialPost)

    Assert.assertNotNull(responsePost)
    Assert.assertEquals(responsePost?.id, 4)
    Assert.assertEquals(responsePost?.userId, 1)
    Assert.assertEquals(responsePost?.title, partialPost.title)

    val otherPost = JsonPlaceHolderWebServiceCaller.updatePostPatchCachePolicy(JsonPlaceHolderServices.CachePolicies.CacheOnly.name, 4, partialPost)

    Assert.assertNotNull(otherPost)
    Assert.assertEquals(otherPost?.id, 4)
    Assert.assertEquals(otherPost?.userId, 1)
    Assert.assertEquals(otherPost?.title, partialPost.title)
  }

  @Test
  fun updatePostCacheFirstTest() = runBlocking {
    val partialPost = PartialPost("title5")
    val responsePost = JsonPlaceHolderWebServiceCaller.updatePostPatchCachePolicy(JsonPlaceHolderServices.CachePolicies.CacheFirst5Minutes.name, 5, partialPost)

    Assert.assertNotNull(responsePost)
    Assert.assertEquals(responsePost?.id, 5)
    Assert.assertEquals(responsePost?.userId, 1)
    Assert.assertEquals(responsePost?.title, partialPost.title)
  }

  @Test
  fun updatePostCacheOnlyErrorTest(): Unit = runBlocking {
    try {
      val partialPost = PartialPost("title6")
      JsonPlaceHolderWebServiceCaller.updatePostPatchCachePolicy(JsonPlaceHolderServices.CachePolicies.CacheOnly.name, 6, partialPost)

      Assert.assertTrue(false)
    } catch (exception: Exception) {
      Assert.assertTrue(exception is CallException)
    }
  }

}
