package com.hagergroup.sweetretrofitcache.request

import com.apollographql.apollo.api.cache.http.HttpCache
import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import okhttp3.Request
import okio.Buffer
import okio.ByteString
import timber.log.Timber
import java.io.IOException

/**
 * @author Ludovic Roland
 * @since 2018.11.13
 */
@Throws(IOException::class)
fun Request.decorateRequest(cachePolicy: HttpCachePolicy.Policy): Request {
  val methodHex = ByteString.of(method().toByte()).md5().hex()
  val urlHex = ByteString.of(url().toString().toByte()).md5().hex()

  val hashBuffer = Buffer()
  body()?.writeTo(hashBuffer)
  val bodyHex = hashBuffer.readByteString().md5().hex()

  val cacheKey = "$methodHex-$urlHex-$bodyHex"

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
