# KotlinEtudes 

## Binary Heap

```kotlin
        val heap = BinaryHeap<Int>()
        val randomData = Array(1_000) { random.nextInt(100) }

        // add data to heap
        randomData.onEach { value -> heap.add(value) }

        // remove (sorted) data from heap
        val sorted = mutableListOf<Int>()
        while(!heap.isEmpty()) {
            sorted.add(heap.remove())
        }

        assertEquals(sorted, randomData.toList().sorted())
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