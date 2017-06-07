package pl.edu.maciejewskipwr.grzegorz.lab3android

fun toTrue(func: () -> Unit): Boolean {
    func()
    return true
}