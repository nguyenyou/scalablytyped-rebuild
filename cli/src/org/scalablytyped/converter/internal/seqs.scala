package org.scalablytyped.converter.internal

import scala.collection.{BuildFrom, IterableOps}

object seqs {
  @inline final implicit class TraversableOps[CC[_], T](private val ts: CC[T])(implicit ev: CC[T] <:< Iterable[T]) {

    def firstDefined[U](f: T => Option[U]): Option[U] = {
      val it = ts.toIterator

      while (it.hasNext) {
        val res = f(it.next())
        if (res.isDefined) return res
      }
      None
    }

    def partitionCollect[T1](t1: PartialFunction[T, T1])(
        implicit cbfT:           BuildFrom[CC[T], T, CC[T]],
        cbfT1:                   BuildFrom[CC[T], T1, CC[T1]],
    ): (CC[T1], CC[T]) = {

      val t1s  = cbfT1.newBuilder(ts)
      val rest = cbfT.newBuilder(ts)

      ts.foreach {
        case t if t1.isDefinedAt(t) => t1s += t1(t)
        case t                      => rest += t
      }

      (t1s.result(), rest.result())
    }

    def partitionCollect2[T1, T2](t1: PartialFunction[T, T1], t2: PartialFunction[T, T2])(
        implicit cbfT:                BuildFrom[CC[T], T, CC[T]],
        cbfT1:                        BuildFrom[CC[T], T1, CC[T1]],
        cbfT2:                        BuildFrom[CC[T], T2, CC[T2]],
    ): (CC[T1], CC[T2], CC[T]) = {
      val t1s  = cbfT1.newBuilder(ts)
      val t2s  = cbfT2.newBuilder(ts)
      val rest = cbfT.newBuilder(ts)

      ts.foreach {
        case t if t1.isDefinedAt(t) => t1s += t1(t)
        case t if t2.isDefinedAt(t) => t2s += t2(t)
        case t                      => rest += t
      }

      (t1s.result(), t2s.result(), rest.result())
    }

    def partitionCollect3[T1, T2, T3](
        t1: PartialFunction[T, T1],
        t2: PartialFunction[T, T2],
        t3: PartialFunction[T, T3],
    )(
        implicit cbfT: BuildFrom[CC[T], T, CC[T]],
        cbfT1:         BuildFrom[CC[T], T1, CC[T1]],
        cbfT2:         BuildFrom[CC[T], T2, CC[T2]],
        cbfT3:         BuildFrom[CC[T], T3, CC[T3]],
    ): (CC[T1], CC[T2], CC[T3], CC[T]) = {

      val t1s  = cbfT1.newBuilder(ts)
      val t2s  = cbfT2.newBuilder(ts)
      val t3s  = cbfT3.newBuilder(ts)
      val rest = cbfT.newBuilder(ts)

      ts.foreach {
        case t if t1.isDefinedAt(t) => t1s += t1(t)
        case t if t2.isDefinedAt(t) => t2s += t2(t)
        case t if t3.isDefinedAt(t) => t3s += t3(t)
        case t                      => rest += t
      }

      (t1s.result(), t2s.result(), t3s.result(), rest.result())
    }

    def partitionCollect4[T1, T2, T3, T4](
        t1: PartialFunction[T, T1],
        t2: PartialFunction[T, T2],
        t3: PartialFunction[T, T3],
        t4: PartialFunction[T, T4],
    )(
        implicit cbfT: BuildFrom[CC[T], T, CC[T]],
        cbfT1:         BuildFrom[CC[T], T1, CC[T1]],
        cbfT2:         BuildFrom[CC[T], T2, CC[T2]],
        cbfT3:         BuildFrom[CC[T], T3, CC[T3]],
        cbfT4:         BuildFrom[CC[T], T4, CC[T4]],
    ): (CC[T1], CC[T2], CC[T3], CC[T4], CC[T]) = {

      val t1s  = cbfT1.newBuilder(ts)
      val t2s  = cbfT2.newBuilder(ts)
      val t3s  = cbfT3.newBuilder(ts)
      val t4s  = cbfT4.newBuilder(ts)
      val rest = cbfT.newBuilder(ts)

      ts.foreach {
        case t if t1.isDefinedAt(t) => t1s += t1(t)
        case t if t2.isDefinedAt(t) => t2s += t2(t)
        case t if t3.isDefinedAt(t) => t3s += t3(t)
        case t if t4.isDefinedAt(t) => t4s += t4(t)
        case t                      => rest += t
      }

      (t1s.result(), t2s.result(), t3s.result(), t4s.result(), rest.result())
    }

    def partitionCollect5[T1, T2, T3, T4, T5](
        t1: PartialFunction[T, T1],
        t2: PartialFunction[T, T2],
        t3: PartialFunction[T, T3],
        t4: PartialFunction[T, T4],
        t5: PartialFunction[T, T5],
    )(
        implicit cbfT: BuildFrom[CC[T], T, CC[T]],
        cbfT1:         BuildFrom[CC[T], T1, CC[T1]],
        cbfT2:         BuildFrom[CC[T], T2, CC[T2]],
        cbfT3:         BuildFrom[CC[T], T3, CC[T3]],
        cbfT4:         BuildFrom[CC[T], T4, CC[T4]],
        cbfT5:         BuildFrom[CC[T], T5, CC[T5]],
    ): (CC[T1], CC[T2], CC[T3], CC[T4], CC[T5], CC[T]) = {

      val t1s  = cbfT1.newBuilder(ts)
      val t2s  = cbfT2.newBuilder(ts)
      val t3s  = cbfT3.newBuilder(ts)
      val t4s  = cbfT4.newBuilder(ts)
      val t5s  = cbfT5.newBuilder(ts)
      val rest = cbfT.newBuilder(ts)

      ts.foreach {
        case t if t1.isDefinedAt(t) => t1s += t1(t)
        case t if t2.isDefinedAt(t) => t2s += t2(t)
        case t if t3.isDefinedAt(t) => t3s += t3(t)
        case t if t4.isDefinedAt(t) => t4s += t4(t)
        case t if t5.isDefinedAt(t) => t5s += t5(t)
        case t                      => rest += t
      }

      (t1s.result(), t2s.result(), t3s.result(), t4s.result(), t5s.result(), rest.result())
    }
  }
}