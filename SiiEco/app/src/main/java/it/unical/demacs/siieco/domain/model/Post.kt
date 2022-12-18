package it.unical.demacs.siieco.domain.model

import java.sql.Timestamp
import java.util.*

data class Post(
    val title: String = "",
    val desc: String = "",
    val image: String = "",
    val date: Date? = null,
    val post_type: Long = 0
) {}