package com.example.jing.data

data class Updatedata(
    val assets: List<Asset>,
    val author: Author,
    val body: String,
    val created_at: String,
    val id: Int,
    val name: String,
    val prerelease: Boolean,
    val tag_name: String,
    val target_commitish: String
)

data class Asset(
    val browser_download_url: String,
    val name: String
)

data class Author(
    val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val html_url: String,
    val id: Int,
    val login: String,
    val name: String,
    val organizations_url: String,
    val received_events_url: String,
    val remark: String,
    val repos_url: String,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String
)