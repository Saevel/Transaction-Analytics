package org.dmcs.transaction.analytics

import scala.util.Random

package object tests {

  implicit class RandomizableSeq[T](sequence: Seq[T]){
    def sample: T = sequence(Random.nextInt(sequence.size))
  }
}
