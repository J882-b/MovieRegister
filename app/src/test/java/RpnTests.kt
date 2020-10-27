
import org.junit.Assert
import org.junit.Test
import java.util.*

class RpnTests {

    @Test
    fun testRpn() {
      Assert.assertEquals("Summa: ", 13255.0, "245 2 x 4 / 88 x".rpn() + "110 4 / 5 x 72 4 / x".rpn(), 0.1)
    }

    fun String.rpn(): Double {
        val stack = Stack<Double>()
        this.split(' ').forEach{word ->
            when(word) {
                "+" -> stack.push(stack.pop() + stack.pop())
                "-" -> stack.push(-stack.pop() + stack.pop())
                "/" -> stack.push(1.0 / stack.pop() * stack.pop())
                "x" -> stack.push(stack.pop() * stack.pop())
                else -> stack.push(word.toDouble())
            }
            println(stack)
        }
        return stack.pop()
    }
}
