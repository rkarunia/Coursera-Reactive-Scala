package quickcheck

object TestQuickCheck {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

  class testHeap extends BinomialHeap with IntHeap {
    //insert(5, empty)
    //override def meld(ts1: H, ts2: H) = ts1 match {
    //  case Nil => ts2
     // case t1 :: ts1 =>
     // val t1x = println("t1: "+t1)
     // val ts1x = println("ts1: "+ts1)
     // List(Node(t1.x, t1.r, ts1 ++ ts2))
   // }
  }

  val obj = new testHeap                          //> obj  : quickcheck.TestQuickCheck.testHeap = quickcheck.TestQuickCheck$$anonf
                                                  //| un$main$1$testHeap$1@15e19d13
  val heap = obj.insert(5, obj.empty)             //> Insert begin, insert x: 5
                                                  //| Insert end, heap: List(Node(5,0,List()))
                                                  //| heap  : quickcheck.TestQuickCheck.obj.H = List(Node(5,0,List()))
  val heap1 = obj.insert(6, heap)                 //> Insert begin, insert x: 6
                                                  //| Insert end, heap: List(Node(5,1,List(Node(6,0,List()))))
                                                  //| heap1  : quickcheck.TestQuickCheck.obj.H = List(Node(5,1,List(Node(6,0,List(
                                                  //| )))))
  val heap3 = obj.insert(7, obj.empty)            //> Insert begin, insert x: 7
                                                  //| Insert end, heap: List(Node(7,0,List()))
                                                  //| heap3  : quickcheck.TestQuickCheck.obj.H = List(Node(7,0,List()))
  val heap4 = obj.insert(8, heap3)                //> Insert begin, insert x: 8
                                                  //| Insert end, heap: List(Node(7,1,List(Node(8,0,List()))))
                                                  //| heap4  : quickcheck.TestQuickCheck.obj.H = List(Node(7,1,List(Node(8,0,List(
                                                  //| )))))
  val melded = obj.meld(heap1, heap4)             //> melded  : quickcheck.TestQuickCheck.obj.H = List(Node(5,2,List(Node(7,1,List
                                                  //| (Node(8,0,List()))), Node(6,0,List()))))

}