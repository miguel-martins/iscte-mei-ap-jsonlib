package generator

@Target(AnnotationTarget.PROPERTY)
annotation class JSONExclude {
}

@Target(AnnotationTarget.PROPERTY)
annotation class JSONIdentifier(val identifier: String) {
}