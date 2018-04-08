package org.dmcs.transaction.analytics.test.framework.utils

import java.io.File

trait FileIO {

  protected def withEmptyFile[T](path: String)(f: File => T): T = {
    val file = new File(path)
    if(file.exists){
      file.delete
    }
    file.createNewFile
    f(file)
  }
}