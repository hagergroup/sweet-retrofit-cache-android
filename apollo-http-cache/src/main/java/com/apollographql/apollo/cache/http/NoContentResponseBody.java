package com.apollographql.apollo.cache.http;

import okhttp3.MediaType;
import okhttp3.ResponseBody;

/**
 * @author Ludovic Roland
 * @since 2020.09.09
 */
final class NoContentResponseBody {

  static ResponseBody create()
  {
    return ResponseBody.create(MediaType.parse("application/json"), "OK");
  }

}
