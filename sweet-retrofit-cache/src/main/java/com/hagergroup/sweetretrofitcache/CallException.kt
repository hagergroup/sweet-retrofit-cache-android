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

/**
 * The exception that will be thrown if any problem occurs during a web service call.
 *
 * @author Ludovic Roland
 * @since 2018.03.27
 */
class CallException
  : Exception
{

  val code: Int

  constructor() : this(0)

  constructor(code: Int) : this(null, null, code)

  constructor(message: String, cause: Throwable) : this(message, cause, 0)

  constructor(message: String) : this(message, 0)

  constructor(message: String, code: Int) : this(message, null, code)

  constructor(cause: Throwable) : this(cause, 0)

  constructor(cause: Throwable, code: Int) : this(null, cause, code)

  constructor(message: String?, cause: Throwable?, code: Int) : super(message, cause)
  {
    this.code = code
  }

}