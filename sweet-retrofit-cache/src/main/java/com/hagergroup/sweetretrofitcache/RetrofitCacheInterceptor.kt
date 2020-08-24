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

package com.hagergroup.sweetretrofitcache

import com.apollographql.apollo.api.cache.http.HttpCachePolicy
import com.hagergroup.sweetretrofitcache.request.decorateRequest
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author Ludovic Roland
 * @since 2020.08.20
 */
class RetrofitCacheInterceptor(private val cachePolicies: Map<String, HttpCachePolicy.Policy>) : Interceptor {

  companion object {

    const val CACHE_POLICY_ID = "CachePolicyId"

  }

  override fun intercept(chain: Interceptor.Chain): Response {
    var request = chain.request()
    val cachePolicyId = request.header(CACHE_POLICY_ID)

    if (cachePolicyId != null) {
      val cachePolicy = cachePolicies[cachePolicyId]

      if (cachePolicy != null) {
        request = request.decorateRequest(cachePolicy)
      }
    }

    return chain.proceed(request)
  }

}