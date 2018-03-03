package com.sot

import io.kotlintest.matchers.shouldBe
import io.kotlintest.matchers.shouldThrow
import io.kotlintest.specs.StringSpec
import java.util.*


class CalculatorSpec : StringSpec({

    "empty string will return 0" {
        val calculator = Calculator()
        calculator.add("") shouldBe 0
    }

    "1 number will return the same number"{
        val calculator = Calculator()
        calculator.add("3") shouldBe 3
    }

    "2 numbers will return the sum" {
        val calculator = Calculator()
        calculator.add("2,3") shouldBe 5
    }

    "can handle an unknown amount of numbers"{
        val calculator = Calculator()
        calculator.add("2,5,9,7,4") shouldBe 27
    }

    "can handle new lines between numbers (instead of commas)"{
        val calculator = Calculator()
        calculator.add("1\\n2,3") shouldBe 6
    }

    "can't a list of numbers that ends with a delimiter"{
        val exception = shouldThrow<IllegalArgumentException> {
            val calculator = Calculator()
            calculator.add("1,\\n")
        }
        exception.message shouldBe "List of numbers cannot end with a delimiter"
    }

    "can handle variable delimiters" {
        val calculator = Calculator()
        calculator.add("//;\\n1;2") shouldBe 3
    }

    "can't handle negative numbers and throws exception contain those numbers"
    val exception = shouldThrow<IllegalArgumentException> {
        val calculator = Calculator()
        calculator.add("2,-5,9,-7,4")
    }
    exception.message shouldBe "negatives not allowed:-5 -7"

})

class Calculator {

    fun add(numbers: String): Int {

        var delimiter = if (numbers.startsWith("//")) numbers.substring(2, 3) else ","


        val strippedStringOfNumbers =
                if (numbers.startsWith("//")) numbers.substring(3).replace("\\n", delimiter)
                else numbers.replace("\\n", delimiter)

        if (strippedStringOfNumbers.endsWith(delimiter))
            throw IllegalArgumentException("List of numbers cannot end with a delimiter")


        val listOfNumbers = StringTokenizer(strippedStringOfNumbers, delimiter).toList()

        /*  var negativeNumbers = listOfNumbers.filter { Integer.parseInt(it as String?) < 0 }

          if(ne)*/

        var totalCount = 0
        listOfNumbers.forEach { totalCount += Integer.parseInt(it as String?) }
        return totalCount
    }
}
