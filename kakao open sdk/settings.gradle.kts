val modules = listOf(
    // "all",
    // "all-rx",
    // "common",
    // "common-rx",
    // "network",
    // "network-rx",
    // "template",
    // "navi",
    // "auth",
    // "auth-rx",
    // "talk",
    // "talk-rx",
    // "story",
    // "story-rx",
    // "share",
    // "share-rx",
    // "user",
    // "user-rx",
    // "friend",
    // "friend-rx",
    "sample-common",
    "sample",
    "sample-rx"
)

modules.forEach {
    include(":$it")
}