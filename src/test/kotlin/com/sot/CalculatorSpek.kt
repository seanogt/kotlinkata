package com.sot

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import java.util.*
import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo


class CalculatorSpek : Spek({


    describe("My calculator app"){

        it("empty string will return 0"){
            val calculator = Calculator()

            assert.that(0, equalTo(calculator.add("")))
        }
        it("1 number will return the same number"){
            val calculator = Calculator()
            assert.that(3, equalTo(calculator.add("3")))
        }
        it("2 numbers will return the sum"){
            val calculator = Calculator()
            assert.that(5, equalTo(calculator.add("2,3")))
        }
        it("can handle an unknown amount of numbers"){
            val calculator = Calculator()
            assert.that(27, equalTo(calculator.add("2,5,9,7,4")))
         }
        //
        it("can handle new lines between numbers (instead of commas)"){
            val calculator = Calculator()
            assert.that(6, equalTo(calculator.add("1\\n2,3")))
        }
        it("can't a list of numbers that ends with a delimiter"){
            val calculator = Calculator()
            try{
                calculator.add("1,\\n")
            }catch(e:IllegalArgumentException){
                assert.that("List of numbers cannot end with a delimiter", equalTo(e.message))
            }
        }
        it("can handle variable delimiters"){
                val calculator = Calculator()
                assert.that(3, equalTo(calculator.add("//;\\n1;2")))
        }
    }
})

class Calculator {

    fun add(numbers: String): Int {
        var delimiter = if (numbers.startsWith("//")) numbers.substring(2,3) else ","

        val strippedStringOfNumbers =
                if (numbers.startsWith("//")) numbers.substring(3).replace("\\n",delimiter)
                else numbers.replace("\\n",delimiter)

        if(strippedStringOfNumbers.endsWith(delimiter))
            throw IllegalArgumentException("List of numbers cannot end with a delimiter")

        val listOfNumbers = StringTokenizer(strippedStringOfNumbers,delimiter)

        if(listOfNumbers.countTokens() == 0) return 0 else {
            var totalCount = 0
                listOfNumbers.toList().forEach{totalCount += Integer.parseInt(it as String?)}
            return return totalCount
        }
    }

}
