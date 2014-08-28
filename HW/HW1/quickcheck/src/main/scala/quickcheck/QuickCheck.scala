package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  val smallInt = Gen.choose(0, 1000)

  property("min3") = forAll(smallInt) { a =>
    //println("Insert a: "+a)
    val h = insert(a, empty)
    val b = a + 1
    //println("A: "+a)
    //println("B: "+b)
    //println("Insert b: "+b)
    val n = insert(b, h)
    //println("min: "+findMin(n))
    val c = b + 1
    //println("Insert c: "+c)
    val p = insert(c, n)
    findMin(p) == a
  }

  property("min2") = forAll(smallInt) { a =>
    //println("Insert a: "+a)
    val h = insert(a, empty)
    val b = a + 1
    //println("A: "+a)
    //println("B: "+b)
    //println("Insert b: "+b)
    val n = insert(b, h)
    //println("min: "+findMin(n))

    findMin(n) == a
  }

  property("meld1") = forAll(smallInt) { a =>
    val h1 = insert(a, empty)
    val b = a + 1
    val h2 = insert(b, h1)
    val c = b + 1
    val h3a = insert(c, h2)
    val h3b = meld(insert(c, empty), h2)
    println("h3a: "+h3a + ", h3b: "+h3b)
    findMin(h3a) == findMin(h3b)
  }
  
  property("linkBogus") = forAll(smallInt) { a =>
    val h1 = insert(a, empty)
    val b = a + 1  
    val h2 = insert(b, h1)
    val c = b + 1
    val h3 = insert(c, h2)
    val h4 = deleteMin(h3)
    findMin(h4) == b
    
  }
  lazy val genHeap: Gen[H] = ???

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

}
