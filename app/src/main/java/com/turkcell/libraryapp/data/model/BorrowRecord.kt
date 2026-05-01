package com.turkcell.libraryapp.data.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class BorrowRecord(
    val id: String,
    @SerialName("student_id") val studentId: String,
    @SerialName("book_id") val bookId: String,
    @SerialName("borrowed_at") val borrowedAt: String = "",
    @SerialName("due_date") val dueDate: String = "",
    @SerialName("returned_at") val returnedAt: String? = null
)