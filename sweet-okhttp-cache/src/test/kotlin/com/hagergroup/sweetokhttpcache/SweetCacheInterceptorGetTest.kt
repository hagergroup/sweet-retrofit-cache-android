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
class SweetCacheInterceptorGetTest {

  /**
  {
  userId: 1,
  id: 2,
  title: "qui est esse",
  body: "est rerum tempore vitae sequi sint nihil reprehenderit dolor beatae ea dolores neque fugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis qui aperiam non debitis possimus qui neque nisi nulla",
  }
   */
  @Test
  fun getPostWithIdNoCacheTest() = runBlocking {
    val post = JsonPlaceHolderWebServiceCaller.getPostWithIdNoCache(1)

    Assert.assertNotNull(post)
    Assert.assertEquals(post?.id, 1)
    Assert.assertEquals(post?.userId, 1)
    Assert.assertEquals(post?.title, "sunt aut facere repellat provident occaecati excepturi optio reprehenderit")
  }

  /**
  {
  userId: 1,
  id: 2,
  title: "qui est esse",
  body: "est rerum tempore vitae sequi sint nihil reprehenderit dolor beatae ea dolores neque fugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis qui aperiam non debitis possimus qui neque nisi nulla",
  }
   */
  @Test
  fun getPostWithIdNetworkFirstTest() = runBlocking {
    val post = JsonPlaceHolderWebServiceCaller.getPostWithIdCachePolicy(JsonPlaceHolderServices.CachePolicies.NetworkFirst.name, 2)

    Assert.assertNotNull(post)
    Assert.assertEquals(post?.id, 2)
    Assert.assertEquals(post?.userId, 1)
    Assert.assertEquals(post?.title, "qui est esse")
  }

  /**
  {
  userId: 1,
  id: 3,
  title: "ea molestias quasi exercitationem repellat qui ipsa sit aut",
  body: "et iusto sed quo iure voluptatem occaecati omnis eligendi aut ad voluptatem doloribus vel accusantium quis pariatur molestiae porro eius odio et labore et velit aut",
  }
   */
  @Test
  fun getPostWithIdNetworkOnlyTest() = runBlocking {
    val post = JsonPlaceHolderWebServiceCaller.getPostWithIdCachePolicy(JsonPlaceHolderServices.CachePolicies.NetworkOnly.name, 3)

    Assert.assertNotNull(post)
    Assert.assertEquals(post?.id, 3)
    Assert.assertEquals(post?.userId, 1)
    Assert.assertEquals(post?.title, "ea molestias quasi exercitationem repellat qui ipsa sit aut")
  }

  /**
  {
  userId: 1,
  id: 4,
  title: "eum et est occaecati",
  body: "ullam et saepe reiciendis voluptatem adipisci sit amet autem assumenda provident rerum culpa quis hic commodi nesciunt rem tenetur doloremque ipsam iure quis sunt voluptatem rerum illo velit",
  }
   */
  @Test
  fun getPostWithIdCacheOnlyTest() = runBlocking {
    val post = JsonPlaceHolderWebServiceCaller.getPostWithIdCachePolicy(JsonPlaceHolderServices.CachePolicies.NetworkOnly.name, 4)

    Assert.assertNotNull(post)
    Assert.assertEquals(post?.id, 4)
    Assert.assertEquals(post?.userId, 1)
    Assert.assertEquals(post?.title, "eum et est occaecati")

    val otherPost = JsonPlaceHolderWebServiceCaller.getPostWithIdCachePolicy(JsonPlaceHolderServices.CachePolicies.CacheOnly.name, 4)

    Assert.assertNotNull(otherPost)
    Assert.assertEquals(otherPost?.id, 4)
    Assert.assertEquals(otherPost?.userId, 1)
    Assert.assertEquals(otherPost?.title, "eum et est occaecati")
  }

  /**
  {
  userId: 1,
  id: 5,
  title: "nesciunt quas odio",
  body: "repudiandae veniam quaerat sunt sed alias aut fugiat sit autem sed est voluptatem omnis possimus esse voluptatibus quis est aut tenetur dolor neque",
  }
   */
  @Test
  fun getPostWithIdCacheFirstTest() = runBlocking {
    val post = JsonPlaceHolderWebServiceCaller.getPostWithIdCachePolicy(JsonPlaceHolderServices.CachePolicies.CacheFirst5Minutes.name, 5)

    Assert.assertNotNull(post)
    Assert.assertEquals(post?.id, 5)
    Assert.assertEquals(post?.userId, 1)
    Assert.assertEquals(post?.title, "nesciunt quas odio")
  }

  /**
  {
  userId: 1,
  id: 5,
  title: "nesciunt quas odio",
  body: "repudiandae veniam quaerat sunt sed alias aut fugiat sit autem sed est voluptatem omnis possimus esse voluptatibus quis est aut tenetur dolor neque",
  }
   */
  @Test
  fun getPostWithIdCacheOnlyErrorTest(): Unit = runBlocking {
    try {
      JsonPlaceHolderWebServiceCaller.getPostWithIdCachePolicy(JsonPlaceHolderServices.CachePolicies.CacheOnly.name, 6)

      Assert.assertTrue(false)
    } catch (exception: Exception) {
      Assert.assertTrue(exception is CallException)
    }
  }

}
