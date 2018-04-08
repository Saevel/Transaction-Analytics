package org.dmcs.transaction.analytics.test.framework.utils

import java.io.Closeable

object ResourceControl {

  def withResource[T <: Closeable, X](c: => T)(f: T => X): X = try {
    f(c)
  } finally {
    c.close
  }
}
