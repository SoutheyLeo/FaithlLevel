package com.faithl.faithllevel.internal.util

import java.util.*

object Count {
    /**
     * 利用ascall码判断是否为数字
     *
     * @param x
     * @return
     */
    fun isNum(x: Char): Boolean {
        return x.code in 48..57
    }

    fun count(str: String): Int? {
        val num = Stack<Int>()
        val stringBuilder = StringBuilder()
        val x = str.toCharArray()
        for (i in x.indices) {
            if (isNum(x[i])) {
                stringBuilder.append(x[i])
                if (i + 1 == x.size) {
                    num.push(stringBuilder.toString().toInt())
                }
                if (x[i + 1] == ' ') {
                    num.push(stringBuilder.toString().toInt())
                    stringBuilder.delete(0, stringBuilder.length)
                    continue
                }
            }
            if (x[i] == '+') {
                num.push(num.pop() + num.pop())
            }
            if (x[i] == '-') {
                val a = num.pop()
                val b = num.pop()
                num.push(b - a)
            }
            if (x[i] == '*') {
                num.push(num.pop() * num.pop())
            }
            if (x[i] == '/') {
                val a = num.pop()
                val b = num.pop()
                num.push(b / a)
            }
        }
        return num.pop()
    }
}