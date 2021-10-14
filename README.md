# KotlinEtudes 

### Password Generator

An example of a [partial function application](https://github.com/sorokod/KotlinEtudes/blob/master/src/main/passwordgenerator/PasswordGenerator.kt)
```kotlin
typealias Alphabet = CharArray

/**
 * Given an Alphabet, returns a function that
 * given an int N return a list of N random characters from that Alphabet
 */
val charGenerator: (Alphabet) -> (Int) -> List<Char> =
    { alphabet: Alphabet ->
        { count: Int -> (1..count).map { alphabet[Random.nextInt(0, alphabet.size)] } }
    }
```

## Binary Heap
A [binary heap](https://github.com/sorokod/KotlinEtudes/blob/master/src/main/heap/BinHeap.kt) backed by an array based 
binary tree.


```kotlin
        val minHeap = BinHeap<Int>()
        val data = Array(1_000) { Random.nextInt(-1_000, 1_000) }

        // add data to heap
        data.forEach { minHeap.add(it) }.apply {
            assertTrue(minHeap.size() == data.size)
        }

        // remove all the elements from the heap and verify that the 
        // result is sorted
        List(minHeap.size()) { minHeap.remove() }.apply {
            assertTrue(minHeap.isEmpty())
            assertEquals(data.sorted(), this)
        }
```

## Sudoku 

## N Queens

## Language
Test driving various language features such as currying and class delegation

## DSL examples 

## Language.Complex
A complex number implementation + a little Mandelbrot set driver:

```
                                                                       *
                                                                       *
                                                                     ****
                                                                    *******
                                                                    ******
                                                                    *******
                                                                   ********
                                                                     *****
                                                                      ***
                                                              *   ************
                                                        ***   ******************
                                                        *** **********************   *
                                                         ************************** ***
                                                          *****************************
                                                         *****************************
                                                       *******************************
                                                     *********************************
                                                      *********************************
                                                     ***********************************
                                                   * *************************************
                                                    *************************************
                                        *          **************************************
                                   *  * *  *       **************************************
                                   *********       ***************************************
                                   ***********    ***************************************
                                  **************  ***************************************
                                 ***************  ***************************************
                                ********************************************************
                                ********************************************************
                            **  *******************************************************
                            **********************************************************
      ******************************************************************************
                            **********************************************************
                            **  *******************************************************
                                ********************************************************
                                ********************************************************
                                 ***************  ***************************************
                                  **************  ***************************************
                                   ***********    ***************************************
                                   *********       ***************************************
                                   *  * *  *       **************************************
                                        *          **************************************
                                                    *************************************
                                                   * *************************************
                                                     ***********************************
                                                      *********************************
                                                     *********************************
                                                       *******************************
                                                         *****************************
                                                          *****************************
                                                         ************************** ***
                                                        *** **********************   *
                                                        ***   ******************
                                                              *   ************
                                                                      ***
                                                                     *****
                                                                   ********
                                                                    *******
                                                                    ******
                                                                    *******
                                                                     ****
                                                                       *
                                                                       *



```