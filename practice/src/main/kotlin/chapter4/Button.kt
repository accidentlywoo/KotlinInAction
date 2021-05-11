package chapter4

class Button : Clickable, Focusable {
    override fun click() {
        println("click!")
    }

    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}