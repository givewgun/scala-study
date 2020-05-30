import org.scalatest._

class MySetTest extends FlatSpec {

  "EmptySet" should "be constructed correctly" in {
    val aSet = MySet()
    assert(aSet == EmptySet)
  }

  it should "have a value after added" in {
    val aSet = MySet().add(1)
    assert(aSet.contains(1) == true)
  }

  "MySet" should "be constructed correctly" in {
    val aSet = MySet(1,2)
    assert(aSet != EmptySet)
    assert(aSet.contains(1) == true)
    assert(aSet.contains(2) == true)
  }

  it should "remove value correctly" in {
    val aSet = MySet(1,2,3).remove(1)
    assert(aSet.contains(1) == false)
  }

  it should "union correctly" in {
    val s1 = MySet(1, 2)
    val s2 = MySet("A", 6)
    val st = s1 union s2
    assert(st.contains(1) == true)
    assert(st.contains(2) == true)
    assert(st.contains("A") == true)
    assert(st.contains(6) == true)
  }

  it should "filter correctly" in {
    val s1 = MySet(1, 2, 3)
    val fs = s1.filter(_ % 2 == 0)
    assert(fs.contains(1) == false)
    assert(fs.contains(2) == true)
    assert(fs.contains(3) == false)
  }

  it should "intersect correctly" in {
    val s1 = MySet(1, 2)
    val s2 = MySet("A", 2)
    val st = s1 intersect s2
    assert(st.contains(1) == false)
    assert(st.contains("A") == false)
    assert(st.contains(2) == true)
  }

  it should "map correctly" in {
    val s1 = MySet(1, 2, 3)
    val fs = s1.map(_ * 4)
    assert(fs.contains(4) == true)
    assert(fs.contains(8) == true)
    assert(fs.contains(12) == true)
  }

  it should "flatMap correctly" in {
    val s1 = MySet(1, 2)
    val fs = s1.flatMap((x: Int) => MySet(x,x * 4))
    assert(fs.contains(1) == true)
    assert(fs.contains(4) == true)
    assert(fs.contains(2) == true)
    assert(fs.contains(8) == true)
  }



}
