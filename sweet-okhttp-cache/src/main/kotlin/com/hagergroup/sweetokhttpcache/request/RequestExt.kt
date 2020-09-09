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

package com.hagergroup.sweetokhttpcache.request

import com.apollographql.apollo.api.cache.http.HttpCache
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import okhttp3.Request
import okio.Buffer
import okio.ByteString.Companion.encodeUtf8
import timber.log.Timber
import java.io.IOException

/**
 * @author Ludovic Roland
 * @since 2018.11.13
 */
@Throws(IOException::class)
fun Request.decorateRequest(cachePolicy: HttpCachePolicy.Policy): Request {
  val methodHex = method().encodeUtf8().md5().hex()
  val urlHex = url().toString().encodeUtf8().md5().hex()

  val hashBuffer = Buffer()
  body()?.writeTo(hashBuffer)
  val bodyHex = hashBuffer.readByteString().md5().hex()

  val cacheKey = "sweet-$methodHex-$urlHex-$bodyHex"

  Timber.d("Created the cacheKey : '$cacheKey'")

  return newBuilder().apply {
    header(HttpCache.CACHE_KEY_HEADER, cacheKey)
    header(HttpCache.CACHE_FETCH_STRATEGY_HEADER, cachePolicy.fetchStrategy.name)
    header(HttpCache.CACHE_EXPIRE_TIMEOUT_HEADER, cachePolicy.expireTimeoutMs().toString())
    header(HttpCache.CACHE_EXPIRE_AFTER_READ_HEADER, cachePolicy.expireAfterRead.toString())
    header(HttpCache.CACHE_PREFETCH_HEADER, false.toString())
    header(HttpCache.CACHE_DO_NOT_STORE, false.toString())
  }.build()
}
