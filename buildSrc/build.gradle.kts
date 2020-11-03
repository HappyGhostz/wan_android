plugins {
    `kotlin-dsl`
}

//需要加入这个，不然会报
//Cannot resolve external dependency org.jetbrains.kotlin:kotlin-compiler-embeddable:1.3.61 because no repositories are defined.
//Required by:
//    project :buildSrc
repositories {
    jcenter()
}