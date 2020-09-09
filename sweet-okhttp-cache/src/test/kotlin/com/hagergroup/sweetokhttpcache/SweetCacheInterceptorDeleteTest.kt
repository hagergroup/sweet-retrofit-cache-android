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
import com.hagergroup.sweetokhttpcache.ws.JsonPlaceHolderServices
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
class SweetCacheInterceptorDeleteTest {

  @Test
  fun deletePostNoCacheTest() = runBlocking {
    val result = JsonPlaceHolderWebServiceCaller.deletePostNoCache(1)
    Assert.assertTrue(result)
  }

  @Test
  fun deletePostNetworkFirstTest() = runBlocking {
    val result = JsonPlaceHolderWebServiceCaller.deletePostCachePolicy(JsonPlaceHolderServices.CachePolicies.NetworkFirst.name, 2)
    Assert.assertTrue(result)
  }

  @Test
  fun deletePostNetworkOnlyTest() = runBlocking {
    val result = JsonPlaceHolderWebServiceCaller.deletePostCachePolicy(JsonPlaceHolderServices.CachePolicies.NetworkOnly.name, 3)
    Assert.assertTrue(result)
  }

  @Test
  fun deletePostCacheOnlyTest() = runBlocking {
    val result = JsonPlaceHolderWebServiceCaller.deletePostCachePolicy(JsonPlaceHolderServices.CachePolicies.NetworkOnly.name, 4)
    Assert.assertTrue(result)

    val otherResult = JsonPlaceHolderWebServiceCaller.deletePostCachePolicy(JsonPlaceHolderServices.CachePolicies.CacheOnly.name, 4)
    Assert.assertTrue(otherResult)
  }

  @Test
  fun deletePostCacheFirstTest() = runBlocking {
    val result = JsonPlaceHolderWebServiceCaller.deletePostCachePolicy(JsonPlaceHolderServices.CachePolicies.CacheFirst5Minutes.name, 5)
    Assert.assertTrue(result)
  }

  @Test
  fun deletePostCacheOnlyErrorTest(): Unit = runBlocking {
    try {
      JsonPlaceHolderWebServiceCaller.deletePostCachePolicy(JsonPlaceHolderServices.CachePolicies.CacheOnly.name, 6)
      Assert.assertTrue(false)
    } catch (exception: Exception) {
      Assert.assertTrue(exception is CallException)
    }
  }

}
